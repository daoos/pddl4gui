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

    final private JButton planButton;

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

    public JButton getPlanButton() {
        return planButton;
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
        setupPanel.setBounds(marging, 60, 330, 190);
        add(setupPanel);

        planButton = new JButton("Resolve this problem !");
        planButton.setBounds(75, 265, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> resolve());
        add(planButton);

        engineStatusPanel = new EngineStatusPanel(this);
        engineStatusPanel.setBounds(marging, 300, 330, 260);
        add(engineStatusPanel);

        statisticsPanel = new StatisticsPanel();
        statisticsPanel.setBounds(860, marging, 330, 220);
        add(statisticsPanel);

        resultPanel = new ResultPanel();
        resultPanel.setBounds(350, marging, 500, 550);
        add(resultPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void resolve() {
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
        planButton.setText("Resolve this problem !");
        planButton.setEnabled(true);
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
