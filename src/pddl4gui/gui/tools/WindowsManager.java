package pddl4gui.gui.tools;

import java.awt.*;

public class WindowsManager {
    public static Point location;
    public static int width;
    public final static String NAME = "PDDL 4 GUI";
    public final static double VERSION = 0.42;

    public static void setPoint(Point location) {
        WindowsManager.location = location;
    }

    public static void setWidth(int width) {
        WindowsManager.width = width;
    }

    public static Point setWindowsLocation() {
        int margin = 100;
        return new Point((int) location.getX() + (width / 2) + margin, (int) location.getY());
    }
}
