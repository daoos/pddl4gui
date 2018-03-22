package pddl4gui.gui.panel;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import pddl4gui.gui.Editor;
import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.Popup;
import pddl4gui.planners.Planner;
import pddl4gui.planners.PlannerFactory;

import javax.swing.*;
import java.io.File;
import java.util.Vector;

public class SetupSolverPanel extends JPanel {

    final private JSpinner weightSpinner, timeoutSpinner;
    final private JButton domainButton, pbButton, editDomainButton, editProblemButton, planButton;
    private File domainFile;
    private Vector<File> problemFiles;
    private Heuristic.Type heuristic = Heuristic.Type.FAST_FORWARD;
    private Planner.Type planner = Planner.Type.HSP;

    public JSpinner getWeightSpinner() {
        return weightSpinner;
    }

    public JSpinner getTimeoutSpinner() {
        return timeoutSpinner;
    }

    public Heuristic.Type getHeuristic() {
        return heuristic;
    }

    public Planner.Type getPlanner() {
        return planner;
    }

    public SetupSolverPanel(Solver solver) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver parameters"));

        final JLabel domainLabel = new JLabel("Domain file:");
        final JLabel problemLabel = new JLabel("Problem file:");
        final JLabel plannerLabel = new JLabel("Planner:");
        final JLabel heuristicLabel = new JLabel("Heuristic:");
        final JLabel weightLabel = new JLabel("Weight:");
        final JLabel timeLabel = new JLabel("Time out:");

        domainButton = new JButton("Choose domain");
        pbButton = new JButton("Choose problem");
        editDomainButton = new JButton(Icons.getEditorIcon());
        editProblemButton = new JButton(Icons.getEditorIcon());

        domainButton.setBounds(100, 25, 150, 25);
        domainButton.setEnabled(true);
        domainButton.addActionListener(e -> {
            final Vector<File> domainTempFile = FileTools.getFiles(this, 0, false, domainButton);
            if (domainTempFile.size() == 1) {
                domainFile = domainTempFile.firstElement();
                if (FileTools.checkFile(domainFile)) {
                    domainButton.setText(domainFile.getName());
                    editDomainButton.setEnabled(true);
                }
            } else {
                editDomainButton.setEnabled(false);
            }
        });
        add(domainButton);

        editDomainButton.setBounds(275, 25, 25, 25);
        editDomainButton.setEnabled(false);
        editDomainButton.addActionListener(e -> {
            enableDomainButton(false);
            new Editor(solver, domainFile, 0);
        });
        add(editDomainButton);

        domainLabel.setBounds(15, 25, 140, 25);
        add(domainLabel);

        pbButton.setBounds(100, 65, 150, 25);
        pbButton.setEnabled(true);
        pbButton.addActionListener(e -> {
            problemFiles = FileTools.getFiles(this, 0, true, pbButton);
            if (problemFiles.size() == 1) {
                if (FileTools.checkFile(problemFiles.firstElement())) {
                    pbButton.setText(problemFiles.firstElement().getName());
                    editProblemButton.setEnabled(true);
                }
            } else if (problemFiles.size() >= 1) {
                pbButton.setText(problemFiles.size() + " problems");
                editProblemButton.setEnabled(false);
            } else {
                editProblemButton.setEnabled(false);
            }
        });
        add(pbButton);

        editProblemButton.setBounds(275, 65, 25, 25);
        editProblemButton.setEnabled(false);
        editProblemButton.addActionListener(e -> {
            enablePBButton(false);
            new Editor(solver, problemFiles.firstElement(), 1);
        });
        add(editProblemButton);

        problemLabel.setBounds(15, 65, 140, 25);
        add(problemLabel);

        final JComboBox plannerComboBox = new JComboBox<>(Planner.Type.values());
        plannerComboBox.setBounds(100, 105, 150, 25);
        plannerComboBox.setSelectedItem(planner);
        plannerComboBox.addActionListener(e -> planner = (Planner.Type) plannerComboBox.getSelectedItem());
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

        final SpinnerNumberModel modelTimeout = new SpinnerNumberModel(PlannerFactory.getPlannerDefaultTimeout(),
                0.0, 10000.0, 1);
        timeoutSpinner = new JSpinner(modelTimeout);
        timeoutSpinner.addChangeListener( e -> {
            if (Math.abs((double) timeoutSpinner.getValue() - 42.0) < 0.0001) {
                new Popup(new ImageIcon("resources/icons/42.png"));
            }
        });
        timeoutSpinner.setBounds(100, 225, 150, 25);
        add(timeoutSpinner);

        timeLabel.setBounds(15, 225, 150, 25);
        add(timeLabel);

        planButton = new JButton("Resolve this problem !");
        planButton.setBounds(65, 270, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> solver.resolve(domainFile, problemFiles));
        add(planButton);
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
