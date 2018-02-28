package pddl4gui.gui.tools;

import java.text.DecimalFormat;

public class DecimalFormatSetup {

    private static DecimalFormat df = new DecimalFormat("##.###");

    public static DecimalFormat getDf() {
        return df;
    }

    private DecimalFormatSetup() {
    }
}
