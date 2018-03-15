package pddl4gui.engine;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.encoding.JsonAdapter;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.parser.Message;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.util.MemoryAgent;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.gui.Solver;
import pddl4gui.gui.panel.EngineStatusPanel;
import pddl4gui.planners.Planner;
import pddl4gui.token.Result;
import pddl4gui.token.Statistics;
import pddl4gui.token.Token;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.LinkedList;

public class Engine extends Thread {

    private Solver solver;

    private LinkedList<Token> tokenList;

    private String error = "";

    private int refresh;

    public void setSolver(Solver solver) {
        this.solver = solver;
    }

    public LinkedList<Token> getTokenList() {
        return tokenList;
    }

    public void addToken(Token token) {
        tokenList.addLast(token);
    }

    public Engine(int refresh) {
        tokenList = new LinkedList<>();
        this.refresh = refresh;
    }

    @Override
    public void run() {
        final EngineStatusPanel engineStatusPanel = solver.getEngineStatusPanel();
        final JProgressBar progressBar = engineStatusPanel.getProgressBar();
        while (solver.isVisible()) {
            try {
                if (tokenList.size() > 0) {
                    error = "";
                    final Token token = tokenList.pop();
                    engineStatusPanel.getEngineLabel().setText("solving " + token);
                    engineStatusPanel.getCirclePanel().setColor(Color.ORANGE);
                    engineStatusPanel.getCirclePanel().repaint();

                    progressBar.setValue(1);
                    final int timeout = token.getPlanner().getTimeOut();
                    progressBar.setMaximum(timeout);
                    final Timer timer = new Timer(1000, (ActionEvent evt) -> {
                        progressBar.setString(progressBar.getValue() + "/" + timeout);
                        progressBar.setValue(progressBar.getValue() + 1);
                    });

                    timer.start();
                    token.setSolved(resolve(token));
                    token.setError(error);
                    timer.stop();
                } else {
                    progressBar.setValue(0);
                    progressBar.setString("Time out");
                    engineStatusPanel.getEngineLabel().setText("waiting for token");
                    engineStatusPanel.getCirclePanel().setColor(Color.GREEN);
                    engineStatusPanel.getCirclePanel().repaint();
                }
                engineStatusPanel.setTokensRemaining(tokenList.size());
                sleep(refresh);
            } catch (InterruptedException | IOException e) {
                engineStatusPanel.getEngineLabel().setText("Engine crash, restart it !");
                engineStatusPanel.getCirclePanel().setColor(Color.RED);
                engineStatusPanel.getCirclePanel().repaint();
                engineStatusPanel.getInitButton().setEnabled(true);
                e.printStackTrace();
                break;
            }
        }
    }

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
                statistics.setTimeToEncodeInSeconds((System.currentTimeMillis() - begin) / 1000.0);
                statistics.setNumberOfActions(pb.getOperators().size());
                statistics.setNumberOfFluents(pb.getRelevantFacts().size());
                statistics.setMemoryForProblemInMBytes(MemoryAgent.deepSizeOf(pb) / (1024.0 * 1024.0));
                if (!pb.isSolvable()) {
                    error = ("Goal can be simplified to FALSE.\n"
                            + "No search will solve it !");
                    return true;
                }

                begin = System.currentTimeMillis();
                final Planner planner = token.getPlanner();
                final Plan plan = planner.search(pb);
                statistics.setTimeToPlanInSeconds((System.currentTimeMillis() - begin) / 1000.0);
                statistics.setMemoryUsedToSearchInMBytes(planner.getStatistics().getMemoryUsedToSearch() / (1024.0 * 1024.0));

                if (plan != null) {
                    final JsonAdapter toJson = new JsonAdapter(pb);
                    statistics.setCost(plan.cost());
                    statistics.setDepth(plan.size());
                    token.setResult(new Result(statistics, plan, pb.toString(plan), toJson.toJsonString(plan)));
                    return true;
                } else {
                    error = ("Plan is null !");
                    statistics.setCost(0);
                    statistics.setDepth(0);
                    token.setResult(new Result(statistics, null, "Time out !", "Time out !"));
                    return true;
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
