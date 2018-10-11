package pddl4gui.gui.panel;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlanner;
import fr.uga.pddl4j.planners.statespace.StateSpacePlannerFactory;
import fr.uga.pddl4j.planners.statespace.generic.GenericPlanner;
import fr.uga.pddl4j.planners.statespace.search.strategy.AStar;
import fr.uga.pddl4j.planners.statespace.search.strategy.BreadthFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.strategy.DepthFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.strategy.EnforcedHillClimbing;
import fr.uga.pddl4j.planners.statespace.search.strategy.GreedyBestFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.strategy.StateSpaceStrategy;
import pddl4gui.gui.Editor;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;

import java.io.File;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.BorderFactory;
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
public class SetupSolverPanel extends JPanel implements Serializable {

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
    private final JButton editDomainButton;
    private final JButton editProblemButton;
    private final JButton planButton;

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
     * The list of planners available in PDDL4J.
     */
    private final String[] plannerList = { "HSP", "FF", "GenericPlanner"};

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
        editDomainButton = new JButton(Icons.getEditorIcon());
        editProblemButton = new JButton(Icons.getEditorIcon());

        domainButton.setBounds(100, 25, 150, 25);
        domainButton.setEnabled(true);
        domainButton.addActionListener(e -> {
            final Vector<File> domainTempFiles = FileTools.getFiles(this, 0, false, domainButton);
            if (domainTempFiles.size() == 1) {
                domainFile = domainTempFiles.firstElement();
                if (FileTools.checkFile(domainFile)) {
                    domainButton.setText(domainFile.getName());
                    editDomainButton.setEnabled(true);
                }
            } else {
                domainFile = null;
                editDomainButton.setEnabled(false);
            }
        });
        add(domainButton);

        editDomainButton.setBounds(275, 25, 25, 25);
        editDomainButton.setEnabled(true);
        editDomainButton.addActionListener(e -> {

            if (domainFile != null) {
                new Editor(domainFile, 0, false);
            } else {
                new Editor(null, 0, true);
            }

            domainFile = null;
            domainButton.setText("Choose domain");
        });
        add(editDomainButton);

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
                    editProblemButton.setEnabled(true);
                }
            } else if (problemTempFiles.size() >= 1) {
                problemFiles = problemTempFiles;
                pbButton.setText(problemFiles.size() + " problems");
                editProblemButton.setEnabled(false);
            } else {
                problemFiles = null;
                editProblemButton.setEnabled(false);
            }
        });
        add(pbButton);

        editProblemButton.setBounds(275, 65, 25, 25);
        editProblemButton.setEnabled(true);
        editProblemButton.addActionListener(e -> {

            if (problemFiles != null) {
                new Editor(problemFiles.firstElement(), 1, false);
            } else {
                new Editor(null, 1, true);
            }

            problemFiles = null;
            pbButton.setText("Choose problem");
        });
        add(editProblemButton);

        problemLabel.setBounds(15, 65, 140, 25);
        add(problemLabel);

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
        timeoutSpinner.addChangeListener(e -> {
            if (Math.abs((double) timeoutSpinner.getValue() - 42.0) < 0.0001) {
                WindowsManager.h2g2("42 is the answer");
            }
        });
        timeoutSpinner.setBounds(100, 225, 150, 25);
        add(timeoutSpinner);

        timeLabel.setBounds(15, 225, 150, 25);
        add(timeLabel);

        planButton = new JButton("Resolve this problem !");
        planButton.setBounds(65, 270, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> resolve(domainFile, problemFiles));
        add(planButton);
    }

    /**
     * Creates token and adds it into the Queue.
     *
     * @param domainFile   the PDDL domain file.
     * @param problemFiles the list of PDDL problem files.
     */
    private void resolve(File domainFile, Vector<File> problemFiles) {
        final StateSpacePlannerFactory plannerFactory = StateSpacePlannerFactory.getInstance();
        final double weight = (double) weightSpinner.getValue();
        final double timeout = (double) timeoutSpinner.getValue() * 1000;
        AbstractStateSpacePlanner planner = null;

        if (plannerName.equals("HSP")) {
            planner = plannerFactory.getPlanner(Planner.Name.HSP, (int) timeout, heuristic, weight, true, 1);
        } else if (plannerName.equals("FF")) {
            planner = plannerFactory.getPlanner(Planner.Name.FF, (int) timeout, heuristic, weight, true, 1);
        } else if (plannerName.equals("GenericPlanner")) {

            StateSpaceStrategy stateSpaceStrategy = null;

            if (strategyName != null) {
                if (strategyName.equals("A*")) {
                    stateSpaceStrategy = new AStar((int) timeout, heuristic, weight);
                } else if (strategyName.equals("Enforced Hill Climbing")) {
                    stateSpaceStrategy = new EnforcedHillClimbing((int) timeout, heuristic, weight);
                } else if (strategyName.equals("Breadth First Search")) {
                    stateSpaceStrategy = new BreadthFirstSearch((int) timeout);
                } else if (strategyName.equals("Depth First Search")) {
                    stateSpaceStrategy = new DepthFirstSearch((int) timeout);
                } else if (strategyName.equals("Greedy Best First Search")) {
                    stateSpaceStrategy = new GreedyBestFirstSearch((int) timeout, heuristic, weight);
                }
            }

            if (stateSpaceStrategy != null) {
                planner = new GenericPlanner(true, 1, stateSpaceStrategy);
            }
        }

        if (planner != null) {
            if (problemFiles != null && domainFile != null) {
                for (File file : problemFiles) {
                    final Token token = new Token(domainFile, file, planner, plannerName);

                    if (token.isRunnable() && TriggerAction.isEngineManagerRunning()) {
                        if (!TriggerAction.getListModel().contains(token)) {
                            TriggerAction.getListModel().addElement(token);
                            TriggerAction.getQueue().addTokenInQueue(token);
                        }
                    }

                    TriggerAction.setTokenRemainingEngineManagerPanel();
                }
            }
        }
    }
}
