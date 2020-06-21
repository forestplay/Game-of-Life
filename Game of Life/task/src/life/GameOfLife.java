package life;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    public GameOfLife() {
        // window label
        super("Game of Life");

        int universeSize = 40;
        int generations = 100;

        Universe universe = new Universe(universeSize, System.currentTimeMillis());

        try {
            int x = 0;
            while (x <= generations) {
                displayUniverse(x, universe);
                universe = Generation.next(universe);
                x++;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        }

    public static void main(String[] args) {
        new GameOfLife();
    }

    public void displayUniverse(int generation, Universe universe) throws Exception {
        int universeSize = universe.getSize();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel labelPanel  = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        JLabel generationLabel = new JLabel("Generation #" + generation);
        labelPanel.add(generationLabel);
        JLabel aliveLabel = new JLabel("Alive: " + universe.livingCells());
        labelPanel.add(aliveLabel);
        add(labelPanel, BorderLayout.NORTH);

        JPanel universePanel = new JPanel();
        universePanel.setLayout(new GridLayout(universeSize, universeSize, 0, 0));
        for (int x = 0; x < universeSize; x++)
            for (int y = 0; y < universeSize; y++) {
                JButton button = new JButton();
                button.setEnabled(false);
                if (universe.getCell(x, y) == Universe.ALIVE)
                    button.setBackground(Color.BLUE);
                universePanel.add(button);
            }
        add(universePanel, BorderLayout.CENTER);

        setVisible(true);
        Thread.sleep(500L);

    }

}