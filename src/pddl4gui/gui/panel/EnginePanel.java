package pddl4gui.gui.panel;

import pddl4gui.engine.Engine;
import pddl4gui.gui.tools.DrawCircle;
import pddl4gui.gui.tools.Icons;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.util.Random;

public class EnginePanel extends JPanel {

    private Engine engine;

    final private EngineManagerPanel engineManagerPanel;

    final private JButton initButton, stopButton;
    final private JLabel engineLabel;
    final private DrawCircle circlePanel;
    final private JProgressBar progressBar;
    private boolean status;

    public JButton getInitButton() {
        return initButton;
    }

    public JLabel getEngineLabel() {
        return engineLabel;
    }

    public DrawCircle getCirclePanel() {
        return circlePanel;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public boolean isStatus() {
        return status;
    }

    public void setTokensRemaining() {
        engineManagerPanel.setTokensRemaining();
    }


    public EnginePanel(EngineManagerPanel engineManagerPanel, boolean active) {
        setLayout(null);
        Random rand = new Random();

        this.engineManagerPanel = engineManagerPanel;

        initButton = new JButton(Icons.getStartIcon());
        stopButton = new JButton(Icons.getStopIcon());
        status = true;

        initButton.setBounds(45, 5, 20, 20);
        initButton.setEnabled(!active);
        initButton.addActionListener(e -> {
            initButton.setEnabled(false);
            stopButton.setEnabled(true);
            engine = new Engine(rand.nextInt(250) + 500, this, engineManagerPanel.getQueue());
            engine.start();
            status = true;
        });
        add(initButton);

        stopButton.setBounds(10, 5, 20, 20);
        stopButton.setEnabled(active);
        stopButton.addActionListener(e -> {
            engine.interrupt();
            stopButton.setEnabled(false);
            status = false;
        });
        add(stopButton);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setVisible(true);
        progressBar.setStringPainted(true);
        progressBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() {
                return Color.black;
            }

            protected Color getSelectionForeground() {
                return Color.black;
            }
        });
        progressBar.setString("");
        progressBar.setBounds(80, 5, 225, 20);
        add(progressBar);

        circlePanel = new DrawCircle(3, 3, 15);
        circlePanel.setBounds(80, 25, 25, 25);
        add(circlePanel);

        engineLabel = new JLabel(" -- ");
        engineLabel.setBounds(110, 25, 265, 20);
        add(engineLabel);

        if (active) {
            engine = new Engine(rand.nextInt(250) + 500, this, engineManagerPanel.getQueue());
            engine.start();
        }
    }
}
