package pddl4gui.gui;

import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.LocalToken;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
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
public class Result extends JFrame {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The reference to the frame.
     */
    private static JFrame frame;

    /**
     * Sets the reference to the JFrame.
     *
     * @param frame the JFrame.
     */
    public static void setFrame(final JFrame frame) {
        Result.frame = frame;
    }

    /**
     * Sets the JFrame visible.
     */
    public static void setVisible() {
        Result.frame.setVisible(true);
    }


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
    public Result() {
        setTitle("PDDL4GUI | Result");
        setSize(WindowsManager.getWidth(), WindowsManager.getHeight());

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createTitledBorder("Solver result"));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        final JScrollPane scrollTextPane = new JScrollPane(resultArea);

        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0,2));

        final JPanel domainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel domainLabel = new JLabel("Domain");
        domain = new JLabel("---");
        domainPanel.add(domainLabel);
        domainPanel.add(domain);

        final JPanel problemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel problemLabel = new JLabel("Problem");
        problem = new JLabel("---");
        problemPanel.add(problemLabel);
        problemPanel.add(problem);

        final JPanel depthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel depthLabel = new JLabel("Depth");
        depth = new JLabel("---");
        depthPanel.add(depthLabel);
        depthPanel.add(depth);

        final JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel costLabel = new JLabel("Cost");
        cost = new JLabel("---");
        costPanel.add(costLabel);
        costPanel.add(cost);

        final JPanel plannerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel plannerLabel = new JLabel("Planner");
        planner = new JLabel("---");
        plannerPanel.add(plannerLabel);
        plannerPanel.add(planner);

        checkbox = new JCheckBox("Detailed plan");
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
        infoPanel.add(domainPanel);
        infoPanel.add(problemPanel);
        infoPanel.add(depthPanel);
        infoPanel.add(costPanel);
        infoPanel.add(plannerPanel);
        infoPanel.add(checkbox);

        contentPanel.add(infoPanel, BorderLayout.NORTH);
        contentPanel.add(scrollTextPane, BorderLayout.CENTER);

        setContentPane(contentPanel);
        setLocation(WindowsManager.setWindowsLocationWidth());

        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
        planner.setText(token.getPlannerName() + " with " + token.getStrategyName() + " ( "
                + token.getPlanner().getStateSpaceStrategies().get(0).getWeight() + " | "
                + token.getPlanner().getStateSpaceStrategies().get(0).getTimeout() / 1000 + " )");
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
        planner.setText(token.getPlannerName() + " with " + token.getStrategyName() + " ( "
                + token.getPlanner().getStateSpaceStrategies().get(0).getWeight() + " | "
                + token.getPlanner().getStateSpaceStrategies().get(0).getTimeout() / 1000 + " )");
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
        planner.setText(token.getPlannerName() + " with " + token.getStrategyName() + " ( "
                + token.getPlanner().getStateSpaceStrategies().get(0).getWeight() + " | "
                + token.getPlanner().getStateSpaceStrategies().get(0).getTimeout() / 1000 + " )");
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
