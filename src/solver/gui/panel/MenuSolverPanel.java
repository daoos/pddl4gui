package solver.gui.panel;

import solver.gui.Solver;
import solver.gui.VAL;
import solver.gui.tools.FileTools;
import solver.gui.tools.Icons;

import javax.swing.*;
import java.io.File;

public class MenuSolverPanel extends JPanel {

    private JButton valButton, saveTxtButton, saveJsonButton, resetButton;

    public JButton getValButton() {
        return valButton;
    }

    public JButton getSaveTxtButton() {
        return saveTxtButton;
    }

    public JButton getSaveJsonButton() {
        return saveJsonButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public MenuSolverPanel(Solver parent) {
        valButton = new JButton(Icons.getValidateIcon());
        valButton.setBounds(10, 10, 40, 40);
        valButton.setToolTipText("VAL on solution");
        valButton.setEnabled(false);
        valButton.addActionListener(e -> {
            valButton.setEnabled(false);
            new VAL(parent, parent.getToken());
        });
        add(valButton);

        saveTxtButton = new JButton(Icons.getSaveIcon());
        saveTxtButton.setBounds(50, 10, 40, 40);
        saveTxtButton.setToolTipText("Save solution (txt)");
        saveTxtButton.addActionListener(e -> {
            File tempFile = FileTools.saveFile(this, 1);
            if (!FileTools.checkFile(tempFile)){
                FileTools.writeInFile(tempFile, parent.getToken().getResult().getSolutionString());
            }
        });
        saveTxtButton.setEnabled(false);
        add(saveTxtButton);

        saveJsonButton = new JButton(Icons.getSaveIcon());
        saveJsonButton.setBounds(110, 10, 40, 40);
        saveJsonButton.setToolTipText("Save solution (json)");
        saveJsonButton.addActionListener(e -> {
            File tempFile = FileTools.saveFile(this, 5);
            if (!FileTools.checkFile(tempFile)) {
                FileTools.writeInFile(tempFile, parent.getToken().getResult().getSolutionJSON());
            }
        });
        saveJsonButton.setEnabled(false);
        add(saveJsonButton);

        resetButton = new JButton(Icons.getResetIcon());
        resetButton.setBounds(160, 10, 40, 40);
        resetButton.setToolTipText("Reset the solver");
        resetButton.setEnabled(false);
        resetButton.addActionListener(e -> {
            new Thread("") {
                public void run() {
                    new Solver();
                }
            }.start();
            parent.dispose();
        });
        add(resetButton);

        JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(210, 10, 40, 40);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        add(exitButton);
    }
}
