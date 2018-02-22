package main.pddl4g.gui.panel;

import main.pddl4g.gui.tools.FileTools;
import main.pddl4g.gui.tools.Find;
import main.pddl4g.gui.tools.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EditorMenuToolBar extends JToolBar {

    private JComboBox fontSize, fontType;
    private boolean isInvertedColor = false;

    public EditorMenuToolBar(EditorPanel parent) {
        fontType = new JComboBox();
        for (String item : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            fontType.addItem(item);
        }

        fontType.setMaximumSize(new Dimension(170, 30));
        add(fontType);
        addSeparator();
        fontType.addActionListener(e -> {
            String p = fontType.getSelectedItem().toString();
            int s = parent.getEditor().getFont().getSize();
            parent.getEditor().setFont(new Font(p, Font.PLAIN, s));
        });

        fontSize = new JComboBox();
        for (int i = 5; i <= 100; i++) {
            fontSize.addItem(i);
        }
        fontSize.setMaximumSize(new Dimension(70, 30));
        add(fontSize);
        fontSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String sizeValue = fontSize.getSelectedItem().toString();
                int sizeOfFont = Integer.parseInt(sizeValue);
                String fontFamily = parent.getEditor().getFont().getFamily();

                Font font1 = new Font(fontFamily, Font.PLAIN, sizeOfFont);
                parent.getEditor().setFont(font1);
            }
        });
        addSeparator();

        JButton saveButton = new JButton(Icons.getSaveIcon());
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(e -> {
            File tempFile = parent.getParent().getFileToEdit();

            if (tempFile != null) {
                FileTools.writeInFile(tempFile, parent.getEditor().getText());
                parent.enableAutoComplete(tempFile);
            }
        });
        this.add(saveButton);
        addSeparator();

        JButton wordWrapButton = new JButton(Icons.getWordwrapIcon());
        wordWrapButton.setToolTipText("Word wrap");
        wordWrapButton.addActionListener(e -> {
            if (!parent.getEditor().getLineWrap()) {
                parent.getEditor().setLineWrap(true);
            } else {
                parent.getEditor().setLineWrap(false);
            }
        });
        this.add(wordWrapButton);

        JButton quickButton = new JButton(Icons.getSearchIcon());
        quickButton.setToolTipText("Quick Search");
        quickButton.addActionListener(e -> {
            new Find(parent.getEditor());
        });
        this.add(quickButton);

        JButton invertColorButton = new JButton(Icons.getInvertIcon());
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

        JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> {
            parent.getParent().dispose();
        });
        add(exitButton);
    }
}
