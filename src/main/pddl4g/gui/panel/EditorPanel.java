package main.pddl4g.gui.panel;

import main.pddl4g.context.pddl.PDDLContext;
import main.pddl4g.gui.Editor;
import main.pddl4g.gui.tools.AutoComplete;
import main.pddl4g.gui.tools.FileTools;
import main.pddl4g.gui.tools.TextLineNumber;
import main.pddl4g.gui.tools.UndoRedo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class EditorPanel extends JPanel {

    private Editor parent;
    private JTextArea textArea;
    private PDDLContext pddl;
    private AutoComplete autocomplete;

    private boolean hasListener = false;

    @Override
    public Editor getParent() {
        return parent;
    }

    public JTextArea getEditor() {
        return textArea;
    }

    public EditorPanel(Editor parent) {
        this.parent = parent;
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
        JScrollPane scrollTextArea = new JScrollPane(textArea);
        TextLineNumber tln = new TextLineNumber(textArea);
        scrollTextArea.setRowHeaderView(tln);

        add(scrollTextArea, BorderLayout.CENTER);

        displayFileContent(parent.getFileToEdit());
        UndoRedo.addUndoRedo(textArea);
    }

    public void displayFileContent(File file) {
        textArea.append(FileTools.readFileToString(file));
        this.enableAutoComplete(file);
    }

    public void enableAutoComplete(File file) {
        if (hasListener) {
            textArea.getDocument().removeDocumentListener(autocomplete);
            hasListener = false;
        }

        ArrayList<String> arrayList;
        String extension = pddl.getExtensionFile();

        if (file.getName().endsWith(extension)) {
            arrayList = pddl.setKeywords(pddl.getPDDLKeywords());
            autocomplete = new AutoComplete(this, arrayList);
            textArea.getDocument().addDocumentListener(autocomplete);
            hasListener = true;
        }
    }
}