package pddl4gui.gui.panel.local;

import pddl4gui.gui.VAL;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WebPage;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.LocalToken;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class implements the MenuSolverPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays the options of the Solver JFrame..
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class MenuSolverPanel extends JMenuBar implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JButton of the MenuSolverPanel.
     */
    private final JMenuItem valItem;
    private final JMenuItem saveResultTxt;
    private final JMenuItem saveResultJson;

    /**
     * Returns the VAL button.
     *
     * @param status the status of the button.
     */
    public void enableValButton(final boolean status) {
        valItem.setEnabled(status);
    }

    /**
     * Enables or disables the "save to text" button.
     *
     * @param status the status of the button.
     */
    public void enableSaveTxtButton(final boolean status) {
        saveResultTxt.setEnabled(status);
    }

    /**
     * Enables or disables the "save to json" button.
     *
     * @param status the status of the button.
     */
    public void enableSaveJsonButton(final boolean status) {
        saveResultJson.setEnabled(status);
    }

    /**
     * Creates a new MenuSolverPanel.
     */
    public MenuSolverPanel() {

        JMenu resultMenu = new JMenu("Result");
        resultMenu.setIcon(Icons.getResultIcon());

        valItem = new JMenuItem(new MenuItemAction("Validate result", Icons.getValidateIcon(),
                KeyEvent.VK_V));
        valItem.setEnabled(false);
        valItem.addActionListener(e -> {
            if (TriggerAction.isTokenListPanelJListSelectedValueSolved()) {
                final LocalToken token = TriggerAction.getTokenListPanelJListSelectedValue();
                new VAL(token.getDomainFile(), token.getProblemFile(),
                        token.getSolutionString(), token.isSolved());
            }
        });

        saveResultTxt = new JMenuItem(new MenuItemAction("Save in txt", Icons.getSaveTxtIcon(),
                KeyEvent.VK_T));
        saveResultTxt.setEnabled(false);
        saveResultTxt.addActionListener(e -> {
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

        saveResultJson = new JMenuItem(new MenuItemAction("Save in json", Icons.getSaveJsonIcon(),
                KeyEvent.VK_J));
        saveResultJson.setEnabled(false);
        saveResultJson.addActionListener(e -> {
            if (TriggerAction.isTokenListPanelJListSelectedValueSolved()) {
                File tempFile = FileTools.saveFile(this, 5);
                if (FileTools.checkFile(tempFile)) {
                    FileTools.writeInFile(tempFile, TriggerAction.getTokenListPanelJListSelectedValue()
                            .getSolutionJson());
                }
            }
        });

        resultMenu.add(valItem);
        resultMenu.addSeparator();
        resultMenu.add(saveResultTxt);
        resultMenu.add(saveResultJson);

        JMenu solverMenu = new JMenu("Solver");
        solverMenu.setIcon(Icons.getSolverIcon());

        final JMenuItem resetItem = new JMenuItem(new MenuItemAction("Reset solver", Icons.getResetIcon(),
                KeyEvent.VK_R));
        resetItem.setEnabled(true);
        resetItem.addActionListener(e -> {
            valItem.setEnabled(false);
            saveResultTxt.setEnabled(false);
            saveResultJson.setEnabled(false);
            TriggerAction.resetSolver();
        });

        final JMenuItem exitItem = new JMenuItem(new MenuItemAction("Exit solver", Icons.getExitIcon(),
                KeyEvent.VK_E));
        exitItem.addActionListener(e -> System.exit(0));

        solverMenu.add(resetItem);
        solverMenu.addSeparator();
        solverMenu.add(exitItem);

        JMenu helpMenu = new JMenu("help");
        helpMenu.setIcon(Icons.getInfoIcon());

        final JMenuItem howToItem = new JMenuItem(new MenuItemAction("Wiki PDDL4J", Icons.getHelpIcon(),
                KeyEvent.VK_H));
        howToItem.setEnabled(true);
        howToItem.addActionListener(e -> {
            try {
                WebPage.openWebpage(new URL("https://github.com/pellierd/pddl4j/wiki"));
            } catch (MalformedURLException exc) {
                System.out.println(exc.getMessage());
            }
        });

        final JMenuItem aboutItem = new JMenuItem(new MenuItemAction("About PDDL4GUI", Icons.getAboutIcon(),
                KeyEvent.VK_A));

        aboutItem.setEnabled(true);
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, WindowsManager.LOCAL_VERSION);
        });

        helpMenu.add(howToItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);

        this.add(resultMenu);
        this.add(solverMenu);
        this.add(Box.createHorizontalGlue());
        this.add(helpMenu);
    }

    /**
     * MenuItemAction for JMenuItem.
     */
    private class MenuItemAction extends AbstractAction {

        /**
         * Creates new MenuItemAction for JMenuItem.
         * @param text     the text of JMenuItem.
         * @param icon     the icon of JMenuItem.
         * @param mnemonic the mnemonic of JMenuItem.
         */
        MenuItemAction(String text, ImageIcon icon,
                              Integer mnemonic) {
            super(text);

            putValue(SMALL_ICON, icon);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * Event on click for JMenuItem.
         *
         * @param e the event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("[Action performed] " + e.getActionCommand());
        }
    }


}
