import com.pagosoft.plaf.PlafOptions;
import solver.gui.Solver;

public class Main {
    public static void main(String[] args) {
        PlafOptions.setAsLookAndFeel();
        PlafOptions.updateAllUIs();
        new Solver();
    }
}
