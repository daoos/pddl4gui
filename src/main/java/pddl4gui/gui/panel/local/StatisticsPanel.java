package pddl4gui.gui.panel.local;

import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.Statistics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class implements the StatisticsPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays statistics about planner solving process.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class StatisticsPanel extends JPanel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JLabel of the StatisticsPanel.
     */
    private final JLabel timeToParse;
    private final JLabel timeToEncode;
    private final JLabel timeToPlan;
    private final JLabel timeTotal;
    private final JLabel numberActions;
    private final JLabel numberFluents;
    private final JLabel memoryPb;
    private final JLabel memorySearch;
    private final JLabel memoryTotal;

    /**
     * Creates a new StatisticsPanel.
     */
    public StatisticsPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver statistics"));

        final JLabel timeToParseLabel = new JLabel("Time to parse");
        final JLabel timeToEncodeLabel = new JLabel("Time to encode");
        final JLabel timeToPlanLabel = new JLabel("Time to find plan");
        final JLabel timeTotalLabel = new JLabel("Total time");
        final JLabel numberActionsLabel = new JLabel("Number of actions");
        final JLabel numberFluentsLabel = new JLabel("Number of facts");
        final JLabel memoryPBLabel = new JLabel("Memory for problem");
        final JLabel memorySearchLabel = new JLabel("Memory for search");
        final JLabel memoryTotalLabel = new JLabel("Total memory used");
        timeToParse = new JLabel("-- seconds");
        timeToEncode = new JLabel("-- seconds");
        timeToPlan = new JLabel("-- seconds");
        timeTotal = new JLabel("-- seconds");
        numberActions = new JLabel("--");
        numberFluents = new JLabel("--");
        memoryPb = new JLabel("-- MBytes");
        memorySearch = new JLabel("-- MBytes");
        memoryTotal = new JLabel("-- MBytes");

        final int labWidth = 140;
        final int labHeight = 22;
        int labMarging = 22;

        timeToParseLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(timeToParseLabel);
        timeToParse.setBounds(155, labMarging, labWidth, labHeight);
        add(timeToParse);
        labMarging += labHeight;

        timeToEncodeLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(timeToEncodeLabel);
        timeToEncode.setBounds(155, labMarging, labWidth, labHeight);
        add(timeToEncode);
        labMarging += labHeight;

        timeToPlanLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(timeToPlanLabel);
        timeToPlan.setBounds(155, labMarging, labWidth, labHeight);
        add(timeToPlan);
        labMarging += labHeight;

        timeTotalLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(timeTotalLabel);
        timeTotal.setBounds(155, labMarging, labWidth, labHeight);
        add(timeTotal);
        labMarging += labHeight;

        numberActionsLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(numberActionsLabel);
        numberActions.setBounds(155, labMarging, labWidth, labHeight);
        add(numberActions);
        labMarging += labHeight;

        numberFluentsLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(numberFluentsLabel);
        numberFluents.setBounds(155, labMarging, labWidth, labHeight);
        add(numberFluents);
        labMarging += labHeight;

        memoryPBLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(memoryPBLabel);
        memoryPb.setBounds(155, labMarging, labWidth, labHeight);
        add(memoryPb);
        labMarging += labHeight;

        memorySearchLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(memorySearchLabel);
        memorySearch.setBounds(155, labMarging, labWidth, labHeight);
        add(memorySearch);
        labMarging += labHeight;

        memoryTotalLabel.setBounds(15, labMarging, labWidth, labHeight);
        add(memoryTotalLabel);
        memoryTotal.setBounds(155, labMarging, labWidth, labHeight);
        add(memoryTotal);
    }

    /**
     * Displays statistics for a specified Statistics object.
     *
     * @param statistics the Statistics object.
     */
    public void displayStats(Statistics statistics) {
        timeToParse.setText(TriggerAction.getDf()
                .format(statistics.getTimeToParseInSeconds()) + " seconds");
        timeToEncode.setText(TriggerAction.getDf()
                .format(statistics.getTimeToEncodeInSeconds()) + " seconds");
        timeToPlan.setText(TriggerAction.getDf()
                .format(statistics.getTimeToPlanInSeconds()) + " seconds");
        timeTotal.setText(TriggerAction.getDf()
                .format(statistics.getTotalTimeInSeconds()) + " seconds");
        numberActions.setText(String.valueOf(statistics.getNumberOfActions()));
        numberFluents.setText(String.valueOf(statistics.getNumberOfFluents()));
        memoryPb.setText(TriggerAction.getDf()
                .format(statistics.getMemoryForProblemInMBytes()) + " MBytes");
        memorySearch.setText(TriggerAction.getDf()
                .format(statistics.getMemoryUsedToSearchInMBytes()) + " MBytes");
        memoryTotal.setText(TriggerAction.getDf()
                .format(statistics.getTotalMemoryInMBytes()) + " MBytes");
    }

    /**
     * Clears the StatisticsPanel.
     */
    public void clearStats() {
        timeToParse.setText("-- seconds");
        timeToEncode.setText("-- seconds");
        timeToPlan.setText("-- seconds");
        timeTotal.setText("-- seconds");
        numberActions.setText("--");
        numberFluents.setText("--");
        memoryPb.setText("-- MBytes");
        memorySearch.setText("-- MBytes");
        memoryTotal.setText("-- MBytes");
    }
}
