package pddl4gui.gui;

import pddl4gui.gui.panel.EditorMenuToolBar;
import pddl4gui.gui.panel.EditorPanel;
import pddl4gui.gui.tools.WindowsManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.Serializable;
import javax.swing.JFrame;

/**
 * This class implements the Editor class of <code>PDDL4GUI</code>.
 * This JFrame displays an EditorPanel for a specific PDDL file (the file to edit).
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Editor extends JFrame implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The file to edit in the Editor.
     */
    private File fileToEdit;

    /**
     * The type of the file to edit.
     */
    private int typeOfFile;

    /**
     * Gets the file to edit in the Editor.
     *
     * @return the file to edit in the Editor.
     */
    public File getFileToEdit() {
        return fileToEdit;
    }

    /**
     * Sets the file to edit in the Editor.
     * @param fileToEdit the file to edit.
     */
    public void setFileToEdit(File fileToEdit) {
        this.fileToEdit = fileToEdit;
    }

    /**
     * Gets the type of the file to edit in the Editor (0: domain ; 1: problem).
     *
     * @return the type of the file to edit in the Editor.
     */
    public int getTypeOfFile() {
        return typeOfFile;
    }

    /**
     * Creates a new Editor.
     *
     * @param file    the file to edit.
     * @param type    the type of the file (0: domain ; 1: problem).
     * @param newFile if its new file or not.
     */
    public Editor(File file, int type, boolean newFile) {
        typeOfFile = type;

        final Container container = getContentPane();
        container.setLayout(new BorderLayout());

        final int width = 800;
        final int height = 600;

        setSize(width, height);

        if (newFile) {
            setTitle("Editor | New file");
        } else {
            fileToEdit = file;
            setTitle("Editor | " + fileToEdit.getName());
        }

        final EditorPanel editorPanel = new EditorPanel(this);

        final EditorMenuToolBar editorMenuToolBar = new EditorMenuToolBar(this, editorPanel);

        container.add(editorMenuToolBar, BorderLayout.NORTH);

        container.add(editorPanel, BorderLayout.CENTER);

        setLocation(WindowsManager.setWindowsLocationWidth());
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
