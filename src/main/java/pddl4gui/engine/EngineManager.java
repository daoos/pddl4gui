package pddl4gui.engine;

import pddl4gui.gui.tools.TriggerAction;
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
    private final int refresh;

    /**
     * The list of all Engine.
     */
    private final Vector<Engine> engineList;

    /**
     * The Queue of token.
     */
    private final Queue queue;

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
     * @param refresh            the refresh time of the EngineManager.
     * @param queue              the token Queue which is managed by the EngineManager.
     */
    public EngineManager(final int refresh, final Queue queue) {
        this.refresh = refresh;
        this.queue = queue;
        this.engineList = new Vector<>();
    }

    /**
     * The process of the EngineManager.
     */
    @Override
    public void run() {
        while (TriggerAction.isPDDL4GUIRunning()) {
            try {
                if (queue.remainingTokens() > 0) {
                    final Engine engine = getEngine();
                    engine.addTokenInQueue(queue.getToken());
                    TriggerAction.setTokenRemainingEngineManagerPanel();
                }
                sleep(refresh);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
