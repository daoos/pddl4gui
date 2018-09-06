package pddl4gui.gui.tools;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.HashMap;

/**
 * This class will display line numbers for a related text component. The text
 * component must use the same line height for each line. TextLineNumber
 * supports wrapped lines and will highlight the line number of the current
 * line in the text component.
 * This class was designed to be used as a component added to the row header
 * of a JScrollPane.
 */
public class TextLineNumber extends JPanel implements CaretListener, DocumentListener, Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The right marging.
     */
    private static final float RIGHT = 1.0f;

    /**
     * The outer border.
     */
    private static final Border OUTER = new MatteBorder(0, 0, 0, 2, Color.GRAY);

    /**
     * The height parameter.
     */
    private static final int HEIGHT = Integer.MAX_VALUE - 1000000;

    /**
     * Text component this TextTextLineNumber component is in sync with.
     */
    private final JTextComponent component;

    /**
     * Properties that can be changed: color.
     */
    private Color currentLineForeground;

    /**
     * Properties that can be changed: alignement.
     */
    private float digitAlignment;

    /**
     * Properties that can be changed: minimal display digit.
     */
    private int minimumDisplayDigits;

    /**
     * Keep history information to reduce the number of times the component needs to be repainted: last digit.
     */
    private int lastDigits;

    /**
     * Keep history information to reduce the number of times the component needs to be repainted: last height.
     */
    private int lastHeight;

    /**
     * Keep history information to reduce the number of times the component needs to be repainted: last line.
     */
    private int lastLine;

    /**
     * The fonts hashmap.
     */
    private HashMap<String, FontMetrics> fonts;

    /**
     * Create a line number component for a text component. This minimum
     * display width will be based on 3 digits.
     *
     * @param component the related text component.
     */
    public TextLineNumber(JTextComponent component) {
        this(component, 3);
    }

    /**
     * Create a line number component for a text component.
     *
     * @param component            the related text component.
     * @param minimumDisplayDigits the number of digits used to calculate the minimum width of the component.
     */
    private TextLineNumber(JTextComponent component, int minimumDisplayDigits) {
        this.component = component;

        setFont(component.getFont());

        setBorderGap();
        setCurrentLineForeground(Color.RED);
        setDigitAlignment(RIGHT);
        setMinimumDisplayDigits(minimumDisplayDigits);

        component.getDocument().addDocumentListener(this);
        component.addCaretListener(this);
    }

    /**
     * The border gap is used in calculating the left and right insets of the
     * border. Default value is 5.
     */
    private void setBorderGap() {
        final Border inner = new EmptyBorder(0, 5, 0, 5);
        setBorder(new CompoundBorder(OUTER, inner));
        lastDigits = 0;
        setPreferredWidth();
    }

    /**
     * Gets the current line rendering Color.
     *
     * @return the Color used to render the current line number.
     */
    private Color getCurrentLineForeground() {
        return currentLineForeground == null ? getForeground() : currentLineForeground;
    }

    /**
     * The Color used to render the current line digits. Default is Coolor.RED.
     *
     * @param currentLineForeground the Color used to render the current line.
     */
    private void setCurrentLineForeground(Color currentLineForeground) {
        this.currentLineForeground = currentLineForeground;
    }

    /**
     * Specify the horizontal alignment of the digits within the component.
     * Common values would be:
     * <ul>
     * <li>TextLineNumber.LEFT
     * <li>TextLineNumber.CENTER
     * <li>TextLineNumber.RIGHT (default)
     * </ul>
     *
     * @param digitAlignment the digits within the component.
     */
    private void setDigitAlignment(float digitAlignment) {
        this.digitAlignment =
                digitAlignment > 1.0f ? 1.0f : digitAlignment < 0.0f ? -1.0f : digitAlignment;
    }

    /**
     * Specify the mimimum number of digits used to calculate the preferred
     * width of the component. Default is 3.
     *
     * @param minimumDisplayDigits the number digits used in the preferred width calculation.
     */
    private void setMinimumDisplayDigits(int minimumDisplayDigits) {
        this.minimumDisplayDigits = minimumDisplayDigits;
        setPreferredWidth();
    }

    /**
     * Calculate the width needed to display the maximum line number.
     */
    private void setPreferredWidth() {
        final Element root = component.getDocument().getDefaultRootElement();
        int lines = root.getElementCount();
        int digits = Math.max(String.valueOf(lines).length(), minimumDisplayDigits);

        //  Update sizes when number of digits in the line number changes

        if (lastDigits != digits) {
            lastDigits = digits;
            final FontMetrics fontMetrics = getFontMetrics(getFont());
            int width = fontMetrics.charWidth('0') * digits;
            final Insets insets = getInsets();
            int preferredWidth = insets.left + insets.right + width;

            final Dimension d = getPreferredSize();
            d.setSize(preferredWidth, HEIGHT);
            setPreferredSize(d);
            setSize(d);
        }
    }

    /**
     * Draw the line numbers.
     *
     * @param g the graphic component to paint.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //	Determine the width of the space available to draw the line number
        final FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
        final Insets insets = getInsets();
        int availableWidth = getSize().width - insets.left - insets.right;

        //  Determine the rows to draw within the clipped bounds.

        final Rectangle clip = g.getClipBounds();
        int rowStartOffset = component.viewToModel(new Point(0, clip.y));
        final int endOffset = component.viewToModel(new Point(0, clip.y + clip.height));

        while (rowStartOffset <= endOffset) {
            try {
                if (isCurrentLine(rowStartOffset)) {
                    g.setColor(getCurrentLineForeground());
                } else {
                    g.setColor(getForeground());
                }

                //  Get the line number as a string and then determine the
                //  "X" and "Y" offsets for drawing the string.

                final String lineNumber = getTextLineNumber(rowStartOffset);
                final int stringWidth = fontMetrics.stringWidth(lineNumber);
                final int x = getOffsetX(availableWidth, stringWidth) + insets.left;
                final int y = getOffsetY(rowStartOffset, fontMetrics);
                g.drawString(lineNumber, x, y);

                //  Move to the next row

                rowStartOffset = Utilities.getRowEnd(component, rowStartOffset) + 1;
            } catch (Exception e) {
                break;
            }
        }
    }

    /**
     * We need to know if the caret is currently positioned on the line we
     * are about to paint so the line number can be highlighted.
     *
     * @param rowStartOffset the starting row offset.
     */
    private boolean isCurrentLine(int rowStartOffset) {
        final int caretPosition = component.getCaretPosition();
        final Element root = component.getDocument().getDefaultRootElement();

        return root.getElementIndex(rowStartOffset) == root.getElementIndex(caretPosition);
    }

    /**
     * Get the line number to be drawn. The empty string will be returned
     * when a line of text has wrapped.
     *
     * @param rowStartOffset the starting row offset.
     */
    private String getTextLineNumber(int rowStartOffset) {
        final Element root = component.getDocument().getDefaultRootElement();
        final int index = root.getElementIndex(rowStartOffset);
        final Element line = root.getElement(index);

        if (line.getStartOffset() == rowStartOffset) {
            return String.valueOf(index + 1);
        } else {
            return "";
        }
    }

    /**
     * Determine the X offset to properly align the line number when drawn.
     *
     * @param availableWidth the available width.
     * @param stringWidth    the string width.
     */
    private int getOffsetX(int availableWidth, int stringWidth) {
        return (int) ((availableWidth - stringWidth) * digitAlignment);
    }

    /**
     * Determine the Y offset for the current row.
     *
     * @param rowStartOffset the starting row offset.
     * @param fontMetrics    the font metrics.
     */
    private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics) throws BadLocationException {
        //  Get the bounding rectangle of the row

        final Rectangle r = component.modelToView(rowStartOffset);
        final int lineHeight = fontMetrics.getHeight();
        final int y = r.y + r.height;
        int descent = 0;

        //  The text needs to be positioned above the bottom of the bounding
        //  rectangle based on the descent of the font(s) contained on the row.

        if (r.height == lineHeight) {
            descent = fontMetrics.getDescent();
        } else {// We need to check all the attributes for font changes
            if (fonts == null) {
                fonts = new HashMap<>();
            }

            final Element root = component.getDocument().getDefaultRootElement();
            final int index = root.getElementIndex(rowStartOffset);
            final Element line = root.getElement(index);

            for (int i = 0; i < line.getElementCount(); i++) {
                final Element child = line.getElement(i);
                final AttributeSet as = child.getAttributes();
                final String fontFamily = (String) as.getAttribute(StyleConstants.FontFamily);
                final Integer fontSize = (Integer) as.getAttribute(StyleConstants.FontSize);
                final String key = fontFamily + fontSize;

                FontMetrics fm = fonts.get(key);

                if (fm == null) {
                    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = component.getFontMetrics(font);
                    fonts.put(key, fm);
                }

                descent = Math.max(descent, fm.getDescent());
            }
        }

        return y - descent;
    }

    /**
     * Implement CaretListener interface.
     *
     * @param e the caret event.
     */
    @Override
    public void caretUpdate(CaretEvent e) {
        //  Get the line the caret is positioned on

        final int caretPosition = component.getCaretPosition();
        final Element root = component.getDocument().getDefaultRootElement();
        final int currentLine = root.getElementIndex(caretPosition);

        //  Need to repaint so the correct line number can be highlighted

        if (lastLine != currentLine) {
            repaint();
            lastLine = currentLine;
        }
    }

    /**
     * Implement DocumentListener interface: change in document.
     *
     * @param e the document event.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged();
    }


    /**
     * Implement DocumentListener interface: insert in document.
     *
     * @param e the document event.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged();
    }


    /**
     * Implement DocumentListener interface: remove in document.
     *
     * @param e the document event.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged();
    }

    /**
     * A document change may affect the number of displayed lines of text.
     * Therefore the lines numbers will also change.
     */
    private void documentChanged() {
        //  View of the component has not been updated at the time
        //  the DocumentEvent is fired

        SwingUtilities.invokeLater(() -> {
            try {
                final int endPos = component.getDocument().getLength();
                final Rectangle rect = component.modelToView(endPos);

                if (rect != null && rect.y != lastHeight) {
                    setPreferredWidth();
                    repaint();
                    lastHeight = rect.y;
                }
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        });
    }
}
