package pddl4gui.gui.tools;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Popup extends JFrame {

    public Popup(final ImageIcon imagIcon) {
        setUndecorated(true);

        final MouseListener ml = new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                dispose();
            }
        };

        final JPanel panel = new JPanel();
        final JLabel lbl = new JLabel(imagIcon);
        panel.addMouseListener(ml);
        panel.add(lbl);
        getContentPane().add(panel);

        setSize(imagIcon.getIconWidth(), imagIcon.getIconHeight());
        setLocation(WindowsManager.setWindowsLocation());
        setVisible(true);
    }
}
