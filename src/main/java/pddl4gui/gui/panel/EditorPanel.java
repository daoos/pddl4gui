package pddl4gui.gui.panel;

import pddl4gui.gui.Editor;
import pddl4gui.gui.tools.AutoComplete;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.TextLineNumber;
import pddl4gui.gui.tools.UndoRedo;
import pddl4gui.pddl.PDDLContext;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class implements the EditorPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays all the components of the Editor.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class EditorPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JTextArea to display the content of the file.
     */
    final private JTextArea textArea;

    /**
     * The PDDLContext for the PDDL language.
     */
    final private PDDLContext pddl;

    /**
     * The AutoComplete object to enable auto complete feature.
     */
    private AutoComplete autocomplete;

    /**
     * If the JTextArea has a listener on it.
     */
    private boolean hasListener = false;

    /**
     * Gets the JTextArea of this EditorPanel.
     *
     * @return the JTextArea of this EditorPanel.
     */
    public JTextArea getEditorTextArea() {
        return textArea;
    }

    /**
     * Creates a new EditorPanel for a specific Editor.
     *
     * @param parent the Editor associated to the EditorPanel.
     */
    public EditorPanel(Editor parent) {
        this.pddl = new PDDLContext();
        setLayout(new BorderLayout());

        textArea = new JTextArea("", 0, 0);
        textArea.setFont(new Font("Century Gothic", Font.BOLD, 12));
        textArea.setTabSize(2);
        textArea.setLineWrap(true);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                pddl.getHighlighter().highLight(textArea, pddl.getPDDLKeywords());
            }
        });
        final JScrollPane scrollTextArea = new JScrollPane(textArea);
        final TextLineNumber tln = new TextLineNumber(textArea);
        scrollTextArea.setRowHeaderView(tln);

        add(scrollTextArea, BorderLayout.CENTER);

        displayFileContent(parent.getFileToEdit());
        UndoRedo.addUndoRedo(textArea);
    }

    /**
     * Displays the content of a file.
     *
     * @param file the file to display.
     */
    private void displayFileContent(File file) {
        textArea.append(FileTools.readFileToString(file));
        this.enableAutoComplete(file);
    }

    /**
     * Enables auto complete feature on a file.
     *
     * @param file the file.
     */
    void enableAutoComplete(File file) {
        if (hasListener) {
            textArea.getDocument().removeDocumentListener(autocomplete);
            hasListener = false;
        }

        final String extension = pddl.getExtensionFile();

        if (file.getName().endsWith(extension)) {
            final ArrayList<String> arrayList = pddl.addKeywords(pddl.getPDDLKeywords());
            autocomplete = new AutoComplete(this, arrayList);
            textArea.getDocument().addDocumentListener(autocomplete);
            hasListener = true;
        }
    }
}