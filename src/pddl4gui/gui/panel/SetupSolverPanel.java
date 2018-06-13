package pddl4gui.gui.panel;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlanner;
import fr.uga.pddl4j.planners.statespace.StateSpacePlannerFactory;
import pddl4gui.gui.Editor;
import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TokenList;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.io.File;
import java.util.Vector;

public class SetupSolverPanel extends JPanel {

    final private Solver solver;
    final private JSpinner weightSpinner, timeoutSpinner;
    final private JButton domainButton, pbButton, editDomainButton, editProblemButton, planButton;
    private File domainFile;
    private Vector<File> problemFiles;
    private Heuristic.Type heuristic = Heuristic.Type.FAST_FORWARD;
    private Planner.Name plannerName = Planner.Name.HSP;

    public SetupSolverPanel(Solver solver) {
        this.solver = solver;

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
        editDomainButton.setEnabled(false);
        editDomainButton.addActionListener(e -> {
            enableDomainButton(false);
            new Editor(solver, domainFile, 0);

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
        editProblemButton.setEnabled(false);
        editProblemButton.addActionListener(e -> {
            enablePBButton(false);
            new Editor(solver, problemFiles.firstElement(), 1);

            problemFiles = null;
            pbButton.setText("Choose problem");
        });
        add(editProblemButton);

        problemLabel.setBounds(15, 65, 140, 25);
        add(problemLabel);

        final JComboBox plannerComboBox = new JComboBox<>(Planner.Name.values());
        plannerComboBox.setBounds(100, 105, 150, 25);
        plannerComboBox.setSelectedItem(plannerName);
        plannerComboBox.addActionListener(e -> plannerName = (Planner.Name) plannerComboBox.getSelectedItem());
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

    private void resolve(File domainFile, Vector<File> problemFiles) {
        final StateSpacePlannerFactory plannerFactory = StateSpacePlannerFactory.getInstance();
        final double weight = (double) weightSpinner.getValue();
        final double timeout = (double) timeoutSpinner.getValue() * 1000;

        final AbstractStateSpacePlanner planner = plannerFactory.getPlanner(plannerName, (int) timeout, heuristic,
                weight, true, 1);

        if (problemFiles != null && domainFile != null) {
            for (File file : problemFiles) {
                final Token token = new Token(domainFile, file, planner, plannerName);

                if (token.isRunnable() && solver.getEngineManagerPanel().isStatus()) {
                    if (!TokenList.getListModel().contains(token)) {
                        TokenList.getListModel().addElement(token);
                        solver.getQueue().addToken(token);
                    }
                }

                solver.getEngineManagerPanel().setTokensRemaining();
            }
        }
    }

    public void enableDomainButton(boolean enable) {
        editDomainButton.setEnabled(enable);
        domainButton.setEnabled(enable);
        planButton.setEnabled(enable);
    }

    public void enablePBButton(boolean enable) {
        editProblemButton.setEnabled(enable);
        pbButton.setEnabled(enable);
        planButton.setEnabled(enable);
    }
}
