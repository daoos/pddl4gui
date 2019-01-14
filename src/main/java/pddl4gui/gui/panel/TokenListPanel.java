package pddl4gui.gui.panel;

import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlanner;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.Token;

import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

/**
 * This class implements the TokenListPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays a JList of token linked to the Queue.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class TokenListPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JList displaying tokens from the Queue.
     */
    private final JList<Token> tokenJList;

    /**
     * The JButton of the TokenListPanel.
     */
    private final JButton multipleResults;

    /**
     * Returns the JList object.
     *
     * @return the JList object.
     */
    public JList<Token> getTokenJList() {
        return tokenJList;
    }

    /**
     * Creates a new TokenListPanel.
     */
    public TokenListPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Token list"));

        final JButton SolutionListButton = new JButton("List of solutions");
        multipleResults = new JButton("Compute cost");
        tokenJList = new JList<>(TriggerAction.getListModel());

        SolutionListButton.setEnabled(true);
        SolutionListButton.setBounds(20, 220, 140, 25);
        SolutionListButton.addActionListener(e -> {
            if (tokenJList.getSelectedValue() != null) {
                new SolutionListPanel(tokenJList.getSelectedValue());
            }
        });
        add(SolutionListButton);

        multipleResults.setEnabled(false);
        multipleResults.setBounds(170, 220, 140, 25);
        multipleResults.addActionListener(e -> {
            double cost = 0.0;
            for (Token token : tokenJList.getSelectedValuesList()) {
                cost += token.getResult().getStatistics().getCost();
            }
            JOptionPane.showMessageDialog(null, "Token selected: "
                            + tokenJList.getSelectedValuesList().size() + "\nTotal cost: "
                            + cost, "Multiple Selection", JOptionPane.PLAIN_MESSAGE);
        });
        add(multipleResults);

        tokenJList.setLayoutOrientation(JList.VERTICAL);
        tokenJList.setVisibleRowCount(20);
        tokenJList.setSelectionModel(new DefaultListSelectionModel() {

            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                }
                super.setSelectionInterval(index0, index1);
            }

            @Override
            public void addSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                    super.addSelectionInterval(index0, index1);
                }
            }

        });
        tokenJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tokenJList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                if (tokenJList.getSelectedValuesList().size() > 1) {
                    TriggerAction.clearResult();
                    TriggerAction.clearStats();
                    multipleResults.setEnabled(true);
                } else if (tokenJList.getSelectedValuesList().size() == 1) {
                    multipleResults.setEnabled(false);

                    final Token selectedValue = tokenJList.getSelectedValue();
                    if (selectedValue != null) {
                        final AbstractStateSpacePlanner planner =
                                (AbstractStateSpacePlanner) selectedValue.getPlanner();
                        if (planner.isAnytime()) {
                            if (selectedValue.isSolved()) {
                                TriggerAction.enableMenuSolverPanelButton(true);
                                TriggerAction.displayResult(selectedValue);
                            } else if (!selectedValue.getError().equals("")) {
                                TriggerAction.enableMenuSolverPanelButton(false);
                                TriggerAction.displayError(selectedValue);
                            } else {
                                TriggerAction.enableMenuSolverPanelButton(false);
                                TriggerAction.displayProgress(selectedValue);
                            }
                        } else {
                            if (selectedValue.isSolved()) {
                                TriggerAction.enableMenuSolverPanelButton(true);
                                TriggerAction.displayResult(selectedValue);
                            } else if (!selectedValue.isSolved() && !selectedValue.getError().equals("")) {
                                TriggerAction.enableMenuSolverPanelButton(false);
                                TriggerAction.displayError(selectedValue);
                            } else {
                                TriggerAction.enableMenuSolverPanelButton(false);
                                TriggerAction.displayProgress(selectedValue);
                            }
                        }
                    }
                } else {
                    TriggerAction.enableMenuSolverPanelButton(false);
                    TriggerAction.clearResult();
                    TriggerAction.clearStats();
                }
            }
        });
        final JScrollPane scrollTokenJList = new JScrollPane(tokenJList);
        scrollTokenJList.setBounds(20, 25, 290, 175);
        add(scrollTokenJList);
    }
}
