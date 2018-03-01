package pddl4gui.gui.tools;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class UndoRedo {

    public static void addUndoRedo(JTextArea jTextArea) {
        final UndoManager undoManager = new UndoManager();
        final Document doc = jTextArea.getDocument();
        doc.addUndoableEditListener((UndoableEditEvent e) -> undoManager.addEdit(e.getEdit()));

        final InputMap im = jTextArea.getInputMap(JComponent.WHEN_FOCUSED);
        final ActionMap am = jTextArea.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
    }
}
