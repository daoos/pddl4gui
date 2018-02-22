package main.pddl4g.gui;

import main.pddl4g.context.planner.Planner;
import main.pddl4g.gui.panel.EngineStatusPanel;
import main.pddl4g.gui.panel.SetupSolverPanel;
import main.pddl4g.gui.tools.WindowsManager;
import main.pddl4g.Pddl4G;
import main.pddl4g.context.planner.EHCContext;
import main.pddl4g.context.planner.FFContext;
import main.pddl4g.context.planner.HSPContext;
import main.pddl4g.gui.panel.MenuSolverPanel;
import main.pddl4g.gui.panel.ResultPanel;
import main.pddl4g.gui.panel.StatisticsPanel;
import main.pddl4g.model.Token;

import javax.swing.*;

public class Solver extends JFrame {

    private Pddl4G pddl4G;

    private SetupSolverPanel setupPanel;
    private StatisticsPanel statisticsPanel;
    private ResultPanel resultPanel;
    private MenuSolverPanel menuSolverPanel;
    private EngineStatusPanel engineStatusPanel;

    private JButton planButton;

    private Token token;

    public SetupSolverPanel getSetupPanel() {
        return setupPanel;
    }

    public StatisticsPanel getStatisticsPanel() {
        return statisticsPanel;
    }

    public ResultPanel getResultPanel() {
        return resultPanel;
    }

    public MenuSolverPanel getMenuSolverPanel() {
        return menuSolverPanel;
    }

    public EngineStatusPanel getEngineStatusPanel() {
        return engineStatusPanel;
    }

    public JButton getPlanButton() {
        return planButton;
    }

    public Token getToken() {
        return token;
    }

    public Solver(Pddl4G pddl4G) {
        this.pddl4G = pddl4G;
        int width = 860;
        int height = 620;
        int marging = 10;

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
        planButton.setBounds(75, 260, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> {
            resolve();
        });

        add(planButton);

        engineStatusPanel = new EngineStatusPanel();
        engineStatusPanel.setBounds(75, 300, 200, 50);
        add(engineStatusPanel);

        statisticsPanel = new StatisticsPanel();
        statisticsPanel.setBounds(marging, 360, 330, 220);
        add(statisticsPanel);

        resultPanel = new ResultPanel();
        resultPanel.setBounds(350, marging, 500, 570);
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

        token = new Token(setupPanel.getDomainFile(), setupPanel.getProblemFile(), planner);

        if (token.isRunnable()) {
            pddl4G.getEngine().getTokenList().add(token);
            planButton.setEnabled(false);
            planButton.setText("In progress !");
        }

    }

    public void displayResult(boolean resolveStatus, String error) {
        if (resolveStatus) {
            resultPanel.displayResult(token);
            statisticsPanel.displayStats(token.getResult().getStatistics());
            planButton.setText("Resolve this problem !");
            planButton.setEnabled(true);
            menuSolverPanel.getValButton().setEnabled(true);
            menuSolverPanel.getSaveJsonButton().setEnabled(true);
            menuSolverPanel.getSaveTxtButton().setEnabled(true);
        } else {
            resultPanel.setText(error);
            planButton.setText("Error !");
            menuSolverPanel.getResetButton().setEnabled(true);
        }
    }
}
