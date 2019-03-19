package pddl4gui.gui.tools;

import java.awt.Point;

import java.io.Serializable;

/**
 * This class implements the WindowsManager class of <code>PDDL4GUI</code>.
 * This class provides functions to manage windows in PDDL4GUI.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class WindowsManager implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The origin point of PDDL4GUI.
     */
    private static Point location;

    /**
     * The width of PDDL4GUI main frame.
     */
    private static int width = 670;

    /**
     * The height of PDDL4GUI main frame.
     */
    private static int height = 660;

    /**
     * The title of PDDL4GUI main frame.
     */
    public static final String NAME = "PDDL4GUI";

    /**
     * The version of PDDL4GUI Local.
     */
    public static final String LOCAL_VERSION = "Local solver based on PDDL4J v3.8.3";

    /**
     * The version of PDDL4GUI REST.
     */
    public static final String REST_VERSION = "REST solver based on PDDL4J WebApps v3.7.2";

    /**
     * Sets the origin point of PDDL4GUI.
     *
     * @param location the origin point of PDDL4GUI.
     */
    public static void setPoint(Point location) {
        WindowsManager.location = location;
    }

    /**
     * Gets the width of PDDL4GUI main frame.
     *
     * @return the width of PDDL4GUI main frame.
     */
    public static int getWidth() {
        return WindowsManager.width;
    }

    /**
     * Gets the height of PDDL4GUI main frame.
     *
     * @return the height of PDDL4GUI main frame.
     */
    public static int getHeight() {
        return WindowsManager.height;
    }

    /**
     * Return new location for windows according to location point, width parameter and margin.
     *
     * @return new location point.
     */
    public static Point setWindowsLocationWidth() {
        int margin = 10;
        return new Point((int) location.getX() + width + margin, (int) location.getY());
    }

    /**
     * Return new location for windows according to location point, height parameter and margin.
     *
     * @return new location point.
     */
    public static Point setWindowsLocationHeight() {
        int margin = 10;
        return new Point((int) location.getX(), (int) location.getY() + height + margin);
    }
}
