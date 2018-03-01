package pddl4gui.gui.panel;

import pddl4gui.gui.tools.DrawCircle;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;


public class EngineStatusPanel extends JPanel {

    final private JLabel engineLabel, remainingLabel;
    final private DrawCircle circlePanel;
    final private JProgressBar progressBar;

    public JLabel getEngineLabel() {
        return engineLabel;
    }

    public void setTokensRemaining(int number) {
        remainingLabel.setText(number + " token(s) remaining");
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
        progressBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return Color.black; }
            protected Color getSelectionForeground() { return Color.black; }
        });
        progressBar.setString("Time out");
        progressBar.setBounds(20, 70, 290, 20);
        add(progressBar);

        remainingLabel = new JLabel(" -- token(s) remaining");
        remainingLabel.setBounds(100, 105, 265, 20);
        add(remainingLabel);
    }
}
