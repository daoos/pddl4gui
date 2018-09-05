package pddl4gui.gui;

import pddl4gui.gui.panel.EngineManagerPanel;
import pddl4gui.gui.panel.MenuSolverPanel;
import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.SetupSolverPanel;
import pddl4gui.gui.panel.StatisticsPanel;
import pddl4gui.gui.panel.TokenListPanel;
import pddl4gui.gui.tools.TokenList;
import pddl4gui.token.Token;

import javax.swing.JList;
import java.io.Serializable;

/**
 * This class implements the TriggerAction class of <code>PDDL4GUI</code>.
 * This class contains all the action that swing components could handle.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class TriggerAction implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The solver JFrame.
     */
    private static Solver solver;

    /**
     * The editor JFrame.
     */
    private static Editor editor;

    /**
     * The solver SetupPanel.
     */
    private static SetupSolverPanel setupPanel;

    /**
     * The solver StatisticsPanel.
     */
    private static StatisticsPanel statisticsPanel;

    /**
     * The solver ResultPanel.
     */
    private static ResultPanel resultPanel;

    /**
     * The solver MenuSolverPanel.
     */
    private static MenuSolverPanel menuSolverPanel;

    /**
     * The solver EngineManagerPanel.
     */
    private static EngineManagerPanel engineManagerPanel;

    /**
     * The solver TokenListPanel.
     */
    private static TokenListPanel tokenListPanel;

    /**
     *
     * @return
     */
    public static Solver getSolver() {
        return solver;
    }

    /**
     *
     * @return
     */
    public static Editor getEditor() {
        return editor;
    }

    /**
     *
     * @return
     */
    public static SetupSolverPanel getSetupPanel() {
        return setupPanel;
    }

    /**
     *
     * @return
     */
    public static StatisticsPanel getStatisticsPanel() {
        return statisticsPanel;
    }

    /**
     *
     * @return
     */
    public static ResultPanel getResultPanel() {
        return resultPanel;
    }

    /**
     *
     * @return
     */
    public static MenuSolverPanel getMenuSolverPanel() {
        return menuSolverPanel;
    }

    /**
     *
     * @return
     */
    public static EngineManagerPanel getEngineManagerPanel() {
        return engineManagerPanel;
    }

    /**
     *
     * @return
     */
    public static TokenListPanel getTokenListPanel() {
        return tokenListPanel;
    }

    /**
     * Private constructor for static class.
     */
    private TriggerAction() {
    }

    /**
     * Setups all the pane used in this class.
     *
     * @param setupPanel the solver SetupPanel.
     * @param statisticsPanel the solver StatisticsPanel.
     * @param resultPanel the solver ResultPanel.
     * @param menuSolverPanel the solver MenuSolverPanel.
     * @param engineManagerPanel the solver EngineManagerPanel.
     * @param tokenListPanel the solver TokenListPanel.
     */
    public static void setup(Solver solver, SetupSolverPanel setupPanel, StatisticsPanel statisticsPanel,
                             ResultPanel resultPanel, MenuSolverPanel menuSolverPanel,
                             EngineManagerPanel engineManagerPanel, TokenListPanel tokenListPanel) {
        TriggerAction.solver = solver;
        TriggerAction.setupPanel = setupPanel;
        TriggerAction.statisticsPanel = statisticsPanel;
        TriggerAction.resultPanel = resultPanel;
        TriggerAction.menuSolverPanel = menuSolverPanel;
        TriggerAction.engineManagerPanel = engineManagerPanel;
        TriggerAction.tokenListPanel = tokenListPanel;
    }

    /**
     *
     * @param token
     */
    public static void displayResult(Token token) {
        resultPanel.displayResult(token);
        statisticsPanel.displayStats(token.getResult().getStatistics());
        menuSolverPanel.getValButton().setEnabled(true);
        menuSolverPanel.getSaveJsonButton().setEnabled(true);
        menuSolverPanel.getSaveTxtButton().setEnabled(true);
    }

    /**
     *
     * @param token
     */
    public static void displayError(Token token) {
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.displayError(token);
    }

    /**
     *
     * @param token
     */
    public static void displayProgress(Token token) {
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.diplayProgress(token);
    }

    /**
     *
     */
    public static void clearResult() {
        resultPanel.clearResult();
        statisticsPanel.clearStats();
    }

    /**
     *
     */
    public static void resetSolver() {
        TriggerAction.clearResult();
        solver.getQueue().clearList();
        TokenList.getListModel().clear();
    }

    /**
     *
     * @param status
     */
    public static void setSetupPanelDomainButton(final boolean status) {
        setupPanel.enableDomainButton(status);
    }

    /**
     *
     * @param status
     */
    public static void setSetupPanelProblemButton(final boolean status) {
        setupPanel.enablePBButton(status);
    }

    /**
     *
     * @return
     */
    public static JList<Token> getTokenListPanelJList() {
        return TriggerAction.tokenListPanel.getTokenJList();
    }

    /**
     *
     * @param state
     */
    public static void enableMenuSolverPanelButton(boolean state) {
        TriggerAction.menuSolverPanel.getValButton().setEnabled(state);
        TriggerAction.menuSolverPanel.getSaveTxtButton().setEnabled(state);
        TriggerAction.menuSolverPanel.getSaveJsonButton().setEnabled(state);
    }

}