package pddl4gui.gui.panel;

import pddl4gui.engine.Engine;
import pddl4gui.gui.tools.DrawCircle;
import pddl4gui.gui.tools.Icons;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.Color;
import java.util.Random;

public class EnginePanel extends JPanel {

    private Engine engine;

    final private JButton initButton, stopButton;
    final private JLabel engineLabel;
    final private DrawCircle circlePanel;
    final private JProgressBar progressBar;

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

    public EnginePanel(EngineManagerPanel engineManagerPanel, boolean active) {
        setLayout(null);
        Random random = new Random();

        initButton = new JButton(Icons.getStartIcon());
        stopButton = new JButton(Icons.getStopIcon());

        initButton.setBounds(30, 5, 20, 20);
        initButton.setEnabled(!active);
        initButton.addActionListener(e -> {
            initButton.setEnabled(false);
            stopButton.setEnabled(true);
            engine = new Engine(random.nextInt(500) + 500, this);
            engineManagerPanel.addEngine(engine);
            engine.start();
        });
        add(initButton);

        stopButton.setBounds(5, 5, 20, 20);
        stopButton.setEnabled(active);
        stopButton.addActionListener(e -> {
            engine.interrupt();
            engineManagerPanel.removeEngine(engine);
            stopButton.setEnabled(false);
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
        progressBar.setBounds(60, 5, 245, 20);
        add(progressBar);

        circlePanel = new DrawCircle(3, 3, 15);
        circlePanel.setBounds(60, 25, 25, 25);
        add(circlePanel);

        engineLabel = new JLabel(" -- ");
        engineLabel.setBounds(90, 25, 265, 20);
        add(engineLabel);

        if (active) {
            engine = new Engine(random.nextInt(500) + 500, this);
            engineManagerPanel.addEngine(engine);
            engine.start();
        }
    }
}
