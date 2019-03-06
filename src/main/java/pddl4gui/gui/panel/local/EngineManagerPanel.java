package pddl4gui.gui.panel.local;

import pddl4gui.engine.EngineManager;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.LocalToken;

import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * This class implements the EngineManagerPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays all the EnginePanel.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class EngineManagerPanel extends JPanel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The EngineManager which manages Engine.
     */
    private final EngineManager engineManager;

    /**
     * The HashMap used to sort the EnginePanel.
     */
    private final HashMap<Integer, Boolean> engineStatus = initHashMap();

    /**
     * The JLabel for remaining tokens to solve.
     */
    private final JLabel remainingLabel;

    /**
     * Returns the EngineManager which manages Engine.
     *
     * @return the EngineManager which manages Engine.
     */
    public EngineManager getEngineManager() {
        return engineManager;
    }

    /**
     * Sets the text of the remaining label about the number of remaining tokens to solve.
     */
    public void setTokensRemaining() {
        remainingLabel.setText(TriggerAction.getRemainningTokenInQueue() + " token(s) remaining");
    }

    /**
     * If the EngineManager is running: true. False otherwise.
     *
     * @return true if the EngineManager is running, false otherwise.
     */
    public boolean isRunning() {
        return engineManager.isRunning();
    }

    /**
     * Creates a new EngineManagerPanel associated to the Solver main JFrame.
     */
    public EngineManagerPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Engines status"));

        this.engineManager = new EngineManager(2000, TriggerAction.getQueue(), this);
        int cores = Runtime.getRuntime().availableProcessors();
        if (cores > 4) {
            cores = 4;
        }

        final SpinnerNumberModel numberCoreModel = new SpinnerNumberModel(1, 1, cores - 1, 1);
        final JSpinner coreSpinner = new JSpinner(numberCoreModel);
        coreSpinner.setBounds(165, 185, 150, 25);
        coreSpinner.addChangeListener(e -> this.engineManager.setNumberEngineMax((Integer) coreSpinner.getValue()));
        add(coreSpinner);

        final JLabel coreLabel = new JLabel("Number of engines");
        coreLabel.setBounds(15, 185, 150, 25);
        add(coreLabel);

        this.remainingLabel = new JLabel(TriggerAction.getRemainningTokenInQueue() + " token(s) remaining");
        this.remainingLabel.setBounds(185, 214, 265, 20);
        add(this.remainingLabel);
    }

    /**
     * Adds a new EnginPanel.
     *
     * @return the EnginePanel created.
     */
    public EnginePanel addEnginePanel(final LocalToken token) {
        final int labMarging = 45;
        final EnginePanel enginePanel = new EnginePanel(this.getId(),this, token);
        enginePanel.setBounds(10, 25 + labMarging * enginePanel.getId(), 310, 50);
        add(enginePanel);

        this.repaint();

        return enginePanel;
    }

    /**
     * Removes the given EnginePanel.
     *
     * @param enginePanel the EnginePanel to remove.
     */
    public void removeEnginePanel(final EnginePanel enginePanel) {
        this.freeId(enginePanel.getId());
        this.remove(enginePanel);
        this.repaint();
    }

    /**
     * Starts the EngineManager.
     */
    public void startEngineManager() {
        this.engineManager.start();
    }

    /**
     * Initializes the HashMap.
     *
     * @return an initialized HashMap.
     */
    private HashMap<Integer, Boolean> initHashMap() {
        HashMap<Integer, Boolean> hashMap = new HashMap<>();
        hashMap.put(0, true);
        hashMap.put(1, true);
        hashMap.put(2, true);
        return hashMap;
    }

    /**
     * Returns the id of a free spot in the HashMap (free = true in value).
     *
     * @return the id of a free spot in the HashMap.
     */
    private int getId() {
        int id = 0;
        for (HashMap.Entry<Integer, Boolean> entry : this.engineStatus.entrySet()) {
            if (entry.getValue()) {
                entry.setValue(false);
                id = entry.getKey();
                break;
            }
        }
        return id;
    }

    /**
     * Free the id (set the value to true).
     *
     * @param id the id to free.
     */
    private void freeId(final int id) {
        this.engineStatus.put(id, true);
    }
}
