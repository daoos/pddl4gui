package pddl4gui.gui;

import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class VAL extends JFrame {

    final private JTextArea textArea;
    private File domainFile, pbFile, planFile;

    private boolean isValide = false;

    public VAL(Token token) {
        final JButton exitButton, generateTexButton, saveButton;

        setTitle("VAL | " + token.toString());
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
                String target = "./resources/apps/validate -l " + domainFile.getAbsolutePath()
                        + " " + pbFile.getAbsolutePath() + " " + planFile.getAbsolutePath();
                StringBuilder output = VAL.execVal(target);
                File tempFile = FileTools.saveFile(this, 2);
                if (FileTools.checkFile(tempFile))
                    FileTools.writeInFile(tempFile, output.toString());
            }
        });
        generateTexButton.setEnabled(true);
        add(generateTexButton);

        saveButton = new JButton(Icons.getSaveIcon());
        saveButton.setBounds(10, 60, 40, 40);
        saveButton.setToolTipText("Save report");
        saveButton.addActionListener(e -> {
            if (isValide) {
                File tempFile = FileTools.saveFile(this, 1);
                if (FileTools.checkFile(tempFile))
                    FileTools.writeInFile(tempFile, textArea.getText());
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

        domainFile = token.getDomainFile();
        pbFile = token.getProblemFile();
        try {
            planFile = File.createTempFile("result", ".txt");
            try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(planFile), "UTF-8"))) {
                writer.write(token.getResult().getSolutionString());
            }

            if (FileTools.checkFile(domainFile) && FileTools.checkFile(pbFile) && token.isSolved()) {
                try {
                    String target = "./resources/apps/validate -v " + domainFile.getAbsolutePath()
                            + " " + pbFile.getAbsolutePath() + " " + planFile.getAbsolutePath();
                    StringBuilder output = VAL.execVal(target);

                    textArea.setText(output.toString());
                    isValide = true;

                } catch (Throwable t) {
                    t.printStackTrace();
                }
            } else {
                textArea.setText("One (or many) of the selected file are null or incorrect ! ");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        setLocation(WindowsManager.setWindowsLocation());

        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private static StringBuilder execVal(String target) {
        final StringBuilder output = new StringBuilder();
        try {
            final Runtime rt = Runtime.getRuntime();
            final Process proc = rt.exec(target);
            proc.waitFor();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            return output;
        } catch (Throwable t) {
            t.printStackTrace();
            return output.append(" Error during validation !");
        }
    }
}