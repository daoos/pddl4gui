package pddl4gui.engine;

import pddl4gui.gui.panel.local.EngineManagerPanel;
import pddl4gui.gui.panel.local.EnginePanel;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.Queue;

import java.io.Serializable;

/**
 * This class implements the EngineManager class of <code>PDDL4GUI</code>.
 * This object extends a thread and manage all the Engines initialized.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class EngineManager extends Thread implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The EngineManagerPanel which displays the EnginPanel.
     */
    private final EngineManagerPanel engineManagerPanel;

    /**
     * The refresh time of the EngineManager.
     */
    private final int refresh;

    /**
     * The maximum number of running Engines.
     */
    private int numberEngineMax = 1;

    /**
     * The current number of running Engines.
     */
    private int numberEngineRunning = 0;

    /**
     * The Queue of token.
     */
    private final Queue queue;

    /**
     * Sets the maximum number of running Engines.
     *
     * @param numberEngineMax the maximum number of running Engines.
     */
    public void setNumberEngineMax(int numberEngineMax) {
        this.numberEngineMax = numberEngineMax;
    }

    /**
     * Increases the current number of running Engines.
     */
    public void increaseNumberEngineRunning() {
        this.numberEngineRunning++;
    }

    /**
     * Decreases the current number of running Engines.
     */
    public void decreaseNumberEngineRunning() {
        this.numberEngineRunning--;
    }

    /**
     * If the EngineManager is alive : true. False otherwise.
     *
     * @return if the EngineManager is alive.
     */
    public boolean isRunning() {
        return this.isAlive();
    }

    /**
     * Creates a new EngineManager which manages Engine.
     *
     * @param refresh            the refresh time of the EngineManager.
     * @param queue              the token Queue which is managed by the EngineManager.
     */
    public EngineManager(final int refresh, final Queue queue, final EngineManagerPanel engineManagerPanel) {
        this.refresh = refresh;
        this.queue = queue;
        this.engineManagerPanel = engineManagerPanel;
    }

    /**
     * The process of the EngineManager.
     */
    public void run() {
        while (TriggerAction.isPDDL4GUIRunning()) {
            try {
                if (this.queue.remainingTokens() > 0 && this.numberEngineRunning < this.numberEngineMax) {
                    final EnginePanel enginePanel = this.engineManagerPanel.addEnginePanel(this.queue.getToken());
                    System.out.println("[EngineManager] create Engine with id " + enginePanel.getId());
                    this.engineManagerPanel.setTokensRemaining();
                    this.increaseNumberEngineRunning();
                    System.out.println("[EngineManager] " + this.numberEngineRunning + "/"
                            + this.numberEngineMax + " Engine(s)");
                    enginePanel.startEngine();
                }
                sleep(this.refresh);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
    }
}
