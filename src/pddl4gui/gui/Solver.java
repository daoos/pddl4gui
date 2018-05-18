package pddl4gui.gui;

import pddl4gui.token.Queue;
import pddl4gui.gui.panel.EngineManagerPanel;
import pddl4gui.gui.panel.MenuSolverPanel;
import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.SetupSolverPanel;
import pddl4gui.gui.panel.StatisticsPanel;
import pddl4gui.gui.panel.TokenListPanel;
import pddl4gui.gui.tools.TokenList;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.planners.Planner;
import pddl4gui.planners.PlannerFactory;
import pddl4gui.token.Token;

import javax.swing.*;
import java.io.File;
import java.util.Vector;

public class Solver extends JFrame {

    final private Queue queue;

    final private SetupSolverPanel setupPanel;
    final private StatisticsPanel statisticsPanel;
    final private ResultPanel resultPanel;
    final private MenuSolverPanel menuSolverPanel;
    final private EngineManagerPanel engineManagerPanel;
    final private TokenListPanel tokenListPanel;

    public Queue getQueue() {
        return queue;
    }

    public ResultPanel getResultPanel() {
        return resultPanel;
    }

    public SetupSolverPanel getSetupPanel() {
        return setupPanel;
    }

    public TokenListPanel getTokenListPanel() {
        return tokenListPanel;
    }

    public MenuSolverPanel getMenuSolverPanel() {
        return menuSolverPanel;
    }

    public Solver(Queue queue) {
        this.queue = queue;

        final int width = 1200;
        final int height = 620;
        final int marging = 10;

        setLayout(null);
        setSize(width, height);
        setTitle(WindowsManager.NAME);
        WindowsManager.setPoint(this.getLocation());
        WindowsManager.setWidth(width);

        menuSolverPanel = new MenuSolverPanel(this);
        menuSolverPanel.setBounds(350, marging + 3, 330, 40);
        add(menuSolverPanel);

        setupPanel = new SetupSolverPanel(this);
        setupPanel.setBounds(marging, marging, 330, 320);
        add(setupPanel);

        tokenListPanel = new TokenListPanel(this);
        tokenListPanel.setBounds(350, 60, 330, 270);
        add(tokenListPanel);

        engineManagerPanel = new EngineManagerPanel(this);
        engineManagerPanel.setBounds(marging, 340, 330, 240);
        add(engineManagerPanel);

        statisticsPanel = new StatisticsPanel();
        statisticsPanel.setBounds(350, 340, 330, 240);
        add(statisticsPanel);

        resultPanel = new ResultPanel(this);
        resultPanel.setBounds(690, marging, 500, 570);
        add(resultPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void resolve(File domainFile, Vector<File> problemFiles) {
        final double weight = (double) setupPanel.getWeightSpinner().getValue();
        final double timeout = (double) setupPanel.getTimeoutSpinner().getValue();

        final Planner planner = PlannerFactory.create(setupPanel.getPlanner(),
                setupPanel.getHeuristic(), weight, timeout);
        if (problemFiles != null && domainFile != null) {
            for (File file : problemFiles) {
                final Token token = new Token(domainFile, file, planner);

                if (token.isRunnable() && engineManagerPanel.isStatus()) {
                    if (!TokenList.getListModel().contains(token)) {
                        TokenList.getListModel().addElement(token);
                        queue.addToken(token);
                    }
                }

                engineManagerPanel.setTokensRemaining();
            }
        }
    }

    public void displayResult(Token token) {
        resultPanel.displayResult(token);
        statisticsPanel.displayStats(token.getResult().getStatistics());
        menuSolverPanel.getValButton().setEnabled(true);
        menuSolverPanel.getSaveJsonButton().setEnabled(true);
        menuSolverPanel.getSaveTxtButton().setEnabled(true);
    }

    public void displayError(Token token) {
        statisticsPanel.displayStats(token.getResult().getStatistics());
        resultPanel.clearResult();
        resultPanel.displayError(token);
    }

    public void displayProgress(Token token) {
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.diplayProgress(token);
    }

    public void clearResult() {
        resultPanel.clearResult();
        statisticsPanel.clearStats();
    }

    public void resetSolver() {
        clearResult();
        queue.clearList();
        TokenList.getListModel().clear();
    }
}
