package pddl4gui.gui.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.swing.JTextArea;

/**
 * This class implements the CustomOutputStream class of <code>PDDL4GUI</code>.
 * This class extends the OutputStream to write output in a JTextArea.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class CustomOutputStream extends OutputStream implements Serializable {

    /**
     * The JTextArea which contains the output.
     */
    private JTextArea textArea;

    /**
     * Create a new CustomOutputStream for a specific JTextArea.
     * @param textArea the JTextArea
     */
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Write an output into a JtextArea.
     * @param b the int value of a character
     * @throws IOException if IO error
     */
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
