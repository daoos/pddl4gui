package pddl4gui.gui.tools;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * This class implements the UndoRedo class of <code>PDDL4GUI</code>.
 * This class provides undo redo functions for JTextArea in PDDL4GUI.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class UndoRedo implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Adds undo redo functions on a JTextArea.
     *
     * @param textArea the JTextArea.
     */
    public static void addUndoRedo(JTextArea textArea) {
        final UndoManager undoManager = new UndoManager();
        final Document doc = textArea.getDocument();
        doc.addUndoableEditListener((UndoableEditEvent e) -> undoManager.addEdit(e.getEdit()));

        final InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        final ActionMap am = textArea.getActionMap();

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
