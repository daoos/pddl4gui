package pddl4gui.gui;

import pddl4gui.gui.panel.EditorMenuToolBar;
import pddl4gui.gui.panel.EditorPanel;
import pddl4gui.gui.tools.WindowsManager;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;
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
        final Container container = getContentPane();
        container.setLayout(new BorderLayout());

        final int width = 800;
        final int height = 600;

        setSize(width, height);
        setTitle("Editor | " + fileToEdit.getName());

        final EditorPanel editorPanel = new EditorPanel(this);

        final EditorMenuToolBar editorMenuToolBar = new EditorMenuToolBar(editorPanel);

        container.add(editorMenuToolBar, BorderLayout.NORTH);

        container.add(editorPanel, BorderLayout.CENTER);

        setLocation(WindowsManager.setWindowsLocation());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (type == 0) {
                    parent.getSetupPanel().enableDomainButton(true);
                } else if (type == 1) {
                    parent.getSetupPanel().enablePBButton(true);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (type == 0) {
                    parent.getSetupPanel().enableDomainButton(true);
                } else if (type == 1) {
                    parent.getSetupPanel().enablePBButton(true);
                }
            }
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
