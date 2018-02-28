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
     * The main frame of PDDL4GUI.
     */
    private Solver solver;

    /**
     * The background engine which solve problems.
     */
    private Engine engine;

    /**
     * Returns the main frame of PDDL4GUI.
     *
     * @return the main frame of PDDL4GUI.
     */
    public Solver getSolver() {
        return solver;
    }

    /**
     * Returns the background engine which solve problems.
     *
     * @return the background engine which solve problems.
     */
    public Engine getEngine() {
        return engine;
    }

    /**
     * Creates a new main GUI.
     */
    private Pddl4Gui() {
        solver = new Solver(this);
        engine = new Engine(this, 500);
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
