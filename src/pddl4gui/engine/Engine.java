package pddl4gui.engine;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.encoding.JsonAdapter;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.parser.Message;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.util.MemoryAgent;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.Pddl4Gui;
import pddl4gui.context.planner.Planner;
import pddl4gui.gui.panel.EngineStatusPanel;
import pddl4gui.token.Result;
import pddl4gui.token.Statistics;
import pddl4gui.token.Token;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class Engine extends Thread {

    private Pddl4Gui pddl4Gui;

    private LinkedList<Token> tokenList;

    private String error = "";

    private int refresh;

    public LinkedList<Token> getTokenList() {
        return tokenList;
    }

    public Engine(Pddl4Gui pddl4Gui, int refresh) {
        tokenList = new LinkedList<>();
        this.pddl4Gui = pddl4Gui;
        this.refresh = refresh;
    }

    @Override
    public void run() {
        final EngineStatusPanel engineStatusPanel = pddl4Gui.getSolver().getEngineStatusPanel();
        while (pddl4Gui.getSolver().isVisible()) {
            try {
                if (tokenList.size() > 0) {
                    final Token token = tokenList.pop();
                    engineStatusPanel.getEngineLabel().setText("solving " + token);
                    engineStatusPanel.getCirclePanel().setColor(Color.ORANGE);
                    engineStatusPanel.getCirclePanel().repaint();
                    token.setSolved(resolve(token));
                    token.setError(error);
                } else {
                    engineStatusPanel.getEngineLabel().setText("waiting for token");
                    engineStatusPanel.getCirclePanel().setColor(Color.GREEN);
                    engineStatusPanel.getCirclePanel().repaint();
                }
                sleep(refresh);
            } catch (InterruptedException | IOException e) {
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
                    error = error.concat("Error in " + token.getDomainFile() + "\n");
                    for (Message message : errorManager.getMessages(token.getDomainFile())) {
                        error = error.concat(message.getContent() + "\n");
                    }
                }
                if (!errorManager.getMessages(token.getProblemFile()).isEmpty()) {
                    error = error.concat("Error in " + token.getProblemFile() + "\n");
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
                return false;
            }
        } else {
            error = ("Domain or Problem not defined !");
            return false;
        }
    }
}
