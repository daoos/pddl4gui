package pddl4gui.gui.panel;

import fr.uga.pddl4j.planners.ff.Node;
import pddl4gui.planners.PlannerAnytime;
import pddl4gui.gui.tools.DecimalFormatSetup;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.Vector;

public class AnytimePanel extends JFrame {

    final private JLabel currentCost, currentDepth, bestCost, isSolved, numberSolution;
    private DefaultListModel<Node> listModel;
    private Token token;
    private double bestCostD = Double.MAX_VALUE;
    private double numberSolutionI = 0;

    public AnytimePanel(Token token) {
        this.token = token;

        setLayout(null);
        setSize(500, 350);
        setTitle(FileTools.removeExtension(this.token.getDomainFile().getName())
                + " " + FileTools.removeExtension(this.token.getProblemFile().getName()));

        final int labWidth = 120;
        final int labHeight = 20;
        int labMarging = 20;

        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder("Anytime in progress"));

        final JLabel currentCostLabel = new JLabel("Cost ");
        final JLabel currentDepthLabel = new JLabel("Depth ");
        final JLabel bestCostLabel = new JLabel("Best cost ");
        final JLabel numberSolutionLabel = new JLabel("Nb solution ");
        final JLabel isSolvedLabel = new JLabel("Problem solved ? ");
        currentCost = new JLabel("--");
        currentDepth = new JLabel("--");
        bestCost = new JLabel("--");
        numberSolution = new JLabel("--");
        isSolved = new JLabel("--");

        currentCostLabel.setBounds(10, labMarging, labWidth - 20, labHeight);
        currentCost.setBounds(80, labMarging, labWidth - 20, labHeight);
        panel.add(currentCostLabel);
        panel.add(currentCost);


        currentDepthLabel.setBounds(190, labMarging, labWidth - 20, labHeight);
        currentDepth.setBounds(280, labMarging, labWidth - 20, labHeight);
        panel.add(currentDepthLabel);
        panel.add(currentDepth);
        labMarging += labHeight;

        bestCostLabel.setBounds(10, labMarging, labWidth - 20, labHeight);
        bestCost.setBounds(80, labMarging, labWidth - 20, labHeight);
        panel.add(bestCostLabel);
        panel.add(bestCost);

        numberSolutionLabel.setBounds(190, labMarging, labWidth - 20, labHeight);
        numberSolution.setBounds(280, labMarging, labWidth - 20, labHeight);
        panel.add(numberSolutionLabel);
        panel.add(numberSolution);
        labMarging += labHeight;

        isSolvedLabel.setBounds(10, labMarging, labWidth, labHeight);
        isSolved.setBounds(130, labMarging, labWidth, labHeight);
        panel.add(isSolvedLabel);
        panel.add(isSolved);

        panel.setBounds(110, 10, 380, 90);
        add(panel);

        final JButton refreshButton = new JButton(Icons.getRefreshIcon());
        refreshButton.setBounds(10, 15, 40, 40);
        refreshButton.setEnabled(true);
        refreshButton.setToolTipText("Refresh");
        refreshButton.addActionListener(e -> {
            refreshJList();
            bestCost.setText(String.valueOf(DecimalFormatSetup.getDf().format(bestCostD)));
            numberSolution.setText(String.valueOf(numberSolutionI));
            isSolved.setText(String.valueOf(token.isSolved()));
        });
        add(refreshButton);

        final JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(60, 15, 40, 40);
        exitButton.setEnabled(true);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> this.dispose());
        add(exitButton);

        listModel = new DefaultListModel<>();
        final JList<Node> nodeJList = new JList<>(listModel);
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
        nodeJList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                final Node node = nodeJList.getSelectedValue();
                if (node != null) {
                    displayAnytimeResult(node.getCost(), node.getDepth());
                }
            }
        });
        final JScrollPane scrollNodeJList = new JScrollPane(nodeJList);
        scrollNodeJList.setBounds(10, 110, 480, 200);
        add(scrollNodeJList);

        setLocation(WindowsManager.setWindowsLocation());
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void refreshJList() {
        final PlannerAnytime plannerAnytime = (PlannerAnytime) token.getPlanner();
        if (!plannerAnytime.getAnytimeSolutions().isEmpty()) {
            final Vector<Node> solutionList = new Vector<>(plannerAnytime.getAnytimeSolutions());
            //listModel.clear();
            for (Node node : solutionList) {
                if (!listModel.contains(node)) {
                    listModel.addElement(node);
                    bestCostD = Math.min(bestCostD, node.getCost());
                }
            }
            numberSolutionI = listModel.getSize();
        }
    }

    private void displayAnytimeResult(double currentCostL, int currentDepthL) {
        currentCost.setText(String.valueOf(DecimalFormatSetup.getDf().format(currentCostL)));
        currentDepth.setText(String.valueOf(currentDepthL));
    }
}
