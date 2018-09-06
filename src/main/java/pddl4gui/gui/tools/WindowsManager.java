package pddl4gui.gui.tools;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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
    private static int width;

    /**
     * The height of PDDL4GUI main frame.
     */
    private static int height;

    /**
     * The title of PDDL4GUI main frame.
     */
    public final static String NAME = "PDDL 4 GUI";

    /**
     * Sets the origin point of PDDL4GUI.
     *
     * @param location the origin point of PDDL4GUI.
     */
    public static void setPoint(Point location) {
        WindowsManager.location = location;
    }

    /**
     * Sets the width of PDDL4GUI main frame.
     *
     * @param width the width of PDDL4GUI main frame.
     */
    public static void setWidth(int width) {
        WindowsManager.width = width;
    }

    /**
     * Sets the height of PDDL4GUI main frame.
     *
     * @param height the height of PDDL4GUI main frame.
     */
    public static void setHeight(int height) {
        WindowsManager.height = height;
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

    /**
     * Easter eggs .
     *
     * @param string easter eggs.
     */
    public static void h2g2(final String string) {
        BufferedImage image = new BufferedImage(144, 32, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Dialog", Font.PLAIN, 14));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString(string, 6, 24);

        for (int y = 0; y < 32; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < 144; x++)
                sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
            if (sb.toString().trim().isEmpty()) continue;
            System.out.println(sb);
        }
    }
}
