package pddl4gui.gui.panel;

import pddl4gui.gui.tools.CustomOutputStream;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;

import java.io.File;
import java.io.PrintStream;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class implements the LogPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays log and standard output in a JTextArea.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class LogPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JTextArea containing solving result of selected token.
     */
    private final JTextArea logArea;

    /**
     * Creates a new LogPanel which displays standard output.
     */
    public LogPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Log output"));

        logArea = new JTextArea();
        PrintStream printStream = new PrintStream(new CustomOutputStream(logArea));

        // keeps reference of standard output stream
        //standardOut = System.out;

        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);

        logArea.setEditable(false);
        final JScrollPane scrollTextPane = new JScrollPane(logArea);
        scrollTextPane.setBounds(15, 25, 1090, 130);
        add(scrollTextPane);

        final JButton saveButton = new JButton(Icons.getSaveTxtIcon());
        saveButton.setBounds(1120, 25, 45, 55);
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
        resetButton.setBounds(1120, 100, 45, 55);
        resetButton.setToolTipText("Clear the logs");
        resetButton.setEnabled(true);
        resetButton.addActionListener(e -> logArea.setText(""));
        add(resetButton);
    }
}
