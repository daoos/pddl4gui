package pddl4gui.gui.panel;

import fr.uga.pddl4j.planners.ff.Node;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;

import javax.swing.*;
import java.util.LinkedList;

public class AnytimePanel extends JFrame {

    private JLabel currentCost, currentDepth, numberSolution, isSolved;
    private LinkedList<Node> solutionList;

    public AnytimePanel(Token token) {
        setLayout(null);
        setSize(620, 150);
        setTitle(token.getDomainFile().getName()+ " " + token.getProblemFile().getName());

        solutionList = token.getPlanner().getAnytimeSolutions();

        int labWidth = 160;
        int labHeight = 20;
        int labMarging = 20;

        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder("Anytime in progress"));
        JLabel currentCostLabel = new JLabel("Current solution cost: ");
        JLabel currentDepthLabel = new JLabel("Current solution depth: ");
        JLabel numberSolutionLabel = new JLabel("Number of solutions: ");
        JLabel isSolvedLabel = new JLabel("Solved: ");
        currentCost = new JLabel("--");
        currentDepth = new JLabel("--");
        numberSolution = new JLabel("--");
        isSolved = new JLabel("--");

        currentCostLabel.setBounds(15, labMarging, labWidth, labHeight);
        currentCost.setBounds(175, labMarging, 100, labHeight);
        panel.add(currentCostLabel);
        panel.add(currentCost);

        currentDepthLabel.setBounds(300, labMarging, labWidth, labHeight);
        currentDepth.setBounds(460, labMarging, 100, labHeight);
        panel.add(currentDepthLabel);
        panel.add(currentDepth);
        labMarging += labHeight;

        numberSolutionLabel.setBounds(15, labMarging, labWidth, labHeight);
        numberSolution.setBounds(175, labMarging, 100, labHeight);
        panel.add(numberSolutionLabel);
        panel.add(numberSolution);

        isSolvedLabel.setBounds(300, labMarging, labWidth, labHeight);
        isSolved.setBounds(460, labMarging, 100, labHeight);
        panel.add(isSolvedLabel);
        panel.add(isSolved);

        JButton refresh = new JButton("Refresh");
        refresh.setBounds(200, 65, 200, 25);
        refresh.setEnabled(true);
        refresh.addActionListener(e -> {
            if(solutionList == null) {
                solutionList = token.getPlanner().getAnytimeSolutions();
            } else {
                if(!solutionList.isEmpty()){
                    final Node currentNode = solutionList.peekLast();
                    this.displayAnytimeResult(currentNode.getCost(), currentNode.getDepth(),
                            solutionList.size(), token.isSolved());
                }
            }
        });
        panel.add(refresh);

        panel.setBounds(10, 10, 600, 100);
        add(panel);

        setLocation(WindowsManager.setWindowsLocation());
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void displayAnytimeResult(double currentCostL, int currentDepthL,
                                int numberSolutionL, boolean solvedL) {
        currentCost.setText(String.valueOf(currentCostL));
        currentDepth.setText(String.valueOf(currentDepthL));
        numberSolution.setText(String.valueOf(numberSolutionL));
        isSolved.setText(String.valueOf(solvedL));

    }
}
