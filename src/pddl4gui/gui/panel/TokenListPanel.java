package pddl4gui.gui.panel;

import pddl4gui.gui.Solver;
import pddl4gui.token.Token;
import pddl4gui.gui.tools.TokenList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class TokenListPanel extends JPanel {

    final private JList<Token> tokenJList;
    final private JButton anytimeSolutionButton;

    public JList<Token> getTokenJList() {
        return tokenJList;
    }

    public TokenListPanel(Solver solver) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Token list"));

        anytimeSolutionButton = new JButton("Anytime details");
        tokenJList = new JList<>(TokenList.getListModel());

        anytimeSolutionButton.setEnabled(false);
        anytimeSolutionButton.setBounds(65, 20, 200, 25);
        anytimeSolutionButton.addActionListener(e -> {
            if(tokenJList.getSelectedValue() != null){
                new AnytimePanel(tokenJList.getSelectedValue());
            }
        });
        add(anytimeSolutionButton);

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
                final Token selectedValue = tokenJList.getSelectedValue();
                if (selectedValue != null) {
                    if (selectedValue.getPlanner().isAnytime()) {
                        if (selectedValue.isSolved()) {
                            anytimeSolutionButton.setEnabled(true);
                            solver.displayResult(selectedValue);
                        } else if (!selectedValue.getError().equals("")) {
                            solver.displayError(selectedValue);
                        } else {
                            new AnytimePanel(selectedValue);
                        }
                    } else {
                        anytimeSolutionButton.setEnabled(false);
                        if (selectedValue.isSolved()) {
                            solver.displayResult(selectedValue);
                        } else if (!selectedValue.isSolved() && !selectedValue.getError().equals("")) {
                            solver.displayError(selectedValue);
                        } else {
                            solver.displayProgress(selectedValue);
                        }
                    }
                }
            }
        });
        final JScrollPane scrollTokenJList = new JScrollPane(tokenJList);
        scrollTokenJList.setBounds(20, 55, 290, 235);
        add(scrollTokenJList);
    }
}
