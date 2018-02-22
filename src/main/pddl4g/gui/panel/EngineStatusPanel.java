package main.pddl4g.gui.panel;

import main.pddl4g.gui.tools.DrawCircle;

import javax.swing.*;

public class EngineStatusPanel extends JPanel {

    private JLabel engineLabel;
    private DrawCircle circlePanel;

    public JLabel getEngineLabel() {
        return engineLabel;
    }

    public DrawCircle getCirclePanel() {
        return circlePanel;
    }

    public EngineStatusPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engine status"));

        circlePanel = new DrawCircle(20, 20 ,20);
        circlePanel.setBounds(0,0,40,40);
        add(circlePanel);

        engineLabel = new JLabel(" -- ");
        engineLabel.setBounds(60, 20, 140, 20);
        add(engineLabel);
    }

}
