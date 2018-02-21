package solver.gui;

import solver.context.EHCContext;
import solver.context.Planner;
import solver.context.PlanningContext;
import solver.gui.panel.MenuSolverPanel;
import solver.gui.panel.ResultPanel;
import solver.gui.panel.SetupSolverPanel;
import solver.gui.panel.StatisticsPanel;
import solver.gui.tools.WindowsManager;
import solver.model.Token;
import solver.context.FFContext;
import solver.context.HSPContext;

import javax.swing.*;
import java.io.IOException;

public class Solver extends JFrame{
    private SetupSolverPanel setupPanel;
    private StatisticsPanel statisticsPanel;
    private ResultPanel resultPanel;
    private MenuSolverPanel menuSolverPanel;

    private JButton planButton;

    private Token token;

    public SetupSolverPanel getSetupPanel() {
        return setupPanel;
    }

    public MenuSolverPanel getMenuSolverPanel() {
        return menuSolverPanel;
    }

    public JButton getPlanButton() {
        return planButton;
    }

    public Token getToken() {
        return token;
    }

    public Solver() {
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
        planButton.setBounds(75, 290, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> {
            statisticsPanel.clearStats();
            resultPanel.clearResult();

            planButton.setEnabled(false);
            planButton.setText("In progress !");

            resolve();
        });

        add(planButton);

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
        try {
            Boolean resolveStatus = false;
            token = new Token(setupPanel.getDomainFile(),setupPanel.getProblemFile());
            if(token.isRunnable()){
                token.setupToken(setupPanel.getHeuristic(),
                        (double) setupPanel.getWeightSpinner().getValue(),
                        (double) setupPanel.getTimeoutSpinner().getValue());
                if(setupPanel.getPlanner() == Planner.Type.FF) {
                    final FFContext ffContext = new FFContext();
                    ffContext.setFF(token.getHeuristic(),token.getWeight(), token.getTimeout());
                    resolveStatus = PlanningContext.resolve(token,ffContext);
                }
                if(setupPanel.getPlanner() == Planner.Type.HSP) {
                    final HSPContext hspContext = new HSPContext();
                    hspContext.setHSP(token.getHeuristic(),token.getWeight(), token.getTimeout());
                    resolveStatus = PlanningContext.resolve(token,hspContext);
                }
                if(setupPanel.getPlanner() == Planner.Type.EHC) {
                    final EHCContext ehcContext = new EHCContext();
                    ehcContext.setEHC(token.getHeuristic(),token.getWeight(), token.getTimeout());
                    resolveStatus = PlanningContext.resolve(token,ehcContext);
                }
            }
            if (resolveStatus) {
                resultPanel.displayResult(token);
                statisticsPanel.displayStats(token.getResult().getStatistics());
                planButton.setText("Resolve this problem !");
                planButton.setEnabled(true);
                menuSolverPanel.getValButton().setEnabled(true);
                menuSolverPanel.getSaveJsonButton().setEnabled(true);
                menuSolverPanel.getSaveTxtButton().setEnabled(true);
            } else {
                resultPanel.setText(PlanningContext.getError());
                planButton.setText("Error !");
                menuSolverPanel.getResetButton().setEnabled(true);
            }
        } catch (IOException io) {
            System.out.println(io.toString());
        }
    }
}
