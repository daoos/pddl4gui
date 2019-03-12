package pddl4gui.gui.panel.local;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.statespace.StateSpacePlanner;
import fr.uga.pddl4j.planners.statespace.StateSpacePlannerFactory;
import fr.uga.pddl4j.planners.statespace.generic.GenericAnytimePlanner;
import fr.uga.pddl4j.planners.statespace.generic.GenericPlanner;
import fr.uga.pddl4j.planners.statespace.search.strategy.AStar;
import fr.uga.pddl4j.planners.statespace.search.strategy.AStarAnytime;
import fr.uga.pddl4j.planners.statespace.search.strategy.BreadthFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.strategy.DepthFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.strategy.EnforcedHillClimbing;
import fr.uga.pddl4j.planners.statespace.search.strategy.GreedyBestFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.strategy.GreedyBestFirstSearchAnytime;
import fr.uga.pddl4j.planners.statespace.search.strategy.StateSpaceStrategy;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.LocalToken;

import java.io.File;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * This class implements the SetupSolverPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays options for the planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class SetupSolverPanel extends JPanel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JSpinner to choose weight for the planner.
     */
    private final JSpinner weightSpinner;

    /**
     * The JSpinner to choose timeout for the planner.
     */
    private final JSpinner timeoutSpinner;

    /**
     * The JButton of the SetupSolverPanel.
     */
    private final JButton domainButton;
    private final JButton pbButton;

    /**
     * The PDDL domain file.
     */
    private File domainFile;

    /**
     * The list of PDDL problem files.
     */
    private Vector<File> problemFiles;

    /**
     * The default Heuristic used by the planner.
     */
    private Heuristic.Type heuristic = Heuristic.Type.FAST_FORWARD;

    /**
     * The default Planner used.
     */
    private String plannerName = "HSP";

    /**
     * The list of search strategies available in PDDL4J.
     */
    private final String[] searchStrategyList = {"A*", "Enforced Hill Climbing", "Breadth First Search",
        "Depth First Search", "Greedy Best First Search"};

    /**
     * The list of anytime search strategies available in PDDL4J.
     */
    private final String[] anytimeSearchStrategyList = {"A* Anytime", "Greedy Best First Search Anytime"};

    /**
     * The default search strategy used.
     */
    private String strategyName = "A*";

    /**
     * Creates a new SetupSolverPanel associated to the Solver main JFrame.
     */
    public SetupSolverPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver parameters"));

        final JLabel domainLabel = new JLabel("Domain file");
        final JLabel problemLabel = new JLabel("Problem file");
        final JLabel plannerLabel = new JLabel("Planner");
        final JLabel heuristicLabel = new JLabel("Heuristic");
        final JLabel weightLabel = new JLabel("Weight");
        final JLabel timeLabel = new JLabel("Time out");

        domainButton = new JButton("Choose domain");
        pbButton = new JButton("Choose problem");

        domainButton.setBounds(100, 25, 150, 25);
        domainButton.setEnabled(true);
        domainButton.addActionListener(e -> {
            final Vector<File> domainTempFiles = FileTools.getFiles(this, 0, false, domainButton);
            if (domainTempFiles.size() == 1) {
                domainFile = domainTempFiles.firstElement();
                if (FileTools.checkFile(domainFile)) {
                    domainButton.setText(domainFile.getName());
                }
            } else {
                domainFile = null;
            }
        });
        add(domainButton);

        domainLabel.setBounds(15, 25, 140, 25);
        add(domainLabel);

        pbButton.setBounds(100, 65, 150, 25);
        pbButton.setEnabled(true);
        pbButton.addActionListener(e -> {
            final Vector<File> problemTempFiles = FileTools.getFiles(this, 0, true, pbButton);
            if (problemTempFiles.size() == 1) {
                problemFiles = problemTempFiles;
                if (FileTools.checkFile(problemFiles.firstElement())) {
                    pbButton.setText(problemFiles.firstElement().getName());
                }
            } else if (problemTempFiles.size() >= 1) {
                problemFiles = problemTempFiles;
                pbButton.setText(problemFiles.size() + " problems");
            } else {
                problemFiles = null;
            }
        });
        add(pbButton);

        problemLabel.setBounds(15, 65, 140, 25);
        add(problemLabel);

        final String[] plannerList = {"HSP", "FF", "GenericPlanner", "FFAnytime", "HCAnytime", "GenericAnytimePlanner"};
        final JComboBox plannerComboBox = new JComboBox<>(plannerList);
        plannerComboBox.setBounds(100, 105, 150, 25);
        plannerComboBox.setSelectedIndex(0);
        plannerComboBox.addActionListener(e -> {
            plannerName = (String) plannerComboBox.getSelectedItem();

            if (plannerName != null && plannerName.equals("GenericPlanner")) {
                final JComboBox<String> combo = new JComboBox<>(searchStrategyList);

                final String[] options = { "OK", "Cancel"};

                String title = "GenericPlanner: Select a search strategy";
                int selection = JOptionPane.showOptionDialog(null, combo, title,
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                        options[0]);

                if (selection == 0) {
                    strategyName = (String) combo.getSelectedItem();
                }
            } else if (plannerName != null && plannerName.equals("GenericAnytimePlanner")) {
                final JComboBox<String> combo = new JComboBox<>(anytimeSearchStrategyList);

                final String[] options = { "OK", "Cancel"};

                String title = "GenericAnytimePlanner: Select an anytime search strategy";
                int selection = JOptionPane.showOptionDialog(null, combo, title,
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                        options[0]);

                if (selection == 0) {
                    strategyName = (String) combo.getSelectedItem();
                }
            }
        });
        add(plannerComboBox);

        plannerLabel.setBounds(15, 105, 140, 25);
        add(plannerLabel);

        final JComboBox heuristicComboBox = new JComboBox<>(Heuristic.Type.values());
        heuristicComboBox.setBounds(100, 145, 150, 25);
        heuristicComboBox.setSelectedItem(heuristic);
        heuristicComboBox.addActionListener(e -> heuristic = (Heuristic.Type) heuristicComboBox.getSelectedItem());
        add(heuristicComboBox);

        heuristicLabel.setBounds(15, 145, 150, 25);
        add(heuristicLabel);

        final SpinnerNumberModel modelWeight = new SpinnerNumberModel(1.0, 0.0, 10.0, 0.1);
        weightSpinner = new JSpinner(modelWeight);
        weightSpinner.setBounds(100, 185, 150, 25);
        add(weightSpinner);

        weightLabel.setBounds(15, 185, 150, 25);
        add(weightLabel);

        final SpinnerNumberModel modelTimeout = new SpinnerNumberModel(Planner.DEFAULT_TIMEOUT,
                0.0, 10000.0, 1);
        timeoutSpinner = new JSpinner(modelTimeout);
        timeoutSpinner.setBounds(100, 225, 150, 25);
        add(timeoutSpinner);

        timeLabel.setBounds(15, 225, 150, 25);
        add(timeLabel);

        final JButton planButton = new JButton("Resolve this problem !");
        planButton.setBounds(65, 270, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> resolve(domainFile, problemFiles));
        add(planButton);
    }

    /**
     * Return a planner according to the parameters given.
     *
     * @param weight  the weight of the heuristic.
     * @param timeout the timeout of the heuristic.
     * @return the planner to use.
     */
    private StateSpacePlanner getPlanner(final double weight, final double timeout) {
        final StateSpacePlannerFactory plannerFactory = StateSpacePlannerFactory.getInstance();
        StateSpacePlanner planner;

        switch (plannerName) {
            case "HSP":
                planner = plannerFactory.getPlanner(Planner.Name.HSP, (int) timeout, heuristic, weight, true, 1);
                break;
            case "FF":
                planner = plannerFactory.getPlanner(Planner.Name.FF, (int) timeout, heuristic, weight, true, 1);
                break;
            case "GenericPlanner":
                System.out.println(strategyName);
                switch (strategyName) {
                    case "A*":
                        planner = new GenericPlanner(true, 1,
                                new AStar((int) timeout, heuristic, weight));
                        break;
                    case "Enforced Hill Climbing":
                        planner = new GenericPlanner(true, 1,
                                new EnforcedHillClimbing((int) timeout, heuristic, weight));
                        break;
                    case "Breadth First Search":
                        planner = new GenericPlanner(true, 1,
                                new BreadthFirstSearch((int) timeout));
                        break;
                    case "Depth First Search":
                        planner = new GenericPlanner(true, 1,
                                new DepthFirstSearch((int) timeout));
                        break;
                    case "Greedy Best First Search":
                        planner = new GenericPlanner(true, 1,
                                new GreedyBestFirstSearch((int) timeout, heuristic, weight));
                        break;
                    default:
                        planner = null;
                        break;
                }
                break;
            case "GenericAnytimePlanner":
                System.out.println(strategyName);
                switch (strategyName) {
                    case "A* Anytime":
                        planner = new GenericAnytimePlanner(true, 1,
                                new AStarAnytime((int) timeout, heuristic, weight));
                        break;
                    case "Greedy Best First Search Anytime":
                        planner = new GenericAnytimePlanner(true, 1,
                                new GreedyBestFirstSearchAnytime((int) timeout, heuristic, weight));
                        break;
                    default:
                        planner = null;
                        break;
                }
                break;
            case "FFAnytime":
                planner = plannerFactory.getPlanner(Planner.Name.FFAnytime, (int) timeout, heuristic, weight, true, 1);
                break;
            case "HCAnytime":
                planner = plannerFactory.getPlanner(Planner.Name.HCAnytime, (int) timeout, heuristic, weight, true, 1);
                break;
            default:
                planner = null;
                break;
        }
        return planner;
    }

    /**
     * Creates token and adds it into the Queue.
     *
     * @param domainFile   the PDDL domain file.
     * @param problemFiles the list of PDDL problem files.
     */
    private void resolve(final File domainFile, final Vector<File> problemFiles) {
        final double weight = (double) weightSpinner.getValue();
        final double timeout = (double) timeoutSpinner.getValue() * 1000;

        if (problemFiles != null && domainFile != null) {
            for (File file : problemFiles) {
                final StateSpacePlanner planner = getPlanner(weight, timeout);
                if (planner != null) {
                    final LocalToken token = new LocalToken(domainFile, file, planner, plannerName);
                    List<StateSpaceStrategy> strategyList = token.getPlanner().getStateSpaceStrategies();
                    token.setSolutioNodeListModel(new DefaultListModel<>());

                    for (StateSpaceStrategy stateSpaceStrategy : strategyList) {
                        stateSpaceStrategy.addSolutionListener(e -> token.getSolutioNodeListModel()
                                .addElement(e.getSolutionNode()));
                    }

                    if (token.isRunnable() && TriggerAction.isEngineManagerRunning()) {
                        if (!TriggerAction.getListModel().contains(token)) {
                            System.out.println("[SetupSolver] Token " + token.toString() + "created and added to the Queue");
                            TriggerAction.getListModel().addElement(token);
                            TriggerAction.getQueue().addTokenInQueue(token);
                        }
                    }

                    System.out.println("[SetupSolver] " + TriggerAction.getQueue().remainingTokens() + " in Queue");

                    TriggerAction.setTokenRemainingEngineManagerPanel();
                }
            }
        }
    }
}
