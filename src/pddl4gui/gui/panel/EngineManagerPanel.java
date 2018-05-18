package pddl4gui.gui.panel;

import pddl4gui.engine.Engine;
import pddl4gui.engine.EngineManager;
import pddl4gui.gui.Solver;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EngineManagerPanel extends JPanel {

    final private Solver solver;

    final private EngineManager engineManager;

    final private JLabel remainingLabel;

    public void addEngine(Engine engine) {
        engineManager.addEngine(engine);
    }

    public void removeEngine(Engine engine) {
        engineManager.removeEngine(engine);
    }

    public void setTokensRemaining() {
        remainingLabel.setText(solver.getQueue().remainingTokens() + " token(s) remaining");
    }

    public boolean isStatus() {
        return engineManager.isRunning();
    }

    public EngineManagerPanel(Solver solver) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engines status"));

        engineManager = new EngineManager(500, this, solver.getQueue());

        this.solver = solver;

        int labHeight = 25;
        final int labMarging = 45;
        final int cores = Runtime.getRuntime().availableProcessors();

        boolean activeEngine = true;
        int inc = 0;
        do {
            if (inc > 0) {
                activeEngine = false;
            }

            final EnginePanel enginePanel = new EnginePanel(this, activeEngine);
            enginePanel.setBounds(10, labHeight, 310, 50);
            add(enginePanel);

            labHeight += labMarging;
            inc++;
        }
        while (inc < cores - 1 && inc < 4);

        remainingLabel = new JLabel(" -- token(s) remaining");
        remainingLabel.setBounds(200, 214, 265, 20);
        add(remainingLabel);

        engineManager.start();
    }
}
