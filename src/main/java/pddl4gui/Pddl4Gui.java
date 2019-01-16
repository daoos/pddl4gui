package pddl4gui;

import com.pagosoft.plaf.PlafOptions;
import pddl4gui.gui.RestClient;
import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.Queue;

import java.io.Serializable;
import javax.swing.JOptionPane;

/**
 * This class implements the main class of <code>PDDL4GUI</code>.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Pddl4Gui implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new main GUI.
     */
    private Pddl4Gui() {
        TriggerAction.setPDDL4GUIRunning(true);
        final Object[] options = {"Local solver", "REST solver"};
        int selection = JOptionPane.showOptionDialog(null,"Which solver do you want to use?","Choose a solver",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (selection == 0) {
            new Solver(new Queue());
        } else if (selection == 1) {
            String restService = JOptionPane.showInputDialog(null,
                    "i.e: http://pddl4j-dev.imag.fr/pddl4j-service-1.0",
                    "Enter URL of RESTFull API", JOptionPane.INFORMATION_MESSAGE);

            if (restService != null && !restService.equals("")) {
                new RestClient(restService + "/search");
            } else {
                new RestClient("http://localhost:8080/pddl4j-service-1.0/search");
            }
        } else {
            System.exit(0);
        }
    }

    /**
     * The pddl4gui main method of <code>PDDL4GUI</code>.
     *
     * @param args the arguments of the command line.
     */
    public static void main(String[] args) {
        PlafOptions.setAsLookAndFeel();
        PlafOptions.updateAllUIs();

        new Pddl4Gui();
    }
}
