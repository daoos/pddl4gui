package pddl4gui.gui.tools;

import javax.swing.*;
import java.awt.*;

public class Find extends JFrame {

    private int startIndex = 0;
    private int select_start = -1;
    private JLabel lab1, lab2;
    private JTextField textF, textR;
    private JButton findBtn, findNext, replace, replaceAll, cancel;
    private JTextArea txt;
    private HighlightText highlightText = new HighlightText(Color.RED);


    public Find(JTextArea text) {
        this.txt = text;

        lab1 = new JLabel("Find:");
        lab2 = new JLabel("Replace:");
        textF = new JTextField(30);
        textR = new JTextField(30);
        findBtn = new JButton("Find");
        findNext = new JButton("Find Next");
        replace = new JButton("Replace");
        replaceAll = new JButton("Replace All");
        cancel = new JButton("Cancel");

        setLayout(null);

        int labWidth = 80;
        int labHeight = 20;

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

        int width = 360;
        int height = 160;

        setSize(width, height);

        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(center.x - width / 2, center.y - height / 2);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

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

    private void findNext() {
        String selection = txt.getSelectedText();
        try {
            selection.equals("");
        } catch (NullPointerException e) {
            selection = textF.getText();
            try {
                selection.equals("");
            } catch (NullPointerException e2) {
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
        }
    }

    private void replace() {
        try {
            find();
            if (select_start != -1)
                txt.replaceSelection(textR.getText());
        } catch (NullPointerException e) {
            System.out.print("Null Pointer Exception: " + e);
        }
    }

    private void replaceAll() {
        txt.setText(txt.getText().toLowerCase().replaceAll(textF.getText().toLowerCase(), textR.getText()));
    }
}
