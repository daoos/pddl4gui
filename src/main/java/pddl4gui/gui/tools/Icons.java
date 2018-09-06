package main.java.pddl4gui.gui.tools;

import javax.swing.ImageIcon;
import java.io.Serializable;

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
    private static final ImageIcon invertIcon = new ImageIcon("resources/icons/invert.png");
    private static final ImageIcon exitIcon = new ImageIcon("resources/icons/exit.png");
    private static final ImageIcon wordwrapIcon = new ImageIcon("resources/icons/wordwrap.png");
    private static final ImageIcon editorIcon = new ImageIcon("resources/icons/editor.png");
    private static final ImageIcon validateIcon = new ImageIcon("resources/icons/validate.png");
    private static final ImageIcon searchIcon = new ImageIcon("resources/icons/search.png");
    private static final ImageIcon refreshIcon = new ImageIcon("resources/icons/refresh.png");
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
     * Returns the word wrap icon.
     *
     * @return the word wrap icon.
     */
    public static ImageIcon getWordwrapIcon() {
        return wordwrapIcon;
    }

    /**
     * Returns the editor icon.
     *
     * @return the editor icon.
     */
    public static ImageIcon getEditorIcon() {
        return editorIcon;
    }

    /**
     * Returns the search icon.
     *
     * @return the search icon.
     */
    public static ImageIcon getSearchIcon() {
        return searchIcon;
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
     * Returns the invert color icon.
     *
     * @return the invert color icon.
     */
    public static ImageIcon getInvertIcon() {
        return invertIcon;
    }

    /**
     * Returns the refresh icon.
     *
     * @return the refresh icon.
     */
    public static ImageIcon getRefreshIcon() {
        return refreshIcon;
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
