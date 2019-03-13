package pddl4gui.gui;

import pddl4gui.gui.panel.local.EngineManagerPanel;
import pddl4gui.gui.panel.local.MenuSolverPanel;
import pddl4gui.gui.panel.local.SetupSolverPanel;
import pddl4gui.gui.panel.local.StatisticsPanel;
import pddl4gui.gui.panel.local.TokenListPanel;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Queue;

import javax.swing.JFrame;

/**
 * This class implements the Solver class of <code>PDDL4GUI</code>.
 * This JFrame displays all the Panel used in PDDL4GUI. It's the main JFrame.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Solver extends JFrame {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The token Queue.
     */
    private final Queue queue;

    /**
     * Gets the token Queue.
     *
     * @return the token Queue.
     */
    public Queue getQueue() {
        return queue;
    }

    /**
     * Creates a new Solver.
     *
     * @param queue the token Queue associated to the solver.
     */
    public Solver(Queue queue) {
        this.queue = queue;
        TriggerAction.setupSolver(this);

        setLayout(null);
        setSize(WindowsManager.getWidth(), WindowsManager.getHeight());
        setTitle(WindowsManager.NAME + " | Local solver");
        WindowsManager.setPoint(this.getLocation());

        final SetupSolverPanel setupPanel;
        final StatisticsPanel statisticsPanel;
        final MenuSolverPanel menuSolverPanel;
        final EngineManagerPanel engineManagerPanel;
        final TokenListPanel tokenListPanel;

        menuSolverPanel = new MenuSolverPanel();
        this.setJMenuBar(menuSolverPanel);

        setupPanel = new SetupSolverPanel();
        setupPanel.setBounds(0, 0, 330, 320);
        add(setupPanel);

        tokenListPanel = new TokenListPanel();
        tokenListPanel.setBounds(340, 0, 330, 320);
        add(tokenListPanel);

        engineManagerPanel = new EngineManagerPanel();
        engineManagerPanel.setBounds(0, 320, 330, 270);
        add(engineManagerPanel);

        statisticsPanel = new StatisticsPanel();
        statisticsPanel.setBounds(340, 320, 330, 270);
        add(statisticsPanel);

        final Result result = new Result();
        Result.setFrame(result);

        TriggerAction.setupPanel(statisticsPanel, result, menuSolverPanel, engineManagerPanel, tokenListPanel);

        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        engineManagerPanel.startEngineManager();
    }
}
