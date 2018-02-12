package solver.gui.panel;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;
import solver.gui.Editor;
import solver.gui.tools.FileTools;
import solver.gui.tools.Icons;

import javax.swing.*;
import java.io.File;

public class SetupSolverPanel extends JPanel {
    private JSpinner weightSpinner, timeoutSpinner;
    private File domainFile;
    private File problemFile;
    private Heuristic.Type heuristic = Heuristic.Type.FAST_FORWARD;
    private Planner.Name planner = Planner.Name.HSP;

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

    public Planner.Name getPlanner() {
        return planner;
    }

    public SetupSolverPanel(){
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver parameters"));

        JButton domainButton = new JButton("Choose domain");
        JButton pbButton = new JButton("Choose problem");
        JButton editDomainButton = new JButton(Icons.getEditorIcon());
        JButton editProblemButton = new JButton(Icons.getEditorIcon());

        domainButton.setBounds(15, 25, 150, 25);
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

        editDomainButton.setBounds(190, 25, 25, 25);
        editDomainButton.setEnabled(false);
        editDomainButton.addActionListener(e -> {
            editDomainButton.setEnabled(false);
            new Editor(editDomainButton, domainFile);
        });
        add(editDomainButton);

        pbButton.setBounds(15, 65, 150, 25);
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

        editProblemButton.setBounds(190, 65, 25, 25);
        editProblemButton.setEnabled(false);
        editProblemButton.addActionListener(e -> {
            editProblemButton.setEnabled(false);
            new Editor(editProblemButton, problemFile);
        });
        add(editProblemButton);

        JComboBox heuristicComboBox = new JComboBox(Heuristic.Type.values());
        heuristicComboBox.setBounds(15, 105, 150, 25);
        heuristicComboBox.setSelectedItem(heuristic);
        heuristicComboBox.addActionListener(e -> {
            heuristic = (Heuristic.Type) heuristicComboBox.getSelectedItem();
        });
        add(heuristicComboBox);

        SpinnerNumberModel modelWeight = new SpinnerNumberModel(1.0, 0.0, 10.0, 0.1);
        weightSpinner = new JSpinner(modelWeight);
        weightSpinner.setBounds(190, 105, 125, 25);
        add(weightSpinner);

        JComboBox plannerComboBox = new JComboBox(Planner.Name.values());
        plannerComboBox.setBounds(15, 145, 150, 25);
        plannerComboBox.setSelectedItem(planner);
        plannerComboBox.addActionListener(e -> {
            planner = (Planner.Name) plannerComboBox.getSelectedItem();
        });
        add(plannerComboBox);

        SpinnerNumberModel modelTimeout = new SpinnerNumberModel(Planner.DEFAULT_TIMEOUT, 0.0, 10000.0, 1);
        timeoutSpinner = new JSpinner(modelTimeout);
        timeoutSpinner.setBounds(190, 145, 125, 25);
        add(timeoutSpinner);
    }
}
