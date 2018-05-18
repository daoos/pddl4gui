package pddl4gui.gui.tools;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.Color;

public class HighlightText extends DefaultHighlighter.DefaultHighlightPainter {

    public HighlightText(Color color) {
        super(color);
    }

    public void hightLightWord(JTextComponent textComp, int select_start, int select_end) {
        removeHighlights(textComp);
        final Highlighter highlight = textComp.getHighlighter();
        try {
            highlight.addHighlight(select_start, select_end, new HighlightText(Color.RED));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

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

    private void removeHighlights(JTextComponent textComp) {
        final Highlighter highlight = textComp.getHighlighter();

        for (Highlighter.Highlight item : highlight.getHighlights()) {
            if (item.getPainter() instanceof HighlightText) {
                highlight.removeHighlight(item);
            }
        }
    }
}
