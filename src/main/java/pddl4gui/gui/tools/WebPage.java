package pddl4gui.gui.tools;

import java.awt.Desktop;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class implements the WebPage static class of <code>PDDL4GUI</code>.
 * This class provides functions to open URL and URI in Desktop.
 *
 * @author E. Hermellin
 * @version 1.0 - 23.01.2019
 */
public class WebPage implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Opens URI webpage in Desktop.
     *
     * @param uri the uri to open.
     * @return the status of the action.
     */
    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Opens URL webpage in Desktop.
     *
     * @param url the url to open.
     * @return the status of the action.
     */
    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
