package pddl4gui;

import com.pagosoft.plaf.PlafOptions;
import pddl4gui.gui.RestSolver;
import pddl4gui.gui.Solver;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.Queue;

import java.io.Serializable;

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
    private Pddl4Gui(final String[] args) {
        TriggerAction.setPDDL4GUIRunning(true);

        if (args.length > 0 && args[0].equals("-REST")) {
            new RestSolver();
        } else if (args.length > 0 && args[0].equals("-LOCAL")) {
            new Solver(new Queue());
        } else {
            System.out.println("** PDDL4GUI **");
            System.out.println("* java -jar pddl4gui-1.0.jar -LOCAL -> use local solver");
            System.out.println("* java -jar pddl4gui-1.0.jar -REST  -> use REST solver");
            System.out.println("**");
            TriggerAction.setPDDL4GUIRunning(false);
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

        new Pddl4Gui(args);
    }
}
