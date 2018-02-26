package pddl4gui.gui;

import pddl4gui.Pddl4Gui;
import pddl4gui.context.planner.EHCContext;
import pddl4gui.context.planner.FFAnytimeContext;
import pddl4gui.context.planner.FFContext;
import pddl4gui.context.planner.HCAnytimeContext;
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

public class Solver extends JFrame {

    private Pddl4Gui pddl4Gui;

    private SetupSolverPanel setupPanel;
    private StatisticsPanel statisticsPanel;
    private ResultPanel resultPanel;
    private MenuSolverPanel menuSolverPanel;
    private EngineStatusPanel engineStatusPanel;

    private JButton planButton;

    private Token token;

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

    public Solver(Pddl4Gui pddl4Gui) {
        this.pddl4Gui = pddl4Gui;
        int width = 1200;
        int height = 600;
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
        planButton.setBounds(75, 265, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> {
            resolve();
        });

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
            planner = new FFAnytimeContext(setupPanel.getHeuristic(),
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
        if (setupPanel.getPlanner() == Planner.Type.HCAnytime) {
            planner = new HCAnytimeContext(setupPanel.getHeuristic(),
                    (double) setupPanel.getWeightSpinner().getValue(),
                    (double) setupPanel.getTimeoutSpinner().getValue());
        }

        token = new Token(setupPanel.getDomainFile(), setupPanel.getProblemFile(), planner);

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
