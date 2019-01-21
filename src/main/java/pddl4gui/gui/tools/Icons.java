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
    private static final ImageIcon saveIcon = new ImageIcon("resources/icons/save.png");
    private static final ImageIcon resetIcon = new ImageIcon("resources/icons/reset.png");
    private static final ImageIcon generateIcon = new ImageIcon("resources/icons/generate.png");
    private static final ImageIcon exitIcon = new ImageIcon("resources/icons/exit.png");
    private static final ImageIcon validateIcon = new ImageIcon("resources/icons/validate.png");
    private static final ImageIcon startIcon = new ImageIcon("resources/icons/start.png");
    private static final ImageIcon stopIcon = new ImageIcon("resources/icons/stop.png");

    /**
     * Returns the save icon.
     *
     * @return the save icon.
     */
    public static ImageIcon getSaveIcon() {
        return saveIcon;
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
     * Returns the exit icon.
     *
     * @return the exit icon.
     */
    public static ImageIcon getExitIcon() {
        return exitIcon;
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
     * Returns the VAL icon.
     *
     * @return the VAL icon.
     */
    public static ImageIcon getValidateIcon() {
        return validateIcon;
    }

    /**
     * Returns the start icon.
     *
     * @return the start icon.
     */
    public static ImageIcon getStartIcon() {
        return startIcon;
    }

    /**
     * Returns the stop icon.
     *
     * @return the stop icon.
     */
    public static ImageIcon getStopIcon() {
        return stopIcon;
    }
}
