package pddl4gui.gui.panel;

import pddl4gui.engine.Engine;
import pddl4gui.engine.EngineManager;
import pddl4gui.gui.Solver;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.Serializable;

/**
 * This class implements the EngineManagerPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays all the EnginePanel.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class EngineManagerPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Solver JFrame.
     */
    final private Solver solver;

    /**
     * The EngineManager which manages Engine.
     */
    final private EngineManager engineManager;

    /**
     * The JLabel for remaining tokens to solve.
     */
    final private JLabel remainingLabel;

    /**
     * Adds an Engine in the EngineManager.
     *
     * @param engine the Engine to add.
     */
    public void addEngine(Engine engine) {
        engineManager.addEngine(engine);
    }

    /**
     * Removes an Engine from the EngineManager.
     *
     * @param engine the Engine to remove.
     */
    public void removeEngine(Engine engine) {
        engineManager.removeEngine(engine);
    }

    /**
     * Sets the text of the remaining label about the number of remaining tokens to solve.
     */
    public void setTokensRemaining() {
        remainingLabel.setText(solver.getQueue().remainingTokens() + " token(s) remaining");
    }

    /**
     * Creates a new EngineManagerPanel associated to the Solver main JFrame.
     *
     * @param solver the Solver main JFrame.
     */
    public EngineManagerPanel(Solver solver) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engines status"));

        engineManager = new EngineManager(1000, this, solver.getQueue());

        this.solver = solver;

        int labHeight = 25;
        final int labMarging = 45;
        final int cores = Runtime.getRuntime().availableProcessors();

        boolean activeEngine = true;
        int inc = 0;
        do {
            if (inc > 0) {
                activeEngine = false;
            }

            final EnginePanel enginePanel = new EnginePanel(this, activeEngine);
            enginePanel.setBounds(10, labHeight, 310, 50);
            add(enginePanel);

            labHeight += labMarging;
            inc++;
        }
        while (inc < cores - 1 && inc < 4);

        remainingLabel = new JLabel(" -- token(s) remaining");
        remainingLabel.setBounds(200, 214, 265, 20);
        add(remainingLabel);

        engineManager.start();
    }
}
