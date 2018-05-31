package pddl4gui.gui;

import pddl4gui.gui.tools.MessageConsole;
import pddl4gui.gui.tools.WindowsManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;

public class Console extends JFrame {

    public Console() {
        setSize(400, 200);
        setTitle(WindowsManager.NAME + " | Console");
        setLayout(new BorderLayout());

        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(new BorderLayout());

        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jTextArea);

        MessageConsole mc = new MessageConsole(jTextArea);
        mc.redirectOut();
        //mc.redirectOut(null, System.out);
        mc.redirectErr(Color.RED, null);
        mc.setMessageLines(50);

        consolePanel.add(scrollPane, BorderLayout.CENTER);
        add(consolePanel, BorderLayout.CENTER);

        setLocation(WindowsManager.setWindowsLocationHeight());
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
