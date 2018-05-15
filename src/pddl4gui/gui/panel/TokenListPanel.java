package pddl4gui.gui.panel;

import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.TokenList;
import pddl4gui.token.Token;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class TokenListPanel extends JPanel {

    final private JList<Token> tokenJList;
    final private JButton anytimeSolutionButton, multipleResults;

    public JList<Token> getTokenJList() {
        return tokenJList;
    }

    public TokenListPanel(Solver solver) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Token list"));

        anytimeSolutionButton = new JButton("Anytime details");
        multipleResults = new JButton("Compute cost");
        tokenJList = new JList<>(TokenList.getListModel());

        anytimeSolutionButton.setEnabled(false);
        anytimeSolutionButton.setBounds(20, 220, 140, 25);
        anytimeSolutionButton.addActionListener(e -> {
            if (tokenJList.getSelectedValue() != null) {
                new AnytimePanel(tokenJList.getSelectedValue());
            }
        });
        add(anytimeSolutionButton);

        multipleResults.setEnabled(false);
        multipleResults.setBounds(170, 220, 140, 25);
        multipleResults.addActionListener(e -> {
            double cost = 0.0;
            for (Token token : tokenJList.getSelectedValuesList()) {
                cost += token.getResult().getStatistics().getCost();
            }
            JOptionPane.showMessageDialog(null, "Token selected: " + tokenJList.getSelectedValuesList().size()
                    + "\nTotal cost: " + cost, "Multiple Selection", JOptionPane.PLAIN_MESSAGE);
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
                    solver.clearResult();
                    multipleResults.setEnabled(true);
                } else if (tokenJList.getSelectedValuesList().size() == 1) {
                    multipleResults.setEnabled(false);

                    final Token selectedValue = tokenJList.getSelectedValue();
                    if (selectedValue != null) {
                        if (selectedValue.getPlanner().isAnytime()) {
                            anytimeSolutionButton.setEnabled(true);
                            if (selectedValue.isSolved()) {
                                solver.getMenuSolverPanel().enableButton(true);
                                solver.displayResult(selectedValue);
                            } else if (!selectedValue.getError().equals("")) {
                                solver.getMenuSolverPanel().enableButton(false);
                                solver.displayError(selectedValue);
                            } else {
                                solver.getMenuSolverPanel().enableButton(false);
                                solver.displayProgress(selectedValue);
                            }
                        } else {
                            anytimeSolutionButton.setEnabled(false);
                            if (selectedValue.isSolved()) {
                                solver.getMenuSolverPanel().enableButton(true);
                                solver.displayResult(selectedValue);
                            } else if (!selectedValue.isSolved() && !selectedValue.getError().equals("")) {
                                solver.getMenuSolverPanel().enableButton(false);
                                solver.displayError(selectedValue);
                            } else {
                                solver.getMenuSolverPanel().enableButton(false);
                                solver.displayProgress(selectedValue);
                            }
                        }
                    }
                } else {
                    solver.getMenuSolverPanel().enableButton(false);
                    anytimeSolutionButton.setEnabled(false);
                    solver.clearResult();
                }
            }
        });
        final JScrollPane scrollTokenJList = new JScrollPane(tokenJList);
        scrollTokenJList.setBounds(20, 25, 290, 175);
        add(scrollTokenJList);
    }
}
