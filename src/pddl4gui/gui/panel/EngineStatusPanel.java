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

    public EngineStatusPanel(Solver parent) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engine status"));

        circlePanel = new DrawCircle(20, 20 ,20);
        circlePanel.setBounds(0,0,40,40);
        add(circlePanel);

        engineLabel = new JLabel(" -- ");
        engineLabel.setBounds(50, 20, 265, 20);
        add(engineLabel);

        tokenJList = new JList<>(TokenList.getListModel());
        tokenJList.setLayoutOrientation(JList.VERTICAL);
        tokenJList.setVisibleRowCount(20);
        tokenJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tokenJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    final Token selectedValue = tokenJList.getSelectedValue();
                    if(selectedValue != null) {
                        if (selectedValue.isSolved()) {
                            if (selectedValue.getError().equals("")) {
                                parent.displayResult(selectedValue);
                            } else {
                                parent.displayError(selectedValue);
                            }
                        } else {
                            parent.displayProgress();
                        }
                    }
                }
            }
        });
        JScrollPane scrollTokenJList= new JScrollPane(tokenJList);
        scrollTokenJList.setBounds(20, 50, 290, 200);
        add(scrollTokenJList);
    }

}
