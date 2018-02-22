package main.pddl4g.gui;

import main.pddl4g.gui.panel.EditorMenuToolBar;
import main.pddl4g.gui.tools.WindowsManager;
import main.pddl4g.gui.panel.EditorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Editor extends JFrame {

    private File fileToEdit;

    public File getFileToEdit() {
        return fileToEdit;
    }

    public Editor(Solver parent, File file, int type) {
        fileToEdit = file;
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        int width = 800;
        int height = 600;

        setSize(width, height);
        setTitle("Editor | " + fileToEdit.getName());

        EditorPanel editorPanel = new EditorPanel(this);

        EditorMenuToolBar editorMenuToolBar = new EditorMenuToolBar(editorPanel);

        container.add(editorMenuToolBar, BorderLayout.NORTH);

        container.add(editorPanel, BorderLayout.CENTER);

        setLocation(WindowsManager.setWindowsLocation());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if(type == 0) {
                    parent.getSetupPanel().enableDomainButton(true);
                } else if (type == 1){
                    parent.getSetupPanel().enablePBButton(true);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(type == 0) {
                    parent.getSetupPanel().enableDomainButton(true);
                } else if (type == 1){
                    parent.getSetupPanel().enablePBButton(true);
                }
            }
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
