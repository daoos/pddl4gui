package pddl4gui.gui.panel.local;

import fr.uga.pddl4j.planners.statespace.search.strategy.Node;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.OwnListSelectionModel;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.LocalToken;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

/**
 * This class implements the SolutionListPanel class of <code>PDDL4GUI</code>.
 * This JFrame displays anytime information for token solved with anytime planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
class SolutionListPanel extends JFrame {

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
        SolutionListPanel.frame = frame;
    }

    /**
     * The labels in the JFrame.
     */
    private final JLabel currentCost;
    private final JLabel currentDepth;

    /**
     * Creates a new Anytime JFrame for a specified token.
     *
     * @param token the token.
     */
    SolutionListPanel(final LocalToken token) {
        setLayout(null);
        setSize(500, 350);
        setTitle(token.getDomainFileName()
                + " " + token.getProblemFileName());

        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder("Details of selected solution node"));

        final JLabel currentCostLabel = new JLabel("Cost ");
        final JLabel currentDepthLabel = new JLabel("Depth ");
        currentCost = new JLabel("--");
        currentDepth = new JLabel("--");

        currentCostLabel.setBounds(20, 20, 60, 25);
        currentCost.setBounds(80, 20, 120, 25);
        panel.add(currentCostLabel);
        panel.add(currentCost);

        currentDepthLabel.setBounds(200, 20, 60, 25);
        currentDepth.setBounds(280, 20, 120, 25);
        panel.add(currentDepthLabel);
        panel.add(currentDepth);

        panel.setBounds(70, 5, 420, 55);
        add(panel);

        final JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(15, 15, 40, 40);
        exitButton.setEnabled(true);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> this.dispose());
        add(exitButton);

        final JList<Node> nodeJList = new JList<>(token.getSolutioNodeListModel());
        nodeJList.setLayoutOrientation(JList.VERTICAL);
        nodeJList.setVisibleRowCount(20);
        nodeJList.setSelectionModel(new OwnListSelectionModel());
        nodeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nodeJList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                final Node node = nodeJList.getSelectedValue();
                if (node != null) {
                    currentCost.setText(String.valueOf(TriggerAction.getDf().format(node.getCost())));
                    currentDepth.setText(String.valueOf(node.getDepth()));
                    TriggerAction.displayResult(token.getCodedProblem()
                            .toString(token.getPlanner().getStateSpaceStrategies().get(0)
                                    .extractPlan(node, token.getCodedProblem())));
                } else {
                    TriggerAction.clearResult();
                }
            }
        });
        final JScrollPane scrollNodeJList = new JScrollPane(nodeJList);
        scrollNodeJList.setBounds(10, 70, 480, 235);
        add(scrollNodeJList);

        setLocation(WindowsManager.setWindowsLocationWidth());
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        addWindowListener(new OnClose());
    }

    /**
     * The method to call on window closing.
     */
    static class OnClose extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            SolutionListPanel.frame.dispose();
        }
    }
}
