package pddl4gui.gui.tools;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.Serializable;

/**
 * This class implements the Find class of <code>PDDL4GUI</code>.
 * This JFrame displays a box with to search, replace a word in a JTextArea.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Find extends JFrame implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The start index used to search a word.
     */
    private int startIndex = 0;

    /**
     * The start index of the text selected in the JTextArea.
     */
    private int select_start = -1;

    /**
     * The JTextField used to find the occurrences of a word.
     */
    final private JTextField textF, textR;

    /**
     * The JTextArea linked to this find box.
     */
    final private JTextArea txt;

    /**
     * The HighlightText to highlight the word found.
     */
    private HighlightText highlightText = new HighlightText(Color.RED);

    /**
     * Create a new find box for a JTextArea.
     *
     * @param text the JTextArea linked to this find box.
     */
    public Find(JTextArea text) {
        this.txt = text;

        final JLabel lab1 = new JLabel("Find:");
        final JLabel lab2 = new JLabel("Replace:");
        final JButton findBtn = new JButton("Find");
        final JButton findNext = new JButton("Find Next");
        final JButton replace = new JButton("Replace");
        final JButton replaceAll = new JButton("Replace All");
        final JButton cancel = new JButton("Cancel");

        textF = new JTextField(30);
        textR = new JTextField(30);

        setLayout(null);

        final int labWidth = 80;
        final int labHeight = 20;

        lab1.setBounds(10, 10, labWidth, labHeight);
        add(lab1);
        textF.setBounds(10 + labWidth, 10, 120, 20);
        add(textF);
        lab2.setBounds(10, 10 + labHeight + 10, labWidth, labHeight);
        add(lab2);
        textR.setBounds(10 + labWidth, 10 + labHeight + 10, 120, 20);
        add(textR);

        findBtn.setBounds(225, 6, 115, 20);
        add(findBtn);
        findBtn.addActionListener(e -> find());

        findNext.setBounds(225, 28, 115, 20);
        add(findNext);
        findNext.addActionListener(e -> findNext());

        replace.setBounds(225, 50, 115, 20);
        add(replace);
        replace.addActionListener(e -> replace());

        replaceAll.setBounds(225, 72, 115, 20);
        add(replaceAll);
        replaceAll.addActionListener(e -> replaceAll());

        cancel.setBounds(225, 94, 115, 20);
        add(cancel);
        cancel.addActionListener(e -> this.dispose());

        final int width = 360;
        final int height = 160;

        setSize(width, height);

        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(center.x - width / 2, center.y - height / 2);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * Finds an occurrence of a word in the text.
     */
    private void find() {
        select_start = txt.getText().toLowerCase().indexOf(textF.getText().toLowerCase());
        if (select_start == -1) {
            startIndex = 0;
            JOptionPane.showMessageDialog(null, "Could not find \"" + textF.getText() + "\"!");
            return;
        }
        if (select_start == txt.getText().toLowerCase().lastIndexOf(textF.getText().toLowerCase())) {
            startIndex = 0;
        }
        int select_end = select_start + textF.getText().length();
        txt.select(select_start, select_end);
        highlightText.hightLightWord(txt, select_start, select_end);
    }

    /**
     * Finds the next occurrence of a word in the text.
     */
    private void findNext() {
        String selection = txt.getSelectedText();
        if (selection == null) {
            selection = textF.getText();
            if (selection == null) {
                selection = JOptionPane.showInputDialog("Find:");
                textF.setText(selection);
            }
        }
        try {
            int select_start = txt.getText().toLowerCase().indexOf(selection.toLowerCase(), startIndex);
            int select_end = select_start + selection.length();
            txt.select(select_start, select_end);
            highlightText.hightLightWord(txt, select_start, select_end);
            startIndex = select_end + 1;

            if (select_start == txt.getText().toLowerCase().lastIndexOf(selection.toLowerCase())) {
                startIndex = 0;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Replaces an occurrence of a word in the text.
     */
    private void replace() {
        try {
            find();
            if (select_start != -1)
                txt.replaceSelection(textR.getText());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Replaces all occurrences of a word in the text.
     */
    private void replaceAll() {
        txt.setText(txt.getText().toLowerCase().replaceAll(textF.getText().toLowerCase(), textR.getText()));
    }
}
