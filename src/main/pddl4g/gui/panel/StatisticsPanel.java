package main.pddl4g.gui.panel;

import main.pddl4g.model.Statistics;

import javax.swing.*;
import java.text.DecimalFormat;

public class StatisticsPanel extends JPanel {

    private JLabel timeToParse, timeToEncode, timeToPlan, timeTotal, numberActions,
            numberFluents, memoryPb, memorySearch, memoryTotal;

    public StatisticsPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver statistics"));

        int labWidth = 140;
        int labHeight = 20;
        int labMarging = 20;

        JLabel timeToParseLabel = new JLabel("Time to parse");
        JLabel timeToEncodeLabel = new JLabel("Time to encode");
        JLabel timeToPlanLabel = new JLabel("Time to find plan");
        JLabel timeTotalLabel = new JLabel("Total time");
        JLabel numberActionsLabel = new JLabel("Number of actions");
        JLabel numberFluentsLabel = new JLabel("Number of facts");
        JLabel memoryPBLabel = new JLabel("Memory for problem");
        JLabel memorySearchLabel = new JLabel("Memory for search");
        JLabel memoryTotalLabel = new JLabel("Total memory used");
        timeToParse = new JLabel("-- seconds");
        timeToEncode = new JLabel("-- seconds");
        timeToPlan = new JLabel("-- seconds");
        timeTotal = new JLabel("-- seconds");
        numberActions = new JLabel("--");
        numberFluents = new JLabel("--");
        memoryPb = new JLabel("-- MBytes");
        memorySearch = new JLabel("-- MBytes");
        memoryTotal = new JLabel("-- MBytes");

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

    public void displayStats(Statistics statistics) {
        DecimalFormat df = new DecimalFormat("#.###");
        timeToParse.setText(String.valueOf(df.format(statistics.getTimeToParseInSeconds()) + " seconds"));
        timeToEncode.setText(String.valueOf(df.format(statistics.getTimeToEncodeInSeconds()) + " seconds"));
        timeToPlan.setText(String.valueOf(df.format(statistics.getTimeToPlanInSeconds()) + " seconds"));
        timeTotal.setText(String.valueOf(df.format(statistics.getTotalTimeInSeconds()) + " seconds"));
        numberActions.setText(String.valueOf(statistics.getNumberOfActions()));
        numberFluents.setText(String.valueOf(statistics.getNumberOfFluents()));
        memoryPb.setText(String.valueOf(df.format(statistics.getMemoryForProblemInMBytes())) + " MBytes");
        memorySearch.setText(String.valueOf(df.format(statistics.getMemoryUsedToSearchInMBytes())) + " MBytes");
        memoryTotal.setText(String.valueOf(df.format(statistics.getTotalMemoryInMBytes())) + " MBytes");
    }

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
