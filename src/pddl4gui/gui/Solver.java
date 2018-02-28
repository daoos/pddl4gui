package pddl4gui.gui;

import pddl4gui.Pddl4Gui;
import pddl4gui.context.planner.EHCContext;
import pddl4gui.context.planner.FFContext;
import pddl4gui.context.planner.HSPContext;
import pddl4gui.context.planner.Planner;
import pddl4gui.gui.panel.EngineStatusPanel;
import pddl4gui.gui.panel.MenuSolverPanel;
import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.SetupSolverPanel;
import pddl4gui.gui.panel.StatisticsPanel;
import pddl4gui.gui.panel.TokenListPanel;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;
import pddl4gui.token.TokenList;

import javax.swing.*;

//import pddl4gui.context.planner.FFAnytimeContext;
//import pddl4gui.context.planner.HCAnytimeContext;

public class Solver extends JFrame {

    final private Pddl4Gui pddl4Gui;

    final private SetupSolverPanel setupPanel;
    final private StatisticsPanel statisticsPanel;
    final private ResultPanel resultPanel;
    final private MenuSolverPanel menuSolverPanel;
    final private EngineStatusPanel engineStatusPanel;
    final private TokenListPanel tokenListPanel;

    public Pddl4Gui getPddl4Gui() {
        return pddl4Gui;
    }

    public SetupSolverPanel getSetupPanel() {
        return setupPanel;
    }

    public StatisticsPanel getStatisticsPanel() {
        return statisticsPanel;
    }

    public ResultPanel getResultPanel() {
        return resultPanel;
    }

    public EngineStatusPanel getEngineStatusPanel() {
        return engineStatusPanel;
    }

    public TokenListPanel getTokenListPanel() {
        return tokenListPanel;
    }

    public Solver(Pddl4Gui pddl4Gui) {
        this.pddl4Gui = pddl4Gui;

        final int width = 1200;
        final int height = 600;
        final int marging = 10;

        setLayout(null);
        setSize(width, height);
        setTitle(WindowsManager.NAME + " " + WindowsManager.VERSION);
        WindowsManager.setPoint(this.getLocation());
        WindowsManager.setWidth(width);

        menuSolverPanel = new MenuSolverPanel(this);
        menuSolverPanel.setBounds(marging, marging, 330, 40);
        add(menuSolverPanel);

        setupPanel = new SetupSolverPanel(this);
        setupPanel.setBounds(marging, 60, 330, 350);
        add(setupPanel);

        tokenListPanel = new TokenListPanel(this);
        tokenListPanel.setBounds(350, 260, 330, 300);
        add(tokenListPanel);

        engineStatusPanel = new EngineStatusPanel();
        engineStatusPanel.setBounds(marging, 420, 330, 140);
        add(engineStatusPanel);

        statisticsPanel = new StatisticsPanel();
        statisticsPanel.setBounds(350, marging, 330, 240);
        add(statisticsPanel);

        resultPanel = new ResultPanel();
        resultPanel.setBounds(690, marging, 500, 550);
        add(resultPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void resolve() {
        Planner planner = null;

        if (setupPanel.getPlanner() == Planner.Type.FF) {
            planner = new FFContext(setupPanel.getHeuristic(),
                    (double) setupPanel.getWeightSpinner().getValue(),
                    (double) setupPanel.getTimeoutSpinner().getValue());
        }
        if (setupPanel.getPlanner() == Planner.Type.FFAnytime) {
            System.out.println("Available with PDDL4J 4.0");
            /*planner = new FFAnytimeContext(setupPanel.getHeuristic(),
                    (double) setupPanel.getWeightSpinner().getValue(),
                    (double) setupPanel.getTimeoutSpinner().getValue());*/
        }
        if (setupPanel.getPlanner() == Planner.Type.HSP) {
            planner = new HSPContext(setupPanel.getHeuristic(),
                    (double) setupPanel.getWeightSpinner().getValue(),
                    (double) setupPanel.getTimeoutSpinner().getValue());
        }
        if (setupPanel.getPlanner() == Planner.Type.EHC) {
            planner = new EHCContext(setupPanel.getHeuristic(),
                    (double) setupPanel.getWeightSpinner().getValue(),
                    (double) setupPanel.getTimeoutSpinner().getValue());
        }
        if (setupPanel.getPlanner() == Planner.Type.HCAnytime) {
            System.out.println("Available with PDDL4J 4.0");
            /*planner = new HCAnytimeContext(setupPanel.getHeuristic(),
                    (double) setupPanel.getWeightSpinner().getValue(),
                    (double) setupPanel.getTimeoutSpinner().getValue());*/
        }

        final Token token = new Token(setupPanel.getDomainFile(), setupPanel.getProblemFile(), planner);

        if (token.isRunnable()) {
            if (!TokenList.getListModel().contains(token)) {
                TokenList.getListModel().addElement(token);
                pddl4Gui.getEngine().getTokenList().addLast(token);
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
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.displayError(token);
    }

    public void displayProgress(Token token) {
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.diplayProgress(token);
    }
}
