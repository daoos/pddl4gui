package pddl4gui.gui.panel;

import pddl4gui.gui.Editor;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Find;
import pddl4gui.gui.tools.Icons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

/**
 * This class implements the EditorMenuToolBar class of <code>PDDL4GUI</code>.
 * This JToolBar displays buttons and options for the EditorPanel.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class EditorMenuToolBar extends JToolBar implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JComboBox for font size.
     */
    private final JComboBox<Integer> fontSize;

    /**
     * If the color is inverted in the EditorPanel.
     */
    private boolean isInvertedColor = false;

    /**
     * Creates a new EditorMenuToolBar for the EditorPanel.
     *
     * @param editor the Editor associated.
     * @param parent the EditorPanel associated.
     */
    public EditorMenuToolBar(Editor editor, EditorPanel parent) {

        fontSize = new JComboBox<>();
        for (int i = 5; i <= 100; i++) {
            fontSize.addItem(i);
        }
        fontSize.setMaximumSize(new Dimension(70, 30));
        add(fontSize);
        fontSize.addActionListener((ActionEvent ev) -> {
            final String sizeValue = String.valueOf(fontSize.getSelectedItem());
            final int sizeOfFont = Integer.parseInt(sizeValue);
            final String fontFamily = parent.getEditorTextArea().getFont().getFamily();

            final Font font1 = new Font(fontFamily, Font.PLAIN, sizeOfFont);
            parent.getEditorTextArea().setFont(font1);
        });
        addSeparator();

        final JButton saveButton = new JButton(Icons.getSaveIcon());
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(e -> {
            final File tempFile = editor.getFileToEdit();

            if (tempFile != null) {
                FileTools.writeInFile(tempFile, parent.getEditorTextArea().getText());
                parent.enableAutoComplete(tempFile);
            }
        });
        this.add(saveButton);
        addSeparator();

        final JButton wordWrapButton = new JButton(Icons.getWordwrapIcon());
        wordWrapButton.setToolTipText("Word wrap");
        wordWrapButton.addActionListener(e -> {
            if (!parent.getEditorTextArea().getLineWrap()) {
                parent.getEditorTextArea().setLineWrap(true);
            } else {
                parent.getEditorTextArea().setLineWrap(false);
            }
        });
        this.add(wordWrapButton);

        final JButton quickButton = new JButton(Icons.getSearchIcon());
        quickButton.setToolTipText("Quick Search");
        quickButton.addActionListener(e -> new Find(parent.getEditorTextArea()));
        this.add(quickButton);

        final JButton invertColorButton = new JButton(Icons.getInvertIcon());
        invertColorButton.setToolTipText("Invert Color");
        invertColorButton.addActionListener(e -> {
            if (isInvertedColor) {
                isInvertedColor = false;
                parent.getEditorTextArea().setBackground(Color.WHITE);
                parent.getEditorTextArea().setForeground(Color.BLACK);
            } else {
                isInvertedColor = true;
                parent.getEditorTextArea().setBackground(Color.BLACK);
                parent.getEditorTextArea().setForeground(Color.WHITE);
            }
        });
        add(invertColorButton);
        addSeparator();

        final JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> editor.dispose());
        add(exitButton);
    }
}
