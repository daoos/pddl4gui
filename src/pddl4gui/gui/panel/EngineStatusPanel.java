package pddl4gui.gui.panel;

import pddl4gui.gui.tools.DrawCircle;

import javax.swing.*;


public class EngineStatusPanel extends JPanel {

    final private JLabel engineLabel;
    final private DrawCircle circlePanel;
    final private JProgressBar progressBar;

    public JLabel getEngineLabel() {
        return engineLabel;
    }

    public DrawCircle getCirclePanel() {
        return circlePanel;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public EngineStatusPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engine status"));

        circlePanel = new DrawCircle(20, 20, 20);
        circlePanel.setBounds(0, 10, 40, 40);
        add(circlePanel);

        engineLabel = new JLabel(" -- ");
        engineLabel.setBounds(50, 30, 265, 20);
        add(engineLabel);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setVisible(true);
        progressBar.setStringPainted(true);
        progressBar.setString("Time out");
        progressBar.setBounds(20, 70, 290, 20);
        add(progressBar);
    }
}
