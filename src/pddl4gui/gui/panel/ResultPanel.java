package pddl4gui.gui.panel;

import pddl4gui.gui.tools.DecimalFormatSetup;
import pddl4gui.token.Token;

import javax.swing.*;

public class ResultPanel extends JPanel {

    final private JLabel domain, problem, cost, depth;
    final private JTextArea resultArea;

    public ResultPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver result"));

        final int labWidth = 140;
        final int labHeight = 20;
        int labMarging = 20;

        final JLabel domainLabel = new JLabel("Domain:");
        final JLabel problemLabel = new JLabel("Problem:");
        final JLabel depthLabel = new JLabel("Depth:");
        final JLabel costLabel = new JLabel("Cost:");
        domain = new JLabel("---");
        problem = new JLabel("---");
        cost = new JLabel("---");
        depth = new JLabel("---");

        domainLabel.setBounds(15, labMarging, labWidth, labHeight);
        domain.setBounds(80, labMarging, labWidth, labHeight);
        add(domainLabel);
        add(domain);

        problemLabel.setBounds(250, labMarging, labWidth, labHeight);
        problem.setBounds(315, labMarging, labWidth, labHeight);
        add(problemLabel);
        add(problem);
        labMarging += labHeight;

        depthLabel.setBounds(15, labMarging, labWidth, labHeight);
        depth.setBounds(80, labMarging, labWidth, labHeight);
        add(depthLabel);
        add(depth);

        costLabel.setBounds(250, labMarging, labWidth, labHeight);
        cost.setBounds(315, labMarging, labWidth, labHeight);
        add(costLabel);
        add(cost);
        labMarging += labHeight * 1.5;

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollTextPane = new JScrollPane(resultArea);
        scrollTextPane.setBounds(15, labMarging, 470, 470);
        add(scrollTextPane);
    }

    public void displayResult(Token token) {
        domain.setText(token.getDomainFile().getName());
        problem.setText(token.getProblemFile().getName());
        cost.setText(String.valueOf(DecimalFormatSetup.getDf()
                .format(token.getResult().getStatistics().getCost())));
        depth.setText(String.valueOf(DecimalFormatSetup.getDf()
                .format(token.getResult().getStatistics().getDepth())));
        resultArea.setText(token.getResult().getSolutionString());
    }

    public void displayError(Token token) {
        domain.setText(token.getDomainFile().getName());
        problem.setText(token.getProblemFile().getName());
        cost.setText(String.valueOf(0));
        depth.setText(String.valueOf(0));
        resultArea.setText(token.getError());
    }

    public void diplayProgress(Token token) {
        domain.setText(token.getDomainFile().getName());
        problem.setText(token.getProblemFile().getName());
        cost.setText(String.valueOf(0));
        depth.setText(String.valueOf(0));
        resultArea.setText("Token not solved yet !");
    }

    public void clearResult() {
        domain.setText("--");
        problem.setText("---");
        cost.setText("---");
        depth.setText("---");
        resultArea.setText("");
    }
}
