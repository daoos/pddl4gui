package pddl4gui.gui.tools;

import javax.swing.*;
import java.awt.*;

public class DrawCircle extends JPanel {

    final private int x;
    final private int y;
    final private int diam;

    private Color color = Color.RED;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public DrawCircle(int x, int y, int diam) {
        this.x = x;
        this.y = y;
        this.diam = diam;
        repaint();
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diam, diam);
    }
}
