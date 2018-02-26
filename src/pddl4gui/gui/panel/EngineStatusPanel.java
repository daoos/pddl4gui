package pddl4gui.gui.panel;

import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.DrawCircle;
import pddl4gui.token.Token;
import pddl4gui.token.TokenList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EngineStatusPanel extends JPanel {

    private JLabel engineLabel;
    private DrawCircle circlePanel;
    private JList<Token> tokenJList;

    public JLabel getEngineLabel() {
        return engineLabel;
    }

    public DrawCircle getCirclePanel() {
        return circlePanel;
    }

    public JList<Token> getTokenJList() {
        return tokenJList;
    }

    public EngineStatusPanel(Solver parent) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engine status"));

        circlePanel = new DrawCircle(20, 20, 20);
        circlePanel.setBounds(0, 0, 40, 40);
        add(circlePanel);

        engineLabel = new JLabel(" -- ");
        engineLabel.setBounds(50, 20, 265, 20);
        add(engineLabel);

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
        tokenJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    final Token selectedValue = tokenJList.getSelectedValue();
                    if (selectedValue != null) {
                        if(selectedValue.getPlanner().isAnytime()) {
                            if (selectedValue.isSolved()) {
                                if (selectedValue.getError().equals("")) {
                                    parent.displayResult(selectedValue);
                                } else {
                                    parent.displayError(selectedValue);
                                }
                            } else {
                                new AnytimePanel(selectedValue);
                            }
                        } else {
                            if (selectedValue.isSolved()) {
                                if (selectedValue.getError().equals("")) {
                                    parent.displayResult(selectedValue);
                                } else {
                                    parent.displayError(selectedValue);
                                }
                            } else {
                                parent.displayProgress(selectedValue);
                            }
                        }
                    }
                }
            }
        });
        JScrollPane scrollTokenJList = new JScrollPane(tokenJList);
        scrollTokenJList.setBounds(20, 50, 290, 200);
        add(scrollTokenJList);
    }

}
