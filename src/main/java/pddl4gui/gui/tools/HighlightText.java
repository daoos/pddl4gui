package pddl4gui.gui.tools;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.io.Serializable;

/**
 * This class implements the HighlightText class of <code>PDDL4GUI</code>.
 * This class provide an highlighter for JText component.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class HighlightText extends DefaultHighlighter.DefaultHighlightPainter implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new HighlightText object with an highlight color.
     *
     * @param color the color to highlight text.
     */
    public HighlightText(Color color) {
        super(color);
    }

    /**
     * Highlights in red the text of a JTextComponent.
     *
     * @param textComp     the JTextComponent.
     * @param select_start the starting index of the highlighted text.
     * @param select_end   the ending index of the highlighted text.
     */
    public void hightLightWord(JTextComponent textComp, int select_start, int select_end) {
        removeHighlights(textComp);
        final Highlighter highlight = textComp.getHighlighter();
        try {
            highlight.addHighlight(select_start, select_end, new HighlightText(Color.RED));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Highlights a text pattern in a JTextComponent.
     *
     * @param textComp the JTextComponent.
     * @param pattern  the text pattern.
     */
    public void highLight(JTextComponent textComp, String[] pattern) {
        removeHighlights(textComp);

        try {
            final Highlighter highlight = textComp.getHighlighter();
            final Document doc = textComp.getDocument();
            final String text = doc.getText(0, doc.getLength());
            for (String item : pattern) {
                int pos = 0;
                while ((pos = text.indexOf(item, pos)) >= 0) {
                    highlight.addHighlight(pos, pos + item.length(), this);
                    pos += item.length();
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Removes all highlights in a JTextComponent.
     *
     * @param textComp the JTextComponent.
     */
    private void removeHighlights(JTextComponent textComp) {
        final Highlighter highlight = textComp.getHighlighter();

        for (Highlighter.Highlight item : highlight.getHighlights()) {
            if (item.getPainter() instanceof HighlightText) {
                highlight.removeHighlight(item);
            }
        }
    }
}
