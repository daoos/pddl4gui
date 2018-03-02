package pddl4gui.gui.panel;

import pddl4gui.engine.Engine;
import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.DrawCircle;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;


public class EngineStatusPanel extends JPanel {

    final private JButton initButton;
    final private JLabel engineLabel, remainingLabel;
    final private DrawCircle circlePanel;
    final private JProgressBar progressBar;

    public JButton getInitButton() {
        return initButton;
    }

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

    public EngineStatusPanel(Solver solver) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engine status"));

        circlePanel = new DrawCircle(20, 20, 20);
        circlePanel.setBounds(0, 5, 40, 40);
        add(circlePanel);

        engineLabel = new JLabel(" -- ");
        engineLabel.setBounds(50, 25, 265, 20);
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
        progressBar.setBounds(20, 65, 290, 20);
        add(progressBar);

        remainingLabel = new JLabel(" -- token(s) remaining");
        remainingLabel.setBounds(100, 100, 265, 20);
        add(remainingLabel);


        final JButton stopButton = new JButton("Stop engine");
        stopButton.setBounds(20, 135, 120, 25);
        stopButton.setEnabled(true);
        stopButton.addActionListener(e -> {
            solver.getEngine().interrupt();
            stopButton.setEnabled(false);
        });
        add(stopButton);

        initButton = new JButton("Init engine");
        initButton.setBounds(190, 135, 120, 25);
        initButton.setEnabled(false);
        initButton.addActionListener(e -> {
            initButton.setEnabled(false);
            stopButton.setEnabled(true);

            final Engine engine = new Engine(500);
            engine.setSolver(solver);
            solver.setEngine(engine);
            solver.resetSolver();
            engine.start();
        });
        add(initButton);
    }
}
