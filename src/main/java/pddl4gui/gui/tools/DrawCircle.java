package pddl4gui.gui.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.JPanel;

/**
 * This class implements the DrawCircle class of <code>PDDL4GUI</code>.
 * This JPanel paints a circle.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class DrawCircle extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The center coordinates of the circle.
     */
    private final int xCoord;
    private final int yCoord;

    /**
     * The diameter of the circle.
     */
    private final int diam;

    /**
     * The default color of the circle.
     */
    private Color color = Color.RED;

    /**
     * Sets the color of the circle.
     *
     * @param color the new color of the circle.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Creates a new circle.
     *
     * @param xarg    the x coordinate of the center.
     * @param yarg    the y coordinate of the center.
     * @param diam the diameter of the circle.
     */
    public DrawCircle(int xarg, int yarg, int diam) {
        this.xCoord = xarg;
        this.yCoord = yarg;
        this.diam = diam;
        repaint();
    }

    /**
     * Paints the circle.
     *
     * @param g the graphic component to paint.
     */
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(this.xCoord, this.yCoord, diam, diam);
    }
}
