package pddl4gui.gui.panel;

import pddl4gui.gui.VAL;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.RestToken;

import java.io.File;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class implements the MenuRestPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays the options of the Rest Solver JFrame.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class MenuRestPanel extends JPanel implements Serializable {

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
     * @param status the status of the button.
     */
    public void enableValButton(final boolean status) {
        valButton.setEnabled(status);
    }

    /**
     * Enables or disables the "save to text" button.
     *
     * @param status the status of the button.
     */
    public void enableSaveTxtButton(final boolean status) {
        saveTxtButton.setEnabled(status);
    }

    /**
     * Enables or disables the "save to json" button.
     *
     * @param status the status of the button.
     */
    public void enableSaveJsonButton(final boolean status) {
        saveJsonButton.setEnabled(status);
    }

    /**
     * Creates a new MenuSolverPanel.
     */
    public MenuRestPanel() {
        valButton = new JButton(Icons.getValidateIcon());
        valButton.setBounds(10, 10, 40, 40);
        valButton.setToolTipText("VAL on solution");
        valButton.setEnabled(false);
        valButton.addActionListener(e -> {
            final int id = TriggerAction.getCurrentComputationId();
            if (id != -1) {
                final RestToken restToken = TriggerAction.getRestTokenFromId(id);
                if (restToken != null) {
                    new VAL(restToken.getDomainFile(), restToken.getProblemFile(),
                            TriggerAction.getResult(), true);
                }
            }
        });
        add(valButton);

        saveTxtButton = new JButton(Icons.getSaveIcon());
        saveTxtButton.setBounds(60, 10, 40, 40);
        saveTxtButton.setToolTipText("Save solution (txt)");
        saveTxtButton.addActionListener(e -> {
            File tempFile = FileTools.saveFile(this, 1);
            if (FileTools.checkFile(tempFile)) {
                FileTools.writeInFile(tempFile, TriggerAction.getResult());
            }
        });
        saveTxtButton.setEnabled(false);
        add(saveTxtButton);

        saveJsonButton = new JButton(Icons.getSaveIcon());
        saveJsonButton.setBounds(110, 10, 40, 40);
        saveJsonButton.setToolTipText("Save solution (txt)");
        saveJsonButton.addActionListener(e -> {
            File tempFile = FileTools.saveFile(this, 5);
            if (FileTools.checkFile(tempFile)) {
                FileTools.writeInFile(tempFile, TriggerAction.getResult());
            }
        });
        saveJsonButton.setEnabled(false);
        add(saveJsonButton);

        final JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(160, 10, 40, 40);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }
}
