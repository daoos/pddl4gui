package pddl4gui.gui.panel;

import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.LocalToken;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class implements the ResultPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays result information for selected token.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class ResultPanel extends JPanel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JLabel of the ResultPanel.
     */
    private final JLabel domain;
    private final JLabel problem;
    private final JLabel cost;
    private final JLabel depth;
    private final JLabel planner;

    /**
     * The JTextArea containing solving result of selected token.
     */
    private final JTextArea resultArea;

    /**
     * The JCheckBox to display detailed plan or not.
     */
    private final JCheckBox checkbox;

    /**
     * The Token.
     */
    private LocalToken token = null;

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

        final int labWidth = 150;
        final int labHeight = 20;
        int labMarging = 20;

        domainLabel.setBounds(15, labMarging, labWidth, labHeight);
        domain.setBounds(70, labMarging, labWidth, labHeight);
        add(domainLabel);
        add(domain);

        costLabel.setBounds(250, labMarging, labWidth, labHeight);
        cost.setBounds(305, labMarging, labWidth / 2, labHeight);
        add(costLabel);
        add(cost);

        labMarging += labHeight;

        problemLabel.setBounds(15, labMarging, labWidth, labHeight);
        problem.setBounds(70, labMarging, labWidth, labHeight);
        add(problemLabel);
        add(problem);

        depthLabel.setBounds(250, labMarging, labWidth, labHeight);
        depth.setBounds(305, labMarging, labWidth / 2, labHeight);
        add(depthLabel);
        add(depth);

        labMarging += labHeight;

        plannerLabel.setBounds(15, labMarging, labWidth, labHeight);
        planner.setBounds(70, labMarging, labWidth, labHeight);
        add(plannerLabel);
        add(planner);

        checkbox.setBounds(375, labMarging, (labWidth / 2) + 40, labHeight);
        checkbox.setSelected(false);
        checkbox.setEnabled(false);
        checkbox.addItemListener(e -> {
            if (token != null && token.isSolved()) {
                if (checkbox.isSelected()) {
                    resultArea.setText(token.getSolutionStringDetailed());
                } else {
                    displayResult(token);
                }
            }
        });
        add(checkbox);

        labMarging += labHeight * 1.50;

        resultArea.setEditable(false);
        final JScrollPane scrollTextPane = new JScrollPane(resultArea);
        scrollTextPane.setBounds(15, labMarging, 600, 490);
        add(scrollTextPane);
    }

    /**
     * Get the content of the JTextArea.
     *
     * @return The String result.
     */
    public String getResult() {
        return resultArea.getText();
    }

    /**
     * Fill JTextArea.
     *
     * @param string the string to fill the JTextArea.
     */
    public void displayResult(String string) {
        resultArea.setText(string);
    }

    /**
     * Displays result for a specific token.
     *
     * @param token the token.
     */
    public void displayResult(LocalToken token) {
        this.token = token;
        checkbox.setEnabled(true);
        checkbox.setSelected(false);

        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(TriggerAction.getDf()
                .format(token.getStatistics().getCost())));
        depth.setText(TriggerAction.getDf()
                .format(token.getStatistics().getDepth()));
        resultArea.setText(token.getSolutionString());
        planner.setText(token.getPlannerName());
    }

    /**
     * Displays error message for a specific token.
     *
     * @param token the token.
     */
    public void displayError(LocalToken token) {
        this.token = token;
        checkbox.setEnabled(false);
        checkbox.setSelected(false);

        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(0));
        depth.setText(String.valueOf(0));
        planner.setText(token.getPlannerName());
        resultArea.setText(token.getError());
    }

    /**
     * Displays progress message for a specific token.
     *
     * @param token the token.
     */
    public void diplayProgress(LocalToken token) {
        this.token = token;
        checkbox.setEnabled(false);
        checkbox.setSelected(false);

        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(0));
        depth.setText(String.valueOf(0));
        planner.setText(token.getPlannerName());
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
