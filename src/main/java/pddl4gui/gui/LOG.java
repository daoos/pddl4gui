package pddl4gui.gui;

import pddl4gui.gui.tools.CustomOutputStream;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.WindowsManager;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class implements the LOG class of <code>PDDL4GUI</code>.
 * This JPanel displays log and standard output in a JTextArea.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class LOG extends JFrame {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The reference to the frame.
     */
    private static JFrame frame;

    /**
     * Sets the reference to the JFrame.
     *
     * @param frame the JFrame.
     */
    public static void setFrame(final JFrame frame) {
        LOG.frame = frame;
    }

    /**
     * The reference to the parent component.
     */
    private static Component component;

    /**
     * Sets the reference to the Component.
     *
     * @param component the Component.
     */
    public static void setComponent(final Component component) {
        LOG.component = component;
    }

    /**
     * The JTextArea containing solving result of selected token.
     */
    private final JTextArea logArea;

    /**
     * The reference to the standard output stream.
     */
    private static final PrintStream standardOut = System.out;

    /**
     * Creates a new LOG which displays standard output.
     */
    public LOG() {
        setLayout(null);
        setSize(WindowsManager.getWidth(), 200);
        setTitle(WindowsManager.NAME + " | Log");
        setLocation(WindowsManager.setWindowsLocationWidth());

        logArea = new JTextArea();
        logArea.setEditable(false);
        final JScrollPane scrollTextPane = new JScrollPane(logArea);
        scrollTextPane.setBounds(10, 10, WindowsManager.getWidth() - 70, 140);
        add(scrollTextPane);

        final JButton saveButton = new JButton(Icons.getSaveTxtIcon());
        saveButton.setBounds((WindowsManager.getWidth() - 70) + 20, 10, 40, 40);
        saveButton.setToolTipText("Save logs");
        saveButton.addActionListener(e -> {
            File tempFile = FileTools.saveFile(this, 1);
            if (FileTools.checkFile(tempFile)) {
                FileTools.writeInFile(tempFile, logArea.getText());
            }

        });
        saveButton.setEnabled(true);
        add(saveButton);

        final JButton resetButton = new JButton(Icons.getResetIcon());
        resetButton.setBounds((WindowsManager.getWidth() - 70) + 20, 60, 40, 40);
        resetButton.setToolTipText("Clear the logs");
        resetButton.setEnabled(true);
        resetButton.addActionListener(e -> logArea.setText(""));
        add(resetButton);

        final JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds((WindowsManager.getWidth() - 70) + 20, 110, 40, 40);
        exitButton.setToolTipText("Exit LOG");
        exitButton.setEnabled(true);
        exitButton.addActionListener(e -> {
            System.setOut(standardOut);
            System.setErr(standardOut);
            LOG.component.setEnabled(true);
            this.dispose();
        });
        add(exitButton);

        try {
            final PrintStream printStream = new PrintStream(new CustomOutputStream(logArea), false, "UTF-8");
            System.setOut(printStream);
            System.setErr(printStream);
        } catch (UnsupportedEncodingException unex) {
            System.err.println(unex.getMessage());
        }

        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new OnClose());
    }

    /**
     * The method to call on window closing.
     */
    static class OnClose extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.setOut(standardOut);
            System.setErr(standardOut);
            LOG.component.setEnabled(true);
            LOG.frame.dispose();
        }
    }
}
