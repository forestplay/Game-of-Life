package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class UniverseView extends JFrame {

    int generation;

    JPanel leftPanel;
    Image playImage;
    Image pauseImage;
    Image skipImage;
    Image resetImage;
    JButton skipButton;
    JToggleButton playButton;
    JButton resetButton;
    JLabel generationLabel;
    JLabel aliveLabel;

    JPanel universePanel;

    public UniverseView(int generation, Universe universe) {
        // window label
        super("Game of Life");

        this.generation = generation;
        int universeSize = universe.getSize();
        this.playImage = (new ImageIcon("Game of Life/task/icons/play-512.png")).getImage()
                .getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        this.pauseImage = (new ImageIcon("Game of Life/task/icons/pause-512.png")).getImage()
                .getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        this.skipImage = (new ImageIcon("Game of Life/task/icons/skip-512.png")).getImage()
                .getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        this.resetImage = (new ImageIcon("Game of Life/task/icons/restart-512.png")).getImage()
                .getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension((15 * universeSize) + 150, 15 * universeSize));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // create the controls and notifications in a box layout, stacking on Y-axis
        this.leftPanel = new JPanel();
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.leftPanel.setPreferredSize(new Dimension(150, 15 * universeSize));

        // create skip button
        this.skipButton = new JButton();
        this.skipButton.setName("SkipButton");
        this.skipButton.setIcon(new ImageIcon(skipImage));
        this.skipButton.setPreferredSize(new Dimension(40, 20));
        leftPanel.add(skipButton);

        // create play button
        this.playButton = new JToggleButton();
        this.playButton.setName("PlayToggleButton");
        this.playButton.setIcon(new ImageIcon(playImage));
        this.playButton.setPreferredSize(new Dimension(40, 20));
        leftPanel.add(playButton);

        // create a Reset button
        this.resetButton = new JButton(new ImageIcon(resetImage));
        this.resetButton.setName("ResetButton");
        this.resetButton.setPreferredSize(new Dimension(40, 20));
        leftPanel.add(this.resetButton);

        // create labels for Generation and Alive cells
        setGenerationLabel(this.generation);
        setAliveLabel(universe.livingCells());

        add(leftPanel, BorderLayout.WEST);

        // create panel to display universe data
        this.universePanel = createUniversePanel(universe);
        add(universePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void setGenerationLabel(int generation) {
        try {
            leftPanel.remove(this.generationLabel);
        } catch (NullPointerException ignored) {}
        this.generationLabel = new JLabel("Generation #" + generation);
        this.generationLabel.setName("GenerationLabel");
        leftPanel.add(this.generationLabel);
    }

    public void setAliveLabel(int livingCells) {
        try {
            leftPanel.remove(this.aliveLabel);
        } catch (NullPointerException ignored) {}
        this.aliveLabel = new JLabel("Alive: " + livingCells);
        this.aliveLabel.setName("AliveLabel");
        leftPanel.add(this.aliveLabel);
    }

    public void setUniverse(Universe universeData) {
        try {
            remove(this.universePanel);
        } catch (NullPointerException ignored) {}
        this.universePanel = createUniversePanel(universeData);
        add(this.universePanel, BorderLayout.CENTER);
        setGenerationLabel(this.generation);
        setAliveLabel(universeData.livingCells());
        revalidate();
    }

    private JPanel createUniversePanel(Universe universe) {
        int universeSize = universe.getSize();
        // create panel to display universe data
        JPanel universePanel = new JPanel();
        universePanel.setLayout(new GridLayout(universeSize, universeSize, 0, 0));
        for (int x = 0; x < universeSize; x++) {
            for (int y = 0; y < universeSize; y++) {
                JButton button = new JButton();
                button.setEnabled(false);
                if (universe.getCell(x, y).equals(Universe.ALIVE))
                    button.setBackground(Color.BLUE);
                universePanel.add(button);
            }
        }
        return universePanel;
    }

    public void setPlayState(boolean playState) {
        if (playState)
            playButton.setIcon(new ImageIcon(pauseImage));
        else
            playButton.setIcon(new ImageIcon(playImage));
    }

    public void setGeneration(int generation) {
        this.generation = generation;
        this.generationLabel.setText("Generation #" + generation);
    }

    void addPlayButtonListener(ItemListener listenForPlayButton) {
        playButton.addItemListener(listenForPlayButton);
    }

    void addSkipButtonListener(ActionListener listenForSkipButton) {
        skipButton.addActionListener(listenForSkipButton);
    }

    void addResetButtonListener(ActionListener listenForResetButton) {
        resetButton.addActionListener(listenForResetButton);
    }

}
