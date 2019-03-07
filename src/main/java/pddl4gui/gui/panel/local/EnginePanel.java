package pddl4gui.gui.panel.local;

import pddl4gui.engine.Engine;
import pddl4gui.gui.tools.DrawCircle;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.LocalToken;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

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
     * The id of the EnginePanel (must be 0, 1 or 2).
     */
    private final int id;

    /**
     * The EngineManagerPanel of the EnginePanel.
     */
    private EngineManagerPanel engineManagerPanel;

    /**
     * The Engine associated to this EnginePanel.
     */
    private Engine engine;

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
     * Returns the id of the EnginePanel.
     *
     * @return the id of the EnginePanel.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the Engine label.
     *
     * @return the Engine label.
     */
    public JLabel getEngineLabel() {
        return this.engineLabel;
    }

    /**
     * Returns Engine circle status.
     *
     * @return Engine circle status.
     */
    public DrawCircle getCirclePanel() {
        return this.circlePanel;
    }

    /**
     * Returns Engine progress bar.
     *
     * @return Engine progress bar.
     */
    public JProgressBar getProgressBar() {
        return this.progressBar;
    }

    /**
     * Creates a new EnginePanel associated to an EngineManagerPanel.
     *
     * @param id                 the id of the EnginePanel.
     * @param engineManagerPanel the EngineManagerPanel of the EnginePanel.
     * @param token              the LocalToken to solve by the Engine attached to the EnginePanel.
     */
    EnginePanel(final int id, final EngineManagerPanel engineManagerPanel, final LocalToken token) {
        this.id = id;
        this.engineManagerPanel = engineManagerPanel;
        setLayout(null);

        final JLabel engineId = new JLabel("Engine " + this.id);
        engineId.setBounds(0, 5, 80, 20);
        add(engineId);

        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setValue(0);
        this.progressBar.setVisible(true);
        this.progressBar.setStringPainted(true);
        this.progressBar.setString("");
        this.progressBar.setBounds(60, 5, 245, 20);
        add(this.progressBar);

        this.circlePanel = new DrawCircle(3, 3, 15);
        this.circlePanel.setBounds(60, 25, 25, 25);
        add(this.circlePanel);

        this.engineLabel = new JLabel(" -- ");
        this.engineLabel.setBounds(90, 25, 265, 20);
        add(this.engineLabel);

        this.engine = new Engine(this, this.engineManagerPanel.getEngineManager(), token);
    }

    /**
     * Starts the Engine attached to the EnginePanel.
     */
    public void startEngine() {
        this.engine.start();
    }

    /**
     * Removes the EnginePanel from the EngineManagerPanel.
     */
    public void exit() {
        this.engineManagerPanel.removeEnginePanel(this);
        TriggerAction.gc();
    }
}
