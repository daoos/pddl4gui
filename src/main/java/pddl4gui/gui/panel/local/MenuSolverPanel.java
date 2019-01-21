package pddl4gui.gui.panel.local;

import pddl4gui.gui.VAL;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.LocalToken;

import java.io.File;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class implements the MenuSolverPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays the options of the Solver JFrame..
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class MenuSolverPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JButton of the MenuSolverPanel.
     */
    private final JButton valButton;
    private final JButton saveTxtButton;
    private final JButton saveJsonButton;

    /**
     * Returns the VAL button.
     *
     * @return the VAL button.
     */
    public JButton getValButton() {
        return valButton;
    }

    /**
     * Returns the save to text button.
     *
     * @return the save to text button.
     */
    public JButton getSaveTxtButton() {
        return saveTxtButton;
    }

    /**
     * Returns the save to json button.
     *
     * @return the save to json button.
     */
    public JButton getSaveJsonButton() {
        return saveJsonButton;
    }

    /**
     * Creates a new MenuSolverPanel.
     */
    public MenuSolverPanel() {
        valButton = new JButton(Icons.getValidateIcon());
        valButton.setBounds(10, 10, 40, 40);
        valButton.setToolTipText("VAL on solution");
        valButton.setEnabled(false);
        valButton.addActionListener(e -> {
            if (TriggerAction.isTokenListPanelJListSelectedValueSolved()) {
                final LocalToken token = TriggerAction.getTokenListPanelJListSelectedValue();
                new VAL(token.getDomainFile(), token.getProblemFile(),
                        token.getSolutionString(), token.isSolved());
            }
        });
        add(valButton);

        saveTxtButton = new JButton(Icons.getSaveIcon());
        saveTxtButton.setBounds(50, 10, 40, 40);
        saveTxtButton.setToolTipText("Save solution (txt)");
        saveTxtButton.addActionListener(e -> {
            if (TriggerAction.isTokenListPanelJListSelectedValueSolved()) {
                File tempFile = FileTools.saveFile(this, 1);
                if (FileTools.checkFile(tempFile)) {
                    if (TriggerAction.isResultPanelDetailedPlan()) {
                        FileTools.writeInFile(tempFile, TriggerAction.getTokenListPanelJListSelectedValue()
                                .getSolutionStringDetailed());
                    } else {
                        FileTools.writeInFile(tempFile, TriggerAction.getTokenListPanelJListSelectedValue()
                                .getSolutionString());
                    }
                }
            }
        });
        saveTxtButton.setEnabled(false);
        add(saveTxtButton);

        saveJsonButton = new JButton(Icons.getSaveIcon());
        saveJsonButton.setBounds(110, 10, 40, 40);
        saveJsonButton.setToolTipText("Save solution (json)");
        saveJsonButton.addActionListener(e -> {
            if (TriggerAction.isTokenListPanelJListSelectedValueSolved()) {
                File tempFile = FileTools.saveFile(this, 5);
                if (FileTools.checkFile(tempFile)) {
                    FileTools.writeInFile(tempFile, TriggerAction.getTokenListPanelJListSelectedValue()
                            .getSolutionJson());
                }
            }
        });
        saveJsonButton.setEnabled(false);
        add(saveJsonButton);

        final JButton resetButton = new JButton(Icons.getResetIcon());
        resetButton.setBounds(160, 10, 40, 40);
        resetButton.setToolTipText("Reset the solver");
        resetButton.setEnabled(true);
        resetButton.addActionListener(e -> {
            valButton.setEnabled(false);
            saveTxtButton.setEnabled(false);
            saveJsonButton.setEnabled(false);
            TriggerAction.resetSolver();
        });
        add(resetButton);

        final JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(210, 10, 40, 40);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }
}
