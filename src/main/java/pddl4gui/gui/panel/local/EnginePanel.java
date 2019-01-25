package pddl4gui.gui.panel.local;

import pddl4gui.engine.Engine;
import pddl4gui.gui.tools.DrawCircle;
import pddl4gui.gui.tools.Icons;

import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * This class implements the EnginePanel class of <code>PDDL4GUI</code>.
 * This JPanel displays all the information of an Engine.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class EnginePanel extends JPanel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Engine associated to this EnginePanel.
     */
    private Engine engine;

    /**
     * The Buttons to init Engine.
     */
    private final JButton initButton;

    /**
     * The Buttons to stop Engine.
     */
    private final JButton stopButton;

    /**
     * The JLabel to display Engine status.
     */
    private final JLabel engineLabel;

    /**
     * The DrawCircle to display Engine status.
     */
    private final DrawCircle circlePanel;

    /**
     * The JProgressBar to display Engine status.
     */
    private final JProgressBar progressBar;

    /**
     * Returns the init button.
     *
     * @return the init button.
     */
    public JButton getInitButton() {
        return initButton;
    }

    /**
     * Returns the Engine label.
     *
     * @return the Engine label.
     */
    public JLabel getEngineLabel() {
        return engineLabel;
    }

    /**
     * Returns Engine circle status.
     *
     * @return Engine circle status.
     */
    public DrawCircle getCirclePanel() {
        return circlePanel;
    }

    /**
     * Returns Engine progress bar.
     *
     * @return Engine progress bar.
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * Creates a new EnginePanel associated to an EngineManagerPanel.
     *
     * @param engineManagerPanel the EngineManagerPanel.
     * @param active             the status of the associated Engine.
     */
    EnginePanel(EngineManagerPanel engineManagerPanel, boolean active) {
        setLayout(null);

        initButton = new JButton(Icons.getStartIcon());
        stopButton = new JButton(Icons.getStopIcon());

        Random random = new Random();
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
