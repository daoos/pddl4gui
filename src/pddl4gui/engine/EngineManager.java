package pddl4gui.engine;

import pddl4gui.gui.panel.EngineManagerPanel;
import pddl4gui.token.Queue;

import java.util.Vector;

public class EngineManager extends Thread {

    final private int refresh;

    final private Vector<Engine> engineList;

    final private Queue queue;

    final private EngineManagerPanel engineManagerPanel;

    public void addEngine(Engine engine) {
        engineList.add(engine);
    }

    public void removeEngine(Engine engine) {
        engineList.remove(engine);
    }

    public boolean isRunning() {
        return this.isAlive();
    }

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

    public EngineManager(int refresh, EngineManagerPanel engineManagerPanel, Queue queue) {
        this.refresh = refresh;
        this.engineManagerPanel = engineManagerPanel;
        this.queue = queue;
        this.engineList = new Vector<>();
    }

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
