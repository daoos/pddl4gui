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
import java.io.Serializable;

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
     * Gets the file to edit in the Editor.
     *
     * @return the file to edit in the Editor.
     */
    public File getFileToEdit() {
        return fileToEdit;
    }

    /**
     * Creates a new Editor.
     *
     * @param parent the solver associated to the Editor.
     * @param file the file to edit.
     * @param type the type of the file (0: domain ; 1: problem).
     */
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

        setLocation(WindowsManager.setWindowsLocationWidth());
        addWindowListener(new WindowAdapter() {

            /**
             * Enable button in the Solver JFrame.
             *
             * @param e the window event.
             */
            @Override
            public void windowClosed(WindowEvent e) {
                if (type == 0) {
                    TriggerAction.setSetupPanelDomainButton(true);
                } else if (type == 1) {
                    TriggerAction.setSetupPanelProblemButton(true);
                }
            }

            /**
             * Enable button in the Solver JFrame.
             *
             * @param e the window event.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if (type == 0) {
                    TriggerAction.setSetupPanelDomainButton(true);
                } else if (type == 1) {
                    TriggerAction.setSetupPanelProblemButton(true);
                }
            }
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
