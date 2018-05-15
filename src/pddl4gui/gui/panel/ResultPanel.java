package pddl4gui.gui.panel;

import pddl4gui.gui.tools.DecimalFormatSetup;
import pddl4gui.token.Token;

import javax.swing.*;

public class ResultPanel extends JPanel {

    final private JLabel domain, problem, cost, depth, planner;
    final private JTextArea resultArea;

    public ResultPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver result"));

        final int labWidth = 120;
        final int labHeight = 20;
        int labMarging = 20;

        final JLabel domainLabel = new JLabel("Domain");
        final JLabel problemLabel = new JLabel("Problem");
        final JLabel depthLabel = new JLabel("Depth");
        final JLabel costLabel = new JLabel("Cost");
        final JLabel plannerLabel = new JLabel("Planner");
        domain = new JLabel("---");
        problem = new JLabel("---");
        cost = new JLabel("---");
        depth = new JLabel("---");
        planner = new JLabel("---");

        domainLabel.setBounds(15, labMarging, labWidth, labHeight);
        domain.setBounds(70, labMarging, labWidth, labHeight);
        add(domainLabel);
        add(domain);

        costLabel.setBounds(210, labMarging, labWidth, labHeight);
        cost.setBounds(265, labMarging, labWidth / 2, labHeight);
        add(costLabel);
        add(cost);

        plannerLabel.setBounds(360, labMarging, labWidth, labHeight);
        planner.setBounds(415, labMarging, labWidth / 2, labHeight);
        add(plannerLabel);
        add(planner);

        labMarging += labHeight;

        problemLabel.setBounds(15, labMarging, labWidth, labHeight);
        problem.setBounds(70, labMarging, labWidth, labHeight);
        add(problemLabel);
        add(problem);

        depthLabel.setBounds(210, labMarging, labWidth, labHeight);
        depth.setBounds(265, labMarging, labWidth / 2, labHeight);
        add(depthLabel);
        add(depth);

        labMarging += labHeight * 1.75;

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        final JScrollPane scrollTextPane = new JScrollPane(resultArea);
        scrollTextPane.setBounds(15, labMarging, 470, 470);
        add(scrollTextPane);
    }

    public void displayResult(Token token) {
        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(DecimalFormatSetup.getDf()
                .format(token.getResult().getStatistics().getCost())));
        depth.setText(String.valueOf(DecimalFormatSetup.getDf()
                .format(token.getResult().getStatistics().getDepth())));
        resultArea.setText(token.getResult().getSolutionString());
        planner.setText(token.getPlanner().getType().toString());
    }

    public void displayError(Token token) {
        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(0));
        depth.setText(String.valueOf(0));
        planner.setText(token.getPlanner().getType().toString());
        resultArea.setText(token.getError());
    }

    public void diplayProgress(Token token) {
        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(0));
        depth.setText(String.valueOf(0));
        planner.setText(token.getPlanner().getType().toString());
        resultArea.setText("Token not solved yet !");
    }

    public void clearResult() {
        domain.setText("--");
        problem.setText("---");
        cost.setText("---");
        depth.setText("---");
        planner.setText("---");
        resultArea.setText("");
    }
}
