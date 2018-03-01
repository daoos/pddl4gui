package pddl4gui;

import com.pagosoft.plaf.PlafOptions;
import pddl4gui.engine.Engine;
import pddl4gui.gui.Solver;

/**
 * This class implements the main class of <code>PDDL4GUI</code>.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Pddl4Gui {

    /**
     * Creates a new main GUI.
     */
    private Pddl4Gui() {
        final Engine engine = new Engine(500);
        final Solver solver = new Solver(engine);
        engine.setSolver(solver);
        engine.start();
    }

    /**
     * The pddl4g main method of <code>PDDL4GUI</code>.
     *
     * @param args the arguments of the command line.
     */
    public static void main(String[] args) {
        PlafOptions.setAsLookAndFeel();
        PlafOptions.updateAllUIs();

        new Pddl4Gui();
    }
}
