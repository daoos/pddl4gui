package pddl4gui.gui.tools;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * This class implements the Icons class of <code>PDDL4GUI</code>.
 * This class provides all ImageIcon used in PDDL4GUI.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Icons implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The ImageIcon used in PDDL4GUI.
     */
    private static final ImageIcon aboutIcon = new ImageIcon("resources/icons/about.png");
    private static final ImageIcon exitIcon = new ImageIcon("resources/icons/exit.png");
    private static final ImageIcon generateIcon = new ImageIcon("resources/icons/generate.png");
    private static final ImageIcon helpIcon = new ImageIcon("resources/icons/help.png");
    private static final ImageIcon infoIcon = new ImageIcon("resources/icons/helpMenu.png");
    private static final ImageIcon resetIcon = new ImageIcon("resources/icons/clean.png");
    private static final ImageIcon editIcon = new ImageIcon("resources/icons/edit.png");
    private static final ImageIcon saveJsonIcon = new ImageIcon("resources/icons/saveJson.png");
    private static final ImageIcon saveTxtIcon = new ImageIcon("resources/icons/saveTxt.png");
    private static final ImageIcon solverIcon = new ImageIcon("resources/icons/solverMenu.png");
    private static final ImageIcon validateIcon = new ImageIcon("resources/icons/val.png");
    private static final ImageIcon displayIcon = new ImageIcon("resources/icons/display.png");
    private static final ImageIcon garbageIcon = new ImageIcon("resources/icons/garbage.png");
    private static final ImageIcon logIcon = new ImageIcon("resources/icons/log.png");
    private static final ImageIcon toolsIcon = new ImageIcon("resources/icons/tools.png");
    private static final ImageIcon resultIcon = new ImageIcon("resources/icons/result.png");

    /**
     * Returns the about icon.
     *
     * @return the about icon.
     */
    public static ImageIcon getAboutIcon() {
        return aboutIcon;
    }

    /**
     * Returns the exit icon.
     *
     * @return the exit icon.
     */
    public static ImageIcon getExitIcon() {
        return exitIcon;
    }

    /**
     * Returns the generate report icon.
     *
     * @return the generate report icon.
     */
    public static ImageIcon getGenerateIcon() {
        return generateIcon;
    }

    /**
     * Returns the help icon.
     *
     * @return the help icon.
     */
    public static ImageIcon getHelpIcon() {
        return helpIcon;
    }

    /**
     * Returns the info icon.
     *
     * @return the info icon.
     */
    public static ImageIcon getInfoIcon() {
        return infoIcon;
    }

    /**
     * Returns the reset icon.
     *
     * @return the reset icon.
     */
    public static ImageIcon getResetIcon() {
        return resetIcon;
    }

    /**
     * Returns the result icon.
     *
     * @return the result icon.
     */
    public static ImageIcon getResultIcon() {
        return resultIcon;
    }

    /**
     * Returns the save json icon.
     *
     * @return the save json icon.
     */
    public static ImageIcon getSaveJsonIcon() {
        return saveJsonIcon;
    }

    /**
     * Returns the save txt icon.
     *
     * @return the save txt icon.
     */
    public static ImageIcon getSaveTxtIcon() {
        return saveTxtIcon;
    }

    /**
     * Returns the solver icon.
     *
     * @return the solver icon.
     */
    public static ImageIcon getSolverIcon() {
        return solverIcon;
    }

    /**
     * Returns the VAL icon.
     *
     * @return the VAL icon.
     */
    public static ImageIcon getValidateIcon() {
        return validateIcon;
    }

    /**
     * Returns the display icon.
     *
     * @return the display icon.
     */
    public static ImageIcon getDisplayIcon() {
        return displayIcon;
    }

    /**
     * Returns the garbage icon.
     *
     * @return the garbage icon.
     */
    public static ImageIcon getGarbageIcon() {
        return garbageIcon;
    }

    /**
     * Returns the log icon.
     *
     * @return the log icon.
     */
    public static ImageIcon getLogIcon() {
        return logIcon;
    }

    /**
     * Returns the tools icon.
     *
     * @return the tools icon.
     */
    public static ImageIcon getToolsIcon() {
        return toolsIcon;
    }

    /**
     * Returns the edit icon.
     *
     * @return the edit icon.
     */
    public static ImageIcon getEditIcon() {
        return editIcon;
    }
}
