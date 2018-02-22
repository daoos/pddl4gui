package main.pddl4g;

import com.pagosoft.plaf.PlafOptions;
import main.pddl4g.engine.Engine;
import main.pddl4g.gui.Solver;

/**
 * This class implements the pddl4g method of <code>PDDL4GUI</code>.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Pddl4G {

    private Solver solver;
    private Engine engine;

    public Solver getSolver() {
        return solver;
    }

    public Engine getEngine() {
        return engine;
    }

    public Pddl4G(){
        solver = new Solver(this);
        engine = new Engine(this);
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

        new Pddl4G();
    }
}
