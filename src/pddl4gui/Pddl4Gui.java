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

    private Solver solver;
    private Engine engine;

    public Solver getSolver() {
        return solver;
    }

    public Engine getEngine() {
        return engine;
    }

    public Pddl4Gui() {
        solver = new Solver(this);
        engine = new Engine(this, 500);
        engine.start();
    }

    /**
     * The pddl4g method of <code>PDDL4GUI</code>.
     *
     * @param args the arguments of the command line.
     */
    public static void main(String[] args) {
        PlafOptions.setAsLookAndFeel();
        PlafOptions.updateAllUIs();

        new Pddl4Gui();
    }
}
