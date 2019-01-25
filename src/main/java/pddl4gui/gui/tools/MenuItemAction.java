package pddl4gui.gui.tools;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 * This class implements the MenuItemAction class for JMenuItem of <code>PDDL4GUI</code>.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class MenuItemAction extends AbstractAction {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new MenuItemAction for JMenuItem.
     * @param text     the text of JMenuItem.
     * @param icon     the icon of JMenuItem.
     * @param mnemonic the mnemonic of JMenuItem.
     */
    public MenuItemAction(String text, ImageIcon icon,
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
