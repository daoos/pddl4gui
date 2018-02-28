package pddl4gui.gui.panel;

import pddl4gui.gui.Solver;
import pddl4gui.token.Token;
import pddl4gui.token.TokenList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class TokenListPanel extends JPanel {

    final private JList<Token> tokenJList;

    public JList<Token> getTokenJList() {
        return tokenJList;
    }

    public TokenListPanel(Solver parent) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Token list"));

        tokenJList = new JList<>(TokenList.getListModel());
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
                            parent.displayResult(selectedValue);
                        } else if (!selectedValue.getError().equals("")) {
                            parent.displayError(selectedValue);
                        } else {
                            new AnytimePanel(selectedValue);
                        }
                    } else {
                        if (selectedValue.isSolved()) {
                            parent.displayResult(selectedValue);
                        } else if (!selectedValue.isSolved() && !selectedValue.getError().equals("")) {
                            parent.displayError(selectedValue);
                        } else {
                            parent.displayProgress(selectedValue);
                        }
                    }
                }
            }
        });
        JScrollPane scrollTokenJList = new JScrollPane(tokenJList);
        scrollTokenJList.setBounds(20, 20, 290, 270);
        add(scrollTokenJList);
    }
}
