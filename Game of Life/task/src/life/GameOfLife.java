package life;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GameOfLife extends JFrame {
    private final int universeSize;
    private Universe universeData;
    private UniverseView theView;
    private int currentGeneration;
    private Timer timer;

    public static void main(String[] args) {
        new GameOfLife();
    }

    public GameOfLife() {
        this.universeSize = 50;
        this.currentGeneration = 0;

        try {
            this.universeData = new Universe(universeSize, System.currentTimeMillis());
            this.theView = new UniverseView(currentGeneration, universeData);
            this.theView.addPlayButtonListener(new PlayListener());
            this.theView.addSkipButtonListener(new SkipListener());
            this.theView.addResetButtonListener(new ResetListener());
            this.timer = new Timer(1000, new TimerListener());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void incrementGeneration() {
        currentGeneration++;
        universeData = Generation.next(universeData);
        theView.setUniverse(universeData);
        theView.setGeneration(currentGeneration);
    }

    class SkipListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent itemEvent) {
            incrementGeneration();
        }
    }

    class PlayListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            // event is generated in button
            int state = itemEvent.getStateChange();
            // if selected, step each generation with timer
            if (state == ItemEvent.SELECTED) {
                theView.setPlayState(true);
                timer.start();
                incrementGeneration();
            } else {
                theView.setPlayState(false);
                timer.stop();
            }
        }
    }

    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent itemEvent) {
            currentGeneration = 0;
            universeData = new Universe(universeSize, System.currentTimeMillis());
            theView.setUniverse(universeData);
            theView.setGeneration(currentGeneration);
        }
    }

    class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent itemEvent) {
            incrementGeneration();
        }
    }
}