package main.java.pddl4gui.gui.panel;

import main.java.pddl4gui.gui.tools.TriggerAction;
import main.java.pddl4gui.token.Token;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.Serializable;

/**
 * This class implements the ResultPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays result information for selected token.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class ResultPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JLabel of the ResultPanel.
     */
    final private JLabel domain, problem, cost, depth, planner;

    /**
     * The JTextArea containing solving result of selected token.
     */
    final private JTextArea resultArea;

    /**
     * The JCheckBox to display detailed plan or not.
     */
    final private JCheckBox checkbox;

    /**
     * The Token.
     */
    private Token token = null;

    /**
     * Returns if JCheckbox is selected (display detailed plan) or not.
     *
     * @return the status of JCheckBox. Selected or not.
     */
    public boolean isCheckboxSelected() {
        return checkbox.isSelected();
    }

    /**
     * Creates a new ResultPanel which displays token's result.
     */
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

        checkbox = new JCheckBox("Detailed plan");
        resultArea = new JTextArea();

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

        checkbox.setBounds(360, labMarging, labWidth, labHeight);
        checkbox.setSelected(false);
        checkbox.setEnabled(false);
        checkbox.addItemListener(e -> {
            if (token != null && token.isSolved()) { //TODO gérer les boutons
                if (checkbox.isSelected()) {
                    resultArea.setText(token.getResult().getSolutionStringDetailed());
                    //solver.getMenuSolverPanel().discardVAL(true);
                } else {
                    displayResult(token);
                    //solver.getMenuSolverPanel().enableButton(true);
                }
            }
        });
        add(checkbox);

        labMarging += labHeight * 1.75;

        resultArea.setEditable(false);
        final JScrollPane scrollTextPane = new JScrollPane(resultArea);
        scrollTextPane.setBounds(15, labMarging, 470, 470);
        add(scrollTextPane);
    }

    /**
     * Displays result for a specific token.
     *
     * @param token the token.
     */
    public void displayResult(Token token) {
        this.token = token;
        checkbox.setEnabled(true);
        checkbox.setSelected(false);

        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(TriggerAction.getDf()
                .format(token.getResult().getStatistics().getCost())));
        depth.setText(String.valueOf(TriggerAction.getDf()
                .format(token.getResult().getStatistics().getDepth())));
        resultArea.setText(token.getResult().getSolutionString());
        planner.setText(token.getPlannerName().toString());
    }

    /**
     * Displays error message for a specific token.
     *
     * @param token the token.
     */
    public void displayError(Token token) {
        this.token = token;
        checkbox.setEnabled(false);
        checkbox.setSelected(false);

        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(0));
        depth.setText(String.valueOf(0));
        planner.setText(token.getPlannerName().toString());
        resultArea.setText(token.getError());
    }

    /**
     * Displays progress message for a specific token.
     *
     * @param token the token.
     */
    public void diplayProgress(Token token) {
        this.token = token;
        checkbox.setEnabled(false);
        checkbox.setSelected(false);

        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(0));
        depth.setText(String.valueOf(0));
        planner.setText(token.getPlannerName().toString());
        resultArea.setText("Token not solved yet !");
    }

    /**
     * Clears the ResultPanel.
     */
    public void clearResult() {
        checkbox.setEnabled(false);
        checkbox.setSelected(false);

        domain.setText("--");
        problem.setText("---");
        cost.setText("---");
        depth.setText("---");
        planner.setText("---");
        resultArea.setText("");
    }
}
