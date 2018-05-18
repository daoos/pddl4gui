package pddl4gui.gui.panel;

import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Find;
import pddl4gui.gui.tools.Icons;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;

public class EditorMenuToolBar extends JToolBar {

    final private JComboBox<Integer> fontSize;
    private boolean isInvertedColor = false;

    public EditorMenuToolBar(EditorPanel parent) {

        fontSize = new JComboBox<>();
        for (int i = 5; i <= 100; i++) {
            fontSize.addItem(i);
        }
        fontSize.setMaximumSize(new Dimension(70, 30));
        add(fontSize);
        fontSize.addActionListener((ActionEvent ev) -> {
            final String sizeValue = String.valueOf(fontSize.getSelectedItem());
            final int sizeOfFont = Integer.parseInt(sizeValue);
            final String fontFamily = parent.getEditor().getFont().getFamily();

            final Font font1 = new Font(fontFamily, Font.PLAIN, sizeOfFont);
            parent.getEditor().setFont(font1);
        });
        addSeparator();

        final JButton saveButton = new JButton(Icons.getSaveIcon());
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(e -> {
            final File tempFile = parent.getParent().getFileToEdit();

            if (tempFile != null) {
                FileTools.writeInFile(tempFile, parent.getEditor().getText());
                parent.enableAutoComplete(tempFile);
            }
        });
        this.add(saveButton);
        addSeparator();

        final JButton wordWrapButton = new JButton(Icons.getWordwrapIcon());
        wordWrapButton.setToolTipText("Word wrap");
        wordWrapButton.addActionListener(e -> {
            if (!parent.getEditor().getLineWrap()) {
                parent.getEditor().setLineWrap(true);
            } else {
                parent.getEditor().setLineWrap(false);
            }
        });
        this.add(wordWrapButton);

        final JButton quickButton = new JButton(Icons.getSearchIcon());
        quickButton.setToolTipText("Quick Search");
        quickButton.addActionListener(e -> new Find(parent.getEditor()));
        this.add(quickButton);

        final JButton invertColorButton = new JButton(Icons.getInvertIcon());
        invertColorButton.setToolTipText("Invert Color");
        invertColorButton.addActionListener(e -> {
            if (isInvertedColor) {
                isInvertedColor = false;
                parent.getEditor().setBackground(Color.WHITE);
                parent.getEditor().setForeground(Color.BLACK);
            } else {
                isInvertedColor = true;
                parent.getEditor().setBackground(Color.BLACK);
                parent.getEditor().setForeground(Color.WHITE);
            }
        });
        add(invertColorButton);
        addSeparator();

        final JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> parent.getParent().dispose());
        add(exitButton);
    }
}
