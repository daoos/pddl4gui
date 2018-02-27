package pddl4gui.gui.panel;

import fr.uga.pddl4j.planners.ff.Node;
import pddl4gui.gui.tools.DecimalFormatSetup;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Vector;

public class AnytimePanel extends JFrame {

    private JLabel currentCost, currentDepth, isSolved;
    private Vector<Node> solutionList;
    private DefaultListModel<Node> listModel;
    private Token token;

    public AnytimePanel(Token token) {
        this.token = token;

        setLayout(null);
        setSize(500, 350);
        setTitle(this.token.getDomainFile().getName() + " " + this.token.getProblemFile().getName());

        int labWidth = 120;
        int labHeight = 20;
        int labMarging = 20;

        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder("Anytime in progress"));
        JLabel currentCostLabel = new JLabel("Cost: ");
        JLabel currentDepthLabel = new JLabel("Depth: ");
        JLabel isSolvedLabel = new JLabel("Problem solved ? ");
        currentCost = new JLabel("--");
        currentDepth = new JLabel("--");
        isSolved = new JLabel("--");

        currentCostLabel.setBounds(10, labMarging, labWidth - 20, labHeight);
        currentCost.setBounds(70, labMarging, labWidth - 20, labHeight);
        panel.add(currentCostLabel);
        panel.add(currentCost);


        currentDepthLabel.setBounds(220, labMarging, labWidth - 20, labHeight);
        currentDepth.setBounds(280, labMarging, labWidth - 20, labHeight);
        panel.add(currentDepthLabel);
        panel.add(currentDepth);
        labMarging += labHeight;

        isSolvedLabel.setBounds(10, labMarging, labWidth, labHeight);
        isSolved.setBounds(130, labMarging, labWidth, labHeight);
        panel.add(isSolvedLabel);
        panel.add(isSolved);

        panel.setBounds(110, 10, 380, 70);
        add(panel);

        JButton refreshButton = new JButton(Icons.getRefreshIcon());
        refreshButton.setBounds(10, 15, 40, 40);
        refreshButton.setEnabled(true);
        refreshButton.setToolTipText("Refresh");
        refreshButton.addActionListener(e -> {
            refreshJList();
            isSolved.setText(String.valueOf(token.isSolved()));
        });
        add(refreshButton);

        JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(60, 15, 40, 40);
        exitButton.setEnabled(true);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> {
            this.dispose();
        });
        add(exitButton);

        listModel = new DefaultListModel<>();
        JList<Node> nodeJList = new JList<>(listModel);
        nodeJList.setLayoutOrientation(JList.VERTICAL);
        nodeJList.setVisibleRowCount(20);
        nodeJList.setSelectionModel(new DefaultListSelectionModel() {

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
        nodeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nodeJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    final Node node = nodeJList.getSelectedValue();
                    if (node != null) {
                        displayAnytimeResult(node.getCost(), node.getDepth());
                    }
                }
            }
        });
        JScrollPane scrollNodeJList = new JScrollPane(nodeJList);
        scrollNodeJList.setBounds(10, 110, 480, 200);
        add(scrollNodeJList);

        setLocation(WindowsManager.setWindowsLocation());
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void refreshJList() {
        if (!token.getPlanner().getAnytimeSolutions().isEmpty()) {
            solutionList = new Vector<>(token.getPlanner().getAnytimeSolutions());
            listModel.clear();
            for (Node node : solutionList) {
                if (!listModel.contains(node)) {
                    listModel.addElement(node);
                }
            }
        }
    }

    private void displayAnytimeResult(double currentCostL, int currentDepthL) {
        currentCost.setText(String.valueOf(DecimalFormatSetup.getDf().format(currentCostL)));
        currentDepth.setText(String.valueOf(currentDepthL));
    }
}
