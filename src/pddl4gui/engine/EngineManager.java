package pddl4gui.engine;

import pddl4gui.gui.panel.EngineManagerPanel;
import pddl4gui.token.Queue;

import java.io.Serializable;
import java.util.Vector;

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
     * The refresh time of the EngineManager.
     */
    final private int refresh;

    /**
     * The list of all Engine.
     */
    final private Vector<Engine> engineList;

    /**
     * The Queue of token.
     */
    final private Queue queue;

    /**
     * The EngineManagerPanel associated which displays status of EngineManager.
     */
    final private EngineManagerPanel engineManagerPanel;

    /**
     * Adds an Engine.
     *
     * @param engine the Engine to add.
     */
    public void addEngine(Engine engine) {
        engineList.add(engine);
    }

    /**
     * Removes an Engine.
     *
     * @param engine the Engine to remove.
     */
    public void removeEngine(Engine engine) {
        engineList.remove(engine);
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
     * Gets an available Engine.
     *
     * @return the first available Engine.
     */
    private Engine getEngine() {
        boolean engineIsAvailable = false;
        Engine engineTemp = new Engine(refresh, null);
        while (!engineIsAvailable) {
            try {
                for (Engine engine : engineList) {
                    if (engine.isAvailable()) {
                        engineIsAvailable = true;
                        engineTemp = engine;
                    }
                }
                sleep(refresh);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        return engineTemp;
    }

    /**
     * Creates a new EngineManager which manages Engine.
     *
     * @param refresh the refresh time of the EngineManager.
     * @param engineManagerPanel the EngineManagerPanel which displays its status.
     * @param queue the token Queue which is managed by the EngineManager.
     */
    public EngineManager(int refresh, EngineManagerPanel engineManagerPanel, Queue queue) {
        this.refresh = refresh;
        this.engineManagerPanel = engineManagerPanel;
        this.queue = queue;
        this.engineList = new Vector<>();
    }

    /**
     * The process of the EngineManager.
     */
    @Override
    public void run() {
        while (engineManagerPanel.isVisible()) {
            try {
                if (queue.remainingTokens() > 0) {
                    final Engine engine = getEngine();
                    engine.addTokenInQueue(queue.getToken());
                    engineManagerPanel.setTokensRemaining();
                }
                sleep(refresh);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
