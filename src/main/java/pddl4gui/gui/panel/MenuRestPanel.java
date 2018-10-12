package pddl4gui.gui.panel;

import pddl4gui.gui.VAL;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TriggerAction;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.io.File;
import java.io.Serializable;

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
     * Creates a new MenuSolverPanel.
     */
    public MenuRestPanel() {
        valButton = new JButton(Icons.getValidateIcon());
        valButton.setBounds(10, 10, 40, 40);
        valButton.setToolTipText("VAL on solution");
        valButton.setEnabled(false);
        valButton.addActionListener(e -> {

        });
        add(valButton);

        saveTxtButton = new JButton(Icons.getSaveIcon());
        saveTxtButton.setBounds(60, 10, 40, 40);
        saveTxtButton.setToolTipText("Save solution (txt)");
        saveTxtButton.addActionListener(e -> {

        });
        saveTxtButton.setEnabled(false);
        add(saveTxtButton);

        final JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(110, 10, 40, 40);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }
}
