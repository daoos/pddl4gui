package pddl4gui.gui.tools;

import pddl4gui.gui.Solver;
import pddl4gui.gui.panel.EngineManagerPanel;
import pddl4gui.gui.panel.InfoRestPanel;
import pddl4gui.gui.panel.MenuRestPanel;
import pddl4gui.gui.panel.MenuSolverPanel;
import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.SetupRestPanel;
import pddl4gui.gui.panel.SetupSolverPanel;
import pddl4gui.gui.panel.StatisticsPanel;
import pddl4gui.gui.panel.TokenListPanel;
import pddl4gui.token.Queue;
import pddl4gui.token.Token;

import java.awt.Color;
import java.io.Serializable;
import java.text.DecimalFormat;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 * This class implements the TriggerAction class of <code>PDDL4GUI</code>.
 * This class contains all the actions that swing components could handle and other usefull stuff.
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
     * The REST SetupRestPanel.
     */
    private static SetupRestPanel setupRestPanel;

    /**
     * The REST MenuRestPanel.
     */
    private static MenuRestPanel menuRestPanel;

    /**
     * The REST InfoRestPanel.
     */
    private static InfoRestPanel infoRestPanel;

    /**
     * The DecimalFormat to use in PDDL4GUI.
     */
    private static DecimalFormat decimalFormat;

    /**
     * The DefaultListModel used in JList in TokenList class.
     */
    private static final DefaultListModel<Token> listModel = new DefaultListModel<>();

    /**
     * The DefaultListModel used in JList in InfoRestPanel class.
     */
    private static final DefaultListModel<Integer> listRestModel = new DefaultListModel<>();

    /**
     * Gets the DecimalFormat to use with decimal number.
     *
     * @return the DecimalFormat to use with decimal number.
     */
    public static DecimalFormat getDf() {
        return TriggerAction.decimalFormat;
    }

    /**
     * Gets the DefaultListModel.
     *
     * @return the DefaultListModel.
     */
    public static DefaultListModel<Token> getListModel() {
        return listModel;
    }

    /**
     * Gets the DefaultListModel (REST).
     *
     * @return the DefaultListModel (REST).
     */
    public static DefaultListModel<Integer> getRestModel() {
        return listRestModel;
    }

    /**
     * Private constructor for static class.
     */
    private TriggerAction() {
    }

    /**
     * Setups all the panel used in this class (Local).
     *
     * @param setupPanel         the solver SetupPanel.
     * @param statisticsPanel    the solver StatisticsPanel.
     * @param resultPanel        the solver ResultPanel.
     * @param menuSolverPanel    the solver MenuSolverPanel.
     * @param engineManagerPanel the solver EngineManagerPanel.
     * @param tokenListPanel     the solver TokenListPanel.
     */
    public static void setupPanel(SetupSolverPanel setupPanel, StatisticsPanel statisticsPanel,
                                  ResultPanel resultPanel, MenuSolverPanel menuSolverPanel,
                                  EngineManagerPanel engineManagerPanel, TokenListPanel tokenListPanel) {
        TriggerAction.setupPanel = setupPanel;
        TriggerAction.statisticsPanel = statisticsPanel;
        TriggerAction.resultPanel = resultPanel;
        TriggerAction.menuSolverPanel = menuSolverPanel;
        TriggerAction.engineManagerPanel = engineManagerPanel;
        TriggerAction.tokenListPanel = tokenListPanel;
        TriggerAction.decimalFormat = new DecimalFormat("##.###");
    }

    /**
     * Setups all the panel used in this class (REST).
     *
     * @param setupRestPanel  the REST SetupRestPanel.
     * @param resultPanel     the REST ResultPanel.
     * @param menuRestPanel   the REST MenuSolverPanel.
     * @param infoRestPanel   the REST InfoRestPanel
     *
     */
    public static void setupPanel(SetupRestPanel setupRestPanel, ResultPanel resultPanel,
                                  MenuRestPanel menuRestPanel, InfoRestPanel infoRestPanel) {
        TriggerAction.setupRestPanel = setupRestPanel;
        TriggerAction.resultPanel = resultPanel;
        TriggerAction.menuRestPanel = menuRestPanel;
        TriggerAction.infoRestPanel = infoRestPanel;
        TriggerAction.decimalFormat = new DecimalFormat("##.###");
    }

    /**
     * Sets the Solver.
     *
     * @param solver the solver.
     */
    public static void setupSolver(Solver solver) {
        TriggerAction.solver = solver;
    }

    /**
     * Triggers display action for results of a specified token.
     *
     * @param token the specified token.
     */
    public static void displayResult(Token token) {
        resultPanel.displayResult(token);
        statisticsPanel.displayStats(token.getResult().getStatistics());
        menuSolverPanel.getValButton().setEnabled(true);
        menuSolverPanel.getSaveJsonButton().setEnabled(true);
        menuSolverPanel.getSaveTxtButton().setEnabled(true);
    }

    /**
     * Triggers display action for results of a specified token.
     *
     * @param string the string to display.
     */
    public static void displayResult(String string) {
        resultPanel.displayResult(string);
    }

    /**
     * Triggers display action for error of a specified token.
     *
     * @param token the specified token.
     */
    public static void displayError(Token token) {
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.displayError(token);
    }

    /**
     * Triggers display action for progress of a specified token.
     *
     * @param token the specified token.
     */
    public static void displayProgress(Token token) {
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.diplayProgress(token);
    }

    /**
     * Triggers clean action for ResultPanel and StatisticsPanel.
     */
    public static void clearResult() {
        resultPanel.clearResult();
        statisticsPanel.clearStats();
    }

    /**
     * Triggers reset action for the solver.
     */
    public static void resetSolver() {
        TriggerAction.clearResult();
        solver.getQueue().clearList();
        TriggerAction.getListModel().clear();
    }

    /**
     * Returns the Jlist of the TokenListPanel.
     *
     * @return the Jlist of the TokenListPanel.
     */
    public static JList<Token> getTokenListPanelJList() {
        return TriggerAction.tokenListPanel.getTokenJList();
    }

    /**
     * Returns the selected token of the JList in the TokenListPanel.
     *
     * @return the selected token of the JList in the TokenListPanel.
     */
    public static Token getTokenListPanelJListSelectedValue() {
        return TriggerAction.tokenListPanel.getTokenJList().getSelectedValue();
    }

    /**
     * Returns the solving status of the selected token of the JList in the TokenListPanel.
     *
     * @return the solving status of the selected token of the JList in the TokenListPanel.
     */
    public static boolean isTokenListPanelJListSelectedValueSolved() {
        return TriggerAction.tokenListPanel.getTokenJList().getSelectedValue().isSolved();
    }

    /**
     * Returns the status of the ResultPanel checkbox for detailed plan.
     *
     * @return the status of the ResultPanel checkbox for detailed plan.
     */
    public static boolean isResultPanelDetailedPlan() {
        return TriggerAction.resultPanel.isCheckboxSelected();
    }

    /**
     * Enables buttons in the MenuSolverPanel.
     *
     * @param state the status of the button.
     */
    public static void enableMenuSolverPanelButton(boolean state) {
        TriggerAction.menuSolverPanel.getValButton().setEnabled(state);
        TriggerAction.menuSolverPanel.getSaveTxtButton().setEnabled(state);
        TriggerAction.menuSolverPanel.getSaveJsonButton().setEnabled(state);
    }

    /**
     * Enables VAL button in the MenuSolverPanel.
     *
     * @param state the status of the button.
     */
    public static void enableMenuSolverPanelValButton(boolean state) {
        TriggerAction.menuSolverPanel.getValButton().setEnabled(state);
    }

    /**
     * Gets the Queue of the Solver.
     *
     * @return the Queue of the Solver.
     */
    public static Queue getQueue() {
        return TriggerAction.solver.getQueue();
    }

    /**
     * Gets the number of remaining tokens in the Queue of the Solver.
     *
     * @return the number of remaining tokens in the Queue of the Solver.
     */
    public static int getRemainningTokenInQueue() {
        return TriggerAction.solver.getQueue().remainingTokens();
    }

    /**
     * Sets the number of remaining tokens in the Queue of the Solver.
     */
    public static void setTokenRemainingEngineManagerPanel() {
        TriggerAction.engineManagerPanel.setTokensRemaining();
    }

    /**
     * Gets if the EngineManager is running.
     *
     * @return the status of the EngineManager.
     */
    public static boolean isEngineManagerRunning() {
        return TriggerAction.engineManagerPanel.isRunning();
    }

    /**
     * Returns the status of the REST API.
     *
     * @return the status of the REST API.
     */
    public static boolean isRestAlive() {
        return TriggerAction.infoRestPanel.getRestStatus() == Color.GREEN;
    }

    /**
     * Sets the status of the REST API.
     *
     * @param status the status of the REST API.
     */
    public static void setRestStatus(final String status) {
        TriggerAction.infoRestPanel.setStatus(status);
    }
}