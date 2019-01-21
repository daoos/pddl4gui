package pddl4gui.gui.panel.local;

import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlanner;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.LocalToken;
import pddl4gui.token.Token;

import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
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
    private final JList<LocalToken> tokenJList;

    /**
     * Returns the JList object.
     *
     * @return the JList object.
     */
    public JList<LocalToken> getTokenJList() {
        return tokenJList;
    }

    /**
     * Creates a new TokenListPanel.
     */
    public TokenListPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Tokens list"));

        final JButton SolutionListButton = new JButton("List of token's solution nodes");
        tokenJList = new JList<>(TriggerAction.getListModel());

        SolutionListButton.setEnabled(false);
        SolutionListButton.setBounds(20, 220, 290, 25);
        SolutionListButton.addActionListener(e -> {
            if (tokenJList.getSelectedValue() != null) {
                new SolutionListPanel(tokenJList.getSelectedValue());
            }
        });
        add(SolutionListButton);

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
        tokenJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tokenJList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {

                final LocalToken selectedValue = (LocalToken) tokenJList.getSelectedValue();
                if (selectedValue != null) {
                    SolutionListButton.setEnabled(true);
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
                } else {
                    SolutionListButton.setEnabled(false);
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
