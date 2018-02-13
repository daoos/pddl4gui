import com.pagosoft.plaf.PlafOptions;
import solver.gui.Solver;

/**
 * This class implements the main method of <code>PDDL4GUI</code>.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Main {

    /**
     * The main method of <code>PDDL4GUI</code>.
     *
     * @param args the arguments of the command line.
     */
    public static void main(String[] args) {
        PlafOptions.setAsLookAndFeel();
        PlafOptions.updateAllUIs();
        new Solver();
    }
}
