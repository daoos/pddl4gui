package pddl4gui.gui.panel;

import fr.uga.pddl4j.planners.ff.Node;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.LinkedList;

public class AnytimePanel extends JFrame {

    private JLabel currentCost, currentDepth, isSolved;
    private LinkedList<Node> solutionList;
    private DefaultListModel<Node> listModel;
    private Token token;

    public AnytimePanel(Token token) {
        solutionList = token.getPlanner().getAnytimeSolutions();
        this.token = token;

        setLayout(null);
        setSize(500, 260);
        setTitle(this.token.getDomainFile().getName()+ " " + this.token.getProblemFile().getName());

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

        currentCostLabel.setBounds(10, labMarging, labWidth, labHeight);
        currentCost.setBounds(130, labMarging, labWidth, labHeight);
        panel.add(currentCostLabel);
        panel.add(currentCost);
        labMarging += labHeight;

        currentDepthLabel.setBounds(10, labMarging, labWidth, labHeight);
        currentDepth.setBounds(130, labMarging, labWidth, labHeight);
        panel.add(currentDepthLabel);
        panel.add(currentDepth);
        labMarging += labHeight;

        isSolvedLabel.setBounds(10, labMarging, labWidth, labHeight);
        isSolved.setBounds(130, labMarging, labWidth, labHeight);
        panel.add(isSolvedLabel);
        panel.add(isSolved);

        panel.setBounds(260, 10, 230, 100);
        add(panel);

        JButton refresh = new JButton("Refresh");
        refresh.setBounds(320, 150, 120, 25);
        refresh.setEnabled(true);
        refresh.addActionListener(e -> {
            refreshJList();
        });
        add(refresh);

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
                        displayAnytimeResult(node.getCost(),node.getDepth(),token.isSolved());
                    }
                }
            }
        });
        JScrollPane scrollNodeJList = new JScrollPane(nodeJList);
        scrollNodeJList.setBounds(10, 15, 240, 200);
        add(scrollNodeJList);

        setLocation(WindowsManager.setWindowsLocation());
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void refreshJList() {
        if(!token.getPlanner().getAnytimeSolutions().isEmpty()) {
            solutionList = token.getPlanner().getAnytimeSolutions();
            listModel.clear();
            for(Node node : solutionList) {
                listModel.addElement(node);
            }
        }
    }

    private void displayAnytimeResult(double currentCostL, int currentDepthL, boolean solvedL) {
        currentCost.setText(String.valueOf(currentCostL));
        currentDepth.setText(String.valueOf(currentDepthL));
        isSolved.setText(String.valueOf(solvedL));
    }
}
