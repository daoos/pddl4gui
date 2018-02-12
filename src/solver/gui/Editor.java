package solver.gui;

import solver.gui.panel.EditorMenuToolBar;
import solver.gui.panel.EditorPanel;
import solver.gui.tools.WindowsManager;

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

    public Editor(JButton parent, File file) {
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
                parent.setEnabled(true);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                parent.setEnabled(true);
            }
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
