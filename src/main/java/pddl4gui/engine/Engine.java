package pddl4gui.engine;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.parser.Message;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.util.MemoryAgent;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.gui.panel.EnginePanel;
import pddl4gui.token.Result;
import pddl4gui.token.Statistics;
import pddl4gui.token.Token;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.JProgressBar;
import javax.swing.Timer;


/**
 * This class implements the Engine class of <code>PDDL4GUI</code>.
 * This object extends a thread and is used by EngineManager to solve token.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Engine extends Thread implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The EnginePanel which displays Engine status.
     */
    private final EnginePanel enginePanel;

    /**
     * The list of tokens to solve.
     */
    private final Vector<Token> ownQueue;

    /**
     * The error String containing error which appears during solving process.
     */
    private String error = "";

    /**
     * The refresh time of the Engine.
     */
    private int refresh;

    /**
     * The availability of the Engine.
     */
    private boolean available;

    /**
     * Adds token in the queue.
     *
     * @param token the token to add.
     */
    public void addTokenInQueue(Token token) {
        this.ownQueue.add(token);
    }

    /**
     * If the Engine is available : true. False otherwise.
     *
     * @return if the Engine is available.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Creates a new Engine. Its role is to solve tokens.
     *
     * @param refresh     the refresh time of the Engine.
     * @param enginePanel the EnginePanel which displays its status.
     */
    public Engine(int refresh, EnginePanel enginePanel) {
        this.refresh = refresh;
        this.enginePanel = enginePanel;
        this.ownQueue = new Vector<>();
        this.available = false;
    }

    /**
     * The process of the Engine.
     */
    @Override
    public void run() {
        final JProgressBar progressBar = enginePanel.getProgressBar();
        while (enginePanel.isVisible()) {
            try {
                available = false;
                if (ownQueue.size() > 0) {
                    error = "";
                    final Token token = ownQueue.firstElement();
                    ownQueue.remove(token);
                    enginePanel.getEngineLabel().setText(token.toString());
                    enginePanel.getCirclePanel().setColor(Color.ORANGE);
                    enginePanel.getCirclePanel().repaint();

                    final int timeout = token.getPlanner().getStateSpaceStrategies().get(0).getTimeout() / 1000;
                    progressBar.setValue(0);
                    progressBar.setMaximum(timeout);
                    progressBar.setString(progressBar.getValue() + "/" + timeout);
                    final Timer timer = new Timer(1000, (ActionEvent evt) -> {
                        progressBar.setValue(progressBar.getValue() + 1);
                        progressBar.setString(progressBar.getValue() + "/" + timeout);
                    });

                    timer.start();
                    System.out.println(token.getPlannerName().toString() + " planner on " + this.getName());
                    token.setSolved(resolve(token));
                    token.setError(error);
                    timer.stop();
                } else {
                    available = true;
                    progressBar.setValue(0);
                    progressBar.setString("Ready !");
                    enginePanel.getEngineLabel().setText("Waiting for token");
                    enginePanel.getCirclePanel().setColor(Color.GREEN);
                    enginePanel.getCirclePanel().repaint();
                }
                sleep(refresh);
            } catch (InterruptedException | IOException e) {
                available = false;
                progressBar.setString("");
                enginePanel.getEngineLabel().setText("Engine crash, restart it !");
                enginePanel.getCirclePanel().setColor(Color.RED);
                enginePanel.getCirclePanel().repaint();
                enginePanel.getInitButton().setEnabled(true);
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Resolves the token.
     *
     * @param token the token to solve.
     * @return true if the token is solved. False otherwise.
     * @throws IOException if there is issues with PDDL files.
     */
    private boolean resolve(final Token token) throws IOException {
        if (token.isRunnable()) {
            final Statistics statistics = new Statistics();
            final ProblemFactory factory = new ProblemFactory();

            long begin = System.currentTimeMillis();
            ErrorManager errorManager = factory.parse(token.getDomainFile(), token.getProblemFile());
            if (!errorManager.isEmpty()) {
                if (!errorManager.getMessages(token.getDomainFile()).isEmpty()) {
                    error = error.concat("Error in " + token.getDomainFileName() + "\n");
                    for (Message message : errorManager.getMessages(token.getDomainFile())) {
                        error = error.concat(message.getContent() + "\n");
                    }
                }
                if (!errorManager.getMessages(token.getProblemFile()).isEmpty()) {
                    error = error.concat("Error in " + token.getProblemFileName() + "\n");
                    for (Message message : errorManager.getMessages(token.getProblemFile())) {
                        error = error.concat(message.getContent() + "\n");
                    }
                }
                return false;
            }
            statistics.setTimeToParseInSeconds((System.currentTimeMillis() - begin) / 1000.0);

            final CodedProblem pb;
            try {
                begin = System.currentTimeMillis();
                try {
                    pb = factory.encode();
                } catch (IllegalArgumentException e) {
                    error = ("Error during encoding process.\n Check the :requirements part of the domain !");
                    e.printStackTrace();
                    return false;
                }
                if (pb != null) {
                    statistics.setTimeToEncodeInSeconds((System.currentTimeMillis() - begin) / 1000.0);
                    statistics.setNumberOfActions(pb.getOperators().size());
                    statistics.setNumberOfFluents(pb.getRelevantFacts().size());
                    statistics.setMemoryForProblemInMBytes(MemoryAgent.getDeepSizeOf(pb) / (1024.0 * 1024.0));
                    if (!pb.isSolvable()) {
                        error = ("Goal can be simplified to FALSE.\n"
                                + "No search will solve it !");
                        return false;
                    }

                    begin = System.currentTimeMillis();
                    final Planner planner = token.getPlanner();
                    final Plan plan = planner.search(pb);
                    statistics.setTimeToPlanInSeconds((System.currentTimeMillis() - begin) / 1000.0);
                    statistics.setMemoryUsedToSearchInMBytes(planner.getStatistics()
                            .getMemoryUsedToSearch() / (1024.0 * 1024.0));

                    token.setResult(new Result(statistics, pb, plan));
                    if (plan != null) {
                        statistics.setCost(plan.cost());
                        statistics.setDepth(plan.size());
                        return true;
                    } else {
                        error = ("No plan found !");
                        statistics.setCost(0);
                        statistics.setDepth(0);
                        return false;
                    }
                } else {
                    error = ("Encoding problem failed !");
                    return false;
                }
            } catch (NullPointerException e) {
                error = ("Error during solving process.\n Check the problem.pddl file !");
                e.printStackTrace();
                return false;
            }
        } else {
            error = ("Domain or Problem not defined !");
            return false;
        }
    }
}
