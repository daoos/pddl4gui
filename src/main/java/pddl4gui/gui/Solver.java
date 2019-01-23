package pddl4gui.gui;

import pddl4gui.gui.panel.LogPanel;
import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.local.EngineManagerPanel;
import pddl4gui.gui.panel.local.MenuSolverPanel;
import pddl4gui.gui.panel.local.SetupSolverPanel;
import pddl4gui.gui.panel.local.StatisticsPanel;
import pddl4gui.gui.panel.local.TokenListPanel;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Queue;

import java.io.Serializable;
import javax.swing.JFrame;

/**
 * This class implements the Solver class of <code>PDDL4GUI</code>.
 * This JFrame displays all the Panel used in PDDL4GUI. It's the main JFrame.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Solver extends JFrame implements Serializable {

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

        final int width = 1200;
        final int height = 835;
        final int marging = 10;

        setLayout(null);
        setSize(width, height);
        setTitle(WindowsManager.NAME + " | Local solver");
        WindowsManager.setPoint(this.getLocation());
        WindowsManager.setWidth(width);
        WindowsManager.setHeight(height);

        final SetupSolverPanel setupPanel;
        final StatisticsPanel statisticsPanel;
        final ResultPanel resultPanel;
        final MenuSolverPanel menuSolverPanel;
        final EngineManagerPanel engineManagerPanel;
        final TokenListPanel tokenListPanel;
        final LogPanel logPanel;

        menuSolverPanel = new MenuSolverPanel();
        this.setJMenuBar(menuSolverPanel);

        setupPanel = new SetupSolverPanel();
        setupPanel.setBounds(marging, marging, 330, 320);
        add(setupPanel);

        tokenListPanel = new TokenListPanel();
        tokenListPanel.setBounds(350, marging, 330, 320);
        add(tokenListPanel);

        engineManagerPanel = new EngineManagerPanel();
        engineManagerPanel.setBounds(marging, 340, 330, 240);
        add(engineManagerPanel);

        statisticsPanel = new StatisticsPanel();
        statisticsPanel.setBounds(350, 340, 330, 240);
        add(statisticsPanel);

        resultPanel = new ResultPanel();
        resultPanel.setBounds(690, marging, 500, 570);
        add(resultPanel);

        logPanel = new LogPanel();
        logPanel.setBounds(marging, 585, 1180, 175);
        add(logPanel);

        TriggerAction.setupPanel(statisticsPanel, resultPanel, menuSolverPanel, engineManagerPanel, tokenListPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
