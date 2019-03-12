package pddl4gui.gui;

import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class implements the VAL class of <code>PDDL4GUI</code>.
 * This JFrame displays VAL result for a given token and propose to save the result.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class VAL extends JFrame {

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
        VAL.frame = frame;
    }

    /**
     * The path to VAL.
     */
    private static final String valPath = "resources" + File.separator + "apps" + File.separator + "validate";

    /**
     * The JTextArea containing VAL result.
     */
    private final JTextArea textArea;

    /**
     * The PDDL domain file.
     */
    private File domainFile;

    /**
     * The PDDL problem file.
     */
    private File pbFile;

    /**
     * The plan file.
     */
    private File planFile;

    /**
     * If the plan is valide according to VAL result.
     */
    private boolean isValide = false;

    /**
     * Creates a new VAL JFrame.
     *
     * @param domain   the domain to validate.
     * @param problem  the problem to validate.
     * @param result   the result to validate.
     * @param isSolved the status of the computation.
     */
    public VAL(final File domain, final File problem, final String result, final boolean isSolved) {
        final JButton exitButton;
        final JButton generateTexButton;
        final JButton saveButton;

        setTitle("VAL | " + FileTools.removeExtension(domain.getName()) + " "
                + FileTools.removeExtension(problem.getName()));
        setLayout(null);

        final int width = 540;
        final int height = 460;

        setSize(width, height);

        textArea = new JTextArea();
        textArea.setEditable(false);
        final JScrollPane scrollTextPane = new JScrollPane(textArea);
        scrollTextPane.setBounds(60, 10, 460, 400);
        add(scrollTextPane);

        generateTexButton = new JButton(Icons.getGenerateIcon());
        generateTexButton.setBounds(10, 10, 40, 40);
        generateTexButton.setToolTipText("Generate TeX report");
        generateTexButton.addActionListener(e -> {
            if (isValide) {
                String target;
                if (TriggerAction.isWindows()) {
                    target = valPath + ".exe -l " + domainFile.getAbsolutePath()
                            + " " + pbFile.getAbsolutePath() + " " + planFile.getAbsolutePath();
                } else {
                    target = valPath + " -l " + domainFile.getAbsolutePath()
                            + " " + pbFile.getAbsolutePath() + " " + planFile.getAbsolutePath();
                }
                StringBuilder output = this.getValResult(target);
                File tempFile = FileTools.saveFile(this, 2);
                if (FileTools.checkFile(tempFile)) {
                    FileTools.writeInFile(tempFile, output.toString());
                }
            }
        });
        generateTexButton.setEnabled(true);
        add(generateTexButton);

        saveButton = new JButton(Icons.getSaveTxtIcon());
        saveButton.setBounds(10, 60, 40, 40);
        saveButton.setToolTipText("Save report");
        saveButton.addActionListener(e -> {
            if (isValide) {
                File tempFile = FileTools.saveFile(this, 1);
                if (FileTools.checkFile(tempFile)) {
                    FileTools.writeInFile(tempFile, textArea.getText());
                }
            }
        });
        saveButton.setEnabled(true);
        add(saveButton);

        exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(10, 110, 40, 40);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> this.dispose());
        add(exitButton);

        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder(
                "VAL input"));

        this.domainFile = domain;
        this.pbFile = problem;
        try {
            planFile = File.createTempFile("result", ".txt");
            try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(planFile), StandardCharsets.UTF_8))) {
                writer.write(result);
            }

            if (FileTools.checkFile(domainFile) && FileTools.checkFile(pbFile) && isSolved) {
                try {
                    String target;
                    if (TriggerAction.isWindows()) {
                        target = valPath + ".exe -v " + domainFile.getAbsolutePath()
                                + " " + pbFile.getAbsolutePath() + " " + planFile.getAbsolutePath();
                    } else {
                        target = valPath + " -v " + domainFile.getAbsolutePath()
                                + " " + pbFile.getAbsolutePath() + " " + planFile.getAbsolutePath();
                    }
                    StringBuilder output = this.getValResult(target);

                    textArea.setText(output.toString());
                    isValide = true;

                } catch (Throwable t) {
                    System.err.println(t.getMessage());
                }
            } else {
                textArea.setText("One (or many) of the selected file are null or incorrect ! ");
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }

        setLocation(WindowsManager.setWindowsLocationWidth());

        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new OnClose());
    }

    /**
     * Returns a StringBuilder containing the VAL result.
     *
     * @param target the command line to execute.
     * @return a StringBuilder containing the VAL result.
     */
    private StringBuilder getValResult(String target) {
        final StringBuilder output = new StringBuilder();
        try {
            final Runtime rt = Runtime.getRuntime();
            final Process proc = rt.exec(target);
            proc.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream(),
                    StandardCharsets.UTF_8));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            } catch (IOException ioex) {
                System.err.println(ioex.getMessage());
            } finally {
                reader.close();
            }
            return output;
        } catch (IOException | InterruptedException t) {
            System.err.println(t.getMessage());
            return output.append(" Error during validation !");
        }
    }

    /**
     * The method to call on window closing.
     */
    static class OnClose extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            VAL.frame.dispose();
        }
    }
}