package pddl4gui.engine;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.parser.Message;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.planners.statespace.StateSpacePlanner;
import fr.uga.pddl4j.util.MemoryAgent;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.gui.panel.local.EnginePanel;
import pddl4gui.token.LocalToken;
import pddl4gui.token.Statistics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 * This class implements the Engine class of <code>PDDL4GUI</code>.
 * This object extends a thread and is used by EngineManager to solve token.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Engine implements Runnable, Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The EnginePanel which displays Engine status.
     */
    private final EnginePanel enginePanel;

    /**
     * The EngineManager which manages Engine.
     */
    private final EngineManager engineManager;

    /**
     * The list of tokens to solve.
     */
    private LocalToken localToken;

    /**
     * The error String containing error which appears during solving process.
     */
    private String error = "";

    /**
     * Creates a new Engine. Its role is to solve tokens.
     *
     * @param enginePanel   the EnginePanel which displays its status.
     * @param engineManager the EngineManager which manages Engine.
     * @param token         the LocalToken to be solved by the engine.
     */
    public Engine(final EnginePanel enginePanel, final EngineManager engineManager, final LocalToken token) {
        Objects.requireNonNull(token);
        this.enginePanel = enginePanel;
        this.engineManager = engineManager;
        this.localToken = token;
    }

    /**
     * Starts the Thread.
     */
    public void start() {
        Thread worker = new Thread(this);
        worker.start();
        System.out.println("[Engine] start Engine with id " + enginePanel.getId() + " on thread " + worker.getName());
    }

    /**
     * The process of the Engine.
     */
    public void run() {
        final JProgressBar progressBar = this.enginePanel.getProgressBar();
        try {
            this.error = "";
            this.enginePanel.getEngineLabel().setText(this.localToken.toString());
            this.enginePanel.getCirclePanel().setColor(Color.ORANGE);
            this.enginePanel.getCirclePanel().repaint();

            final int timeout = this.localToken.getPlanner().getStateSpaceStrategies().size()
                    * (this.localToken.getPlanner().getStateSpaceStrategies().get(0).getTimeout() / 1000);
            progressBar.setValue(0);
            progressBar.setMaximum(timeout);
            progressBar.setString(progressBar.getValue() + "/" + timeout);
            final Timer timer = new Timer(1000, (ActionEvent evt) -> {
                progressBar.setValue(progressBar.getValue() + 1);
                progressBar.setString(progressBar.getValue() + "/" + timeout);
            });

            timer.start();
            this.localToken.setSolved(resolve(this.localToken));
            this.localToken.setError(this.error);
            timer.stop();

        } catch (IOException e) {
            Thread.currentThread().interrupt();
            System.err.println(e.getMessage());
        }
        this.engineManager.decreaseNumberEngineRunning();
        this.enginePanel.exit();
        System.out.println("[Engine] Engine with id " + enginePanel.getId() + " stop");
    }

    /**
     * Resolves the token.
     *
     * @param token the token to solve.
     * @return true if the token is solved. False otherwise.
     * @throws IOException if there is issues with PDDL files.
     */
    private boolean resolve(final LocalToken token) throws IOException {
        if (token.isRunnable()) {
            System.out.println("[Engine " + this.enginePanel.getId() + "] Token " + token.toString() + " runnable");
            final Statistics statistics = new Statistics();
            final ProblemFactory factory = new ProblemFactory();

            long begin = System.currentTimeMillis();
            ErrorManager errorManager = factory.parse(token.getDomainFile(), token.getProblemFile());
            if (!errorManager.isEmpty()) {
                if (!errorManager.getMessages(token.getDomainFile()).isEmpty()) {
                    this.error = this.error.concat("Error in " + token.getDomainFileName() + "\n");
                    for (Message message : errorManager.getMessages(token.getDomainFile())) {
                        this.error = this.error.concat(message.getContent() + "\n");
                    }
                }
                if (!errorManager.getMessages(token.getProblemFile()).isEmpty()) {
                    this.error = this.error.concat("Error in " + token.getProblemFileName() + "\n");
                    for (Message message : errorManager.getMessages(token.getProblemFile())) {
                        this.error = this.error.concat(message.getContent() + "\n");
                    }
                }
                System.out.println("[Engine " + this.enginePanel.getId() + "] Error during parsing Token " + token);
                return false;
            }
            System.out.println("[Engine " + this.enginePanel.getId() + "] Parsing Token " + token);
            statistics.setTimeToParseInSeconds((System.currentTimeMillis() - begin) / 1000.0);

            final CodedProblem pb;
            try {
                begin = System.currentTimeMillis();
                try {
                    System.out.println("[Engine " + this.enginePanel.getId() + "] Encoding problem");
                    pb = factory.encode();
                } catch (IllegalArgumentException e) {
                    this.error = ("Error during encoding process.\n Check the :requirements part of the domain !");
                    System.err.println(e.getMessage());
                    return false;
                }
                if (pb != null) {
                    token.setCodedProblem(pb);
                    statistics.setTimeToEncodeInSeconds((System.currentTimeMillis() - begin) / 1000.0);
                    statistics.setNumberOfActions(pb.getOperators().size());
                    statistics.setNumberOfFluents(pb.getRelevantFacts().size());
                    statistics.setMemoryForProblemInMBytes(MemoryAgent.getDeepSizeOf(pb) / (1024.0 * 1024.0));
                    if (!pb.isSolvable()) {
                        this.error = ("Goal can be simplified to FALSE.\n"
                                + "No search will solve it !");
                        return false;
                    }

                    begin = System.currentTimeMillis();
                    final StateSpacePlanner planner = token.getPlanner();
                    System.out.println("[Engine " + this.enginePanel.getId() + "] Solving problem with "
                            + planner.toString() + " and " + planner.getStateSpaceStrategies().get(0).toString());
                    final Plan plan = planner.search(pb);
                    statistics.setTimeToPlanInSeconds((System.currentTimeMillis() - begin) / 1000.0);
                    statistics.setMemoryUsedToSearchInMBytes(planner.getStatistics()
                            .getMemoryUsedToSearch() / (1024.0 * 1024.0));

                    token.setResult(statistics, plan);
                    if (plan != null) {
                        System.out.println("[Engine " + this.enginePanel.getId() + "] Plan found (cost: "
                                + plan.cost() + " | depth: " + plan.size());
                        statistics.setCost(plan.cost());
                        statistics.setDepth(plan.size());
                        return true;
                    } else {
                        this.error = ("No plan found !");
                        statistics.setCost(0);
                        statistics.setDepth(0);
                        return false;
                    }
                } else {
                    this.error = ("Encoding problem failed !");
                    return false;
                }
            } catch (NullPointerException e) {
                this.error = ("Error during solving process.\n Check the problem.pddl file !");
                System.err.println(e.getMessage());
                return false;
            }
        } else {
            this.error = ("Domain or Problem not defined !");
            return false;
        }
    }
}
