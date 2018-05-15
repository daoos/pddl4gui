package pddl4gui.gui.panel;

import pddl4gui.engine.Queue;
import pddl4gui.gui.Solver;

import javax.swing.*;
import java.util.Vector;

public class EngineManagerPanel extends JPanel {

    final private Vector<EnginePanel> enginePanels;

    final private Solver solver;

    final private JLabel remainingLabel;

    public Queue getQueue() {
        return solver.getQueue();
    }

    public void setTokensRemaining() {
        remainingLabel.setText(solver.getQueue().remainingTokens() + " token(s) remaining");
    }

    public boolean isStatus() {
        boolean engineStatus = false;
        for(EnginePanel enginePanel : enginePanels) {
            if(enginePanel.isStatus()) {
                engineStatus = true;
            }
        }
        return engineStatus;
    }

    public EngineManagerPanel(Solver solver) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engine status"));

        this.solver = solver;
        enginePanels = new Vector<>();

        int labHeight = 25;
        int labMarging = 45;

        EnginePanel enginePanel1 = new EnginePanel(this, true);
        enginePanel1.setBounds(10, labHeight, 310, 50);
        enginePanels.add(enginePanel1);
        add(enginePanel1);

        labHeight += labMarging;

        EnginePanel enginePanel2 = new EnginePanel(this, false);
        enginePanel2.setBounds(10, labHeight, 310, 50);
        enginePanels.add(enginePanel2);
        add(enginePanel2);

        labHeight += labMarging;

        EnginePanel enginePanel3 = new EnginePanel(this, false);
        enginePanel3.setBounds(10, labHeight, 310, 50);
        enginePanels.add(enginePanel3);
        add(enginePanel3);

        labHeight += labMarging;

        EnginePanel enginePanel4 = new EnginePanel(this, false);
        enginePanel4.setBounds(10, labHeight, 310, 50);
        enginePanels.add(enginePanel4);
        add(enginePanel4);

        labHeight += labMarging * 1.2;

        remainingLabel = new JLabel(" -- token(s) remaining");
        remainingLabel.setBounds(200, labHeight, 265, 20);
        add(remainingLabel);
    }
}
