package pddl4gui.gui.tools;

import pddl4gui.gui.Result;
import pddl4gui.gui.Solver;
import pddl4gui.gui.panel.local.EngineManagerPanel;
import pddl4gui.gui.panel.local.MenuSolverPanel;
import pddl4gui.gui.panel.local.StatisticsPanel;
import pddl4gui.gui.panel.local.TokenListPanel;
import pddl4gui.gui.panel.rest.InfoRestPanel;
import pddl4gui.gui.panel.rest.MenuRestPanel;
import pddl4gui.token.LocalToken;
import pddl4gui.token.Queue;
import pddl4gui.token.RestToken;

import java.awt.Color;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Vector;
import javax.swing.DefaultListModel;

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
     * Status of PDDL4GUI.
     */
    private static boolean isRunning = false;

    /**
     * The solver JFrame.
     */
    private static Solver solver;

    /**
     * The solver StatisticsPanel.
     */
    private static StatisticsPanel statisticsPanel;

    /**
     * The solver ResultPanel.
     */
    private static Result resultFrame;

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
    private static final DefaultListModel<LocalToken> listModel = new DefaultListModel<>();

    /**
     * The DefaultListModel used in JList in InfoRestPanel class (REST).
     */
    private static final DefaultListModel<Integer> listRestModel = new DefaultListModel<>();

    /**
     * The Vector list to store RestToken.
     */
    private static final Vector<RestToken> restTokenList = new Vector<>();

    /**
     * Gets status of PDDL4GUI.
     *
     * @return the status of PDDL4GUI.
     */
    public static boolean isPDDL4GUIRunning() {
        return isRunning;
    }

    /**
     * Sets the status of PDDL4GUI.
     *
     * @param isRunning the status of PDDL4GUI.
     */
    public static void setPDDL4GUIRunning(boolean isRunning) {
        TriggerAction.isRunning = isRunning;
    }

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
    public static DefaultListModel<LocalToken> getListModel() {
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
     * Gets the Vector list of RestToken (REST).
     *
     * @return the Vector list of RestToken (REST).
     */
    public static Vector<RestToken> getRestTokenList() {
        return restTokenList;
    }

    /**
     * Gets the RestToken according to the computation id (REST).
     *
     * @return the RestToken according to the computation id (REST).
     */
    public static RestToken getRestTokenFromId(final int id) {
        RestToken returnedToken = null;
        for (RestToken token : restTokenList) {
            if (token.getId() == id) {
                returnedToken = token;
            }
        }
        return returnedToken;
    }

    /**
     * Private constructor for static class.
     */
    private TriggerAction() {
    }

    /**
     * Setups all the panel used in this class (Local).
     *
     * @param statisticsPanel    the solver StatisticsPanel.
     * @param resultFrame        the solver ResultPanel.
     * @param menuSolverPanel    the solver MenuSolverPanel.
     * @param engineManagerPanel the solver EngineManagerPanel.
     * @param tokenListPanel     the solver TokenListPanel.
     */
    public static void setupPanel(StatisticsPanel statisticsPanel, Result resultFrame,
                                  MenuSolverPanel menuSolverPanel, EngineManagerPanel engineManagerPanel,
                                  TokenListPanel tokenListPanel) {
        TriggerAction.statisticsPanel = statisticsPanel;
        TriggerAction.resultFrame = resultFrame;
        TriggerAction.menuSolverPanel = menuSolverPanel;
        TriggerAction.engineManagerPanel = engineManagerPanel;
        TriggerAction.tokenListPanel = tokenListPanel;
        TriggerAction.decimalFormat = new DecimalFormat("##.###");
    }

    /**
     * Setups all the panel used in this class (REST).
     *
     * @param resultFrame     the REST ResultPanel.
     * @param menuRestPanel   the REST MenuSolverPanel.
     * @param infoRestPanel   the REST InfoRestPanel
     *
     */
    public static void setupPanel(Result resultFrame, MenuRestPanel menuRestPanel, InfoRestPanel infoRestPanel) {
        TriggerAction.resultFrame = resultFrame;
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
    public static void displayResult(LocalToken token) {
        resultFrame.displayResult(token);
        statisticsPanel.displayStats(token.getStatistics());
        menuSolverPanel.enableValButton(true);
        menuSolverPanel.enableSaveTxtButton(true);
        menuSolverPanel.enableSaveJsonButton(true);
    }

    /**
     * Triggers display action for results of a specified token.
     *
     * @param string the string to display.
     */
    public static void displayResult(String string) {
        resultFrame.displayResult(string);
    }

    /**
     * Triggers display action to get result from the JTextArea.
     *
     * @return The String result.
     */
    public static String getResult() {
        return resultFrame.getResult();
    }

    /**
     * Triggers display action for error of a specified token.
     *
     * @param token the specified token.
     */
    public static void displayError(LocalToken token) {
        statisticsPanel.clearStats();
        statisticsPanel.displayStats(token.getStatistics());
        resultFrame.clearResult();
        resultFrame.displayError(token);
    }

    /**
     * Triggers display action for progress of a specified token.
     *
     * @param token the specified token.
     */
    public static void displayProgress(LocalToken token) {
        statisticsPanel.clearStats();
        resultFrame.clearResult();
        resultFrame.diplayProgress(token);
    }

    /**
     * Triggers clean action for ResultPanel.
     */
    public static void clearResult() {
        resultFrame.clearResult();
    }

    /**
     * Triggers clean action for StatisticsPanel.
     */
    public static void clearStats() {
        statisticsPanel.clearStats();
    }

    /**
     * Triggers reset action for the solver.
     */
    public static void resetSolver() {
        TriggerAction.clearResult();
        TriggerAction.clearStats();
        solver.getQueue().clearList();
        TriggerAction.getListModel().clear();
    }

    /**
     * Returns the selected token of the JList in the TokenListPanel.
     *
     * @return the selected token of the JList in the TokenListPanel.
     */
    public static LocalToken getTokenListPanelJListSelectedValue() {
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
        return TriggerAction.resultFrame.isCheckboxSelected();
    }

    /**
     * Enables buttons in the MenuSolverPanel.
     *
     * @param state the status of the button.
     */
    public static void enableMenuSolverPanelButton(boolean state) {
        TriggerAction.menuSolverPanel.enableValButton(state);
        TriggerAction.menuSolverPanel.enableSaveTxtButton(state);
        TriggerAction.menuSolverPanel.enableSaveJsonButton(state);
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

    /**
     * Enables or disables the result's save button (REST).
     *
     * @param status the status of the save button (REST).
     */
    public static void enableSaveTxtResultRest(final boolean status) {
        menuRestPanel.enableSaveTxtButton(status);
    }

    /**
     * Enables or disables the result's save button (REST).
     *
     * @param status the status of the save button (REST).
     */
    public static void enableSaveJsonResultRest(final boolean status) {
        menuRestPanel.enableSaveJsonButton(status);
    }

    /**
     * Enables or disables the Val button (REST).
     *
     * @param status the status of the Val button (REST).
     */
    public static void enableValRest(final boolean status) {
        menuRestPanel.enableValButton(status);
    }

    /**
     * Gets the current computation id (REST).
     *
     * @return the current computation id (REST).
     */
    public static int getCurrentComputationId() {
        return infoRestPanel.getCurrentComputationId();
    }

    /**
     * Returns the URL of the REST service (REST).
     *
     * @return the URL of the REST service (REST).
     */
    public static String getRestURL() {
        return infoRestPanel.getUrl();
    }

    /**
     * Calling the Java Garbage Collector.
     */
    public static void gc() {
        Runtime.getRuntime().gc();
    }

    /**
     * Check if the OS is Windows.
     *
     * @return true if the OS is Windows, false otherwise.
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase(new Locale("en", "EN")).contains("win");
    }
}