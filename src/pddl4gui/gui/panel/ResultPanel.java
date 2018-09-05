package pddl4gui.gui.panel;

import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.DecimalFormatSetup;
import pddl4gui.token.Token;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.Serializable;

/**
 *
 */
public class ResultPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    final private JLabel domain, problem, cost, depth, planner;

    /**
     *
     */
    final private JTextArea resultArea;

    /**
     *
     */
    final private JCheckBox checkbox;

    /**
     *
     */
    private Token token = null;

    /**
     *
     * @return
     */
    public boolean isCheckboxSelected() {
        return checkbox.isSelected();
    }

    /**
     *
     * @param solver
     */
    public ResultPanel(Solver solver) {
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
            if (token != null && token.isSolved()) {
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
     *
     * @param token
     */
    public void displayResult(Token token) {
        this.token = token;
        checkbox.setEnabled(true);
        checkbox.setSelected(false);

        domain.setText(token.getDomainFileName());
        problem.setText(token.getProblemFileName());
        cost.setText(String.valueOf(DecimalFormatSetup.getDf()
                .format(token.getResult().getStatistics().getCost())));
        depth.setText(String.valueOf(DecimalFormatSetup.getDf()
                .format(token.getResult().getStatistics().getDepth())));
        resultArea.setText(token.getResult().getSolutionString());
        planner.setText(token.getPlannerName().toString());
    }

    /**
     *
     * @param token
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
     *
     * @param token
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
     *
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
