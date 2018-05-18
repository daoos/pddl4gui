package pddl4gui.gui.tools;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class WindowsManager {
    private static Point location;
    private static int width;
    public final static String NAME = "PDDL 4 GUI";

    public static void setPoint(Point location) {
        WindowsManager.location = location;
    }

    public static void setWidth(int width) {
        WindowsManager.width = width;
    }

    public static Point setWindowsLocation() {
        int margin = 10;
        return new Point((int) location.getX() + width + margin, (int) location.getY());
    }

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
