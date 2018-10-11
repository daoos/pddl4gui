package pddl4gui.gui.panel;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlanner;
import pddl4gui.gui.Editor;
import pddl4gui.gui.tools.DrawCircle;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.WindowsManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.io.File;
import java.io.Serializable;
import java.util.Vector;

/**
 * This class implements the InfoRestPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays informations about the Rest planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class InfoRestPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The DrawCircle to display REST status.
     */
    private final DrawCircle circlePanel;

    /**
     * The JButton of the SetupSolverPanel.
     */
    private final JButton getResultButton;
    private final JButton getResultJsonButton;
    private final JButton deleteResultButton;

    /**
     * Creates a new SetupRestPanel associated to the RestSolver main JFrame.
     */
    public InfoRestPanel(final String url) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver informations"));

        final JLabel statusLabel = new JLabel("REST solver alive ?");
        final JTextField urlRest = new JTextField(url, 300);
        final JTextField resultID = new JTextField("id", 300);
        final JTextField status = new JTextField("status", 300);

        circlePanel = new DrawCircle(3, 3, 15);
        circlePanel.setBounds(140, 16, 25, 25);
        add(circlePanel);

        statusLabel.setBounds(15, 15, 120, 25);
        add(statusLabel);

        urlRest.setEditable(false);
        urlRest.setBounds(15, 50, 270, 25);
        add(urlRest);

        resultID.setEditable(true);
        resultID.setBounds(15, 130, 270, 25);
        add(resultID);

        status.setEditable(false);
        status.setBounds(15, 165, 270, 25);
        add(status);

        getResultButton = new JButton("Get result (text)");
        getResultJsonButton = new JButton("Get result (json)");
        deleteResultButton = new JButton("Delete result");

        getResultButton.setBounds(50, 200, 200, 25);
        getResultButton.setEnabled(true);
        getResultButton.addActionListener(e -> {

        });
        add(getResultButton);

        getResultJsonButton.setBounds(50, 235, 200, 25);
        getResultJsonButton.setEnabled(true);
        getResultJsonButton.addActionListener(e -> {

        });
        add(getResultJsonButton);


        deleteResultButton.setBounds(50, 270, 200, 25);
        deleteResultButton.setEnabled(true);
        deleteResultButton.addActionListener(e -> {

        });
        add(deleteResultButton);
    }
}
