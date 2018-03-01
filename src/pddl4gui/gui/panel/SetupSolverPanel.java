package pddl4gui.gui.panel;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import pddl4gui.context.planner.Planner;
import pddl4gui.context.planner.PlannerDefault;
import pddl4gui.gui.Editor;
import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;

import javax.swing.*;
import java.io.File;

public class SetupSolverPanel extends JPanel {

    final private JSpinner weightSpinner, timeoutSpinner;
    final private JButton domainButton, pbButton, editDomainButton, editProblemButton, planButton;
    private File domainFile;
    private File problemFile;
    private Heuristic.Type heuristic = Heuristic.Type.FAST_FORWARD;
    private Planner.Type planner = Planner.Type.HSP;

    public JSpinner getWeightSpinner() {
        return weightSpinner;
    }

    public JSpinner getTimeoutSpinner() {
        return timeoutSpinner;
    }

    public File getDomainFile() {
        return domainFile;
    }

    public File getProblemFile() {
        return problemFile;
    }

    public Heuristic.Type getHeuristic() {
        return heuristic;
    }

    public Planner.Type getPlanner() {
        return planner;
    }

    public SetupSolverPanel(Solver parent) {
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
            File tempFile = FileTools.getFile(this, 0);
            if (!FileTools.checkFile(tempFile)) {
                domainFile = tempFile;
                domainButton.setText(tempFile.getName());
                editDomainButton.setEnabled(true);
            }
        });
        add(domainButton);

        editDomainButton.setBounds(275, 25, 25, 25);
        editDomainButton.setEnabled(false);
        editDomainButton.addActionListener(e -> {
            enableDomainButton(false);
            new Editor(parent, domainFile, 0);
        });
        add(editDomainButton);

        domainLabel.setBounds(15, 25, 140, 25);
        add(domainLabel);

        pbButton.setBounds(100, 65, 150, 25);
        pbButton.setEnabled(true);
        pbButton.addActionListener(e -> {
            File tempFile = FileTools.getFile(this, 0);
            if (!FileTools.checkFile(tempFile)) {
                problemFile = tempFile;
                pbButton.setText(tempFile.getName());
                editProblemButton.setEnabled(true);
            }

        });
        add(pbButton);

        editProblemButton.setBounds(275, 65, 25, 25);
        editProblemButton.setEnabled(false);
        editProblemButton.addActionListener(e -> {
            enablePBButton(false);
            new Editor(parent, problemFile, 1);
        });
        add(editProblemButton);

        problemLabel.setBounds(15, 65, 140, 25);
        add(problemLabel);

        JComboBox plannerComboBox = new JComboBox<>(Planner.Type.values());
        plannerComboBox.setBounds(100, 105, 150, 25);
        plannerComboBox.setSelectedItem(planner);
        plannerComboBox.addActionListener(e -> planner = (Planner.Type) plannerComboBox.getSelectedItem());
        add(plannerComboBox);

        plannerLabel.setBounds(15, 105, 140, 25);
        add(plannerLabel);

        JComboBox heuristicComboBox = new JComboBox<>(Heuristic.Type.values());
        heuristicComboBox.setBounds(100, 145, 150, 25);
        heuristicComboBox.setSelectedItem(heuristic);
        heuristicComboBox.addActionListener(e -> heuristic = (Heuristic.Type) heuristicComboBox.getSelectedItem());
        add(heuristicComboBox);

        heuristicLabel.setBounds(15, 145, 150, 25);
        add(heuristicLabel);

        SpinnerNumberModel modelWeight = new SpinnerNumberModel(1.0, 0.0, 10.0, 0.1);
        weightSpinner = new JSpinner(modelWeight);
        weightSpinner.setBounds(100, 185, 150, 25);
        add(weightSpinner);

        weightLabel.setBounds(15, 185, 150, 25);
        add(weightLabel);

        SpinnerNumberModel modelTimeout = new SpinnerNumberModel(PlannerDefault.getPlannerDefaultTimeout(),
                0.0, 10000.0, 1);
        timeoutSpinner = new JSpinner(modelTimeout);
        timeoutSpinner.setBounds(100, 225, 150, 25);
        add(timeoutSpinner);

        timeLabel.setBounds(15, 225, 150, 25);
        add(timeLabel);

        planButton = new JButton("Resolve this problem !");
        planButton.setBounds(75, 290, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> parent.resolve());
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
