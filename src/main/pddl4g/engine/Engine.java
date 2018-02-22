package main.pddl4g.engine;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.encoding.JsonAdapter;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.parser.Message;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.util.MemoryAgent;
import fr.uga.pddl4j.util.Plan;
import main.pddl4g.Pddl4G;
import main.pddl4g.context.planner.Planner;
import main.pddl4g.gui.panel.EngineStatusPanel;
import main.pddl4g.model.Result;
import main.pddl4g.model.Statistics;
import main.pddl4g.model.Token;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class Engine extends Thread{

    private Pddl4G pddl4G;

    private LinkedList<Token> tokenList;

    private String error = "";

    public LinkedList<Token> getTokenList() {
        return tokenList;
    }

    public Engine(Pddl4G pddl4G) {
        tokenList = new LinkedList<>();
        this.pddl4G = pddl4G;
    }

    @Override
    public void run() {
        final EngineStatusPanel engineStatusPanel = pddl4G.getSolver().getEngineStatusPanel();
        while (pddl4G.getSolver().isVisible()) {
            try {
                if(tokenList.size() > 0){
                    engineStatusPanel.getEngineLabel().setText("solving token");
                    engineStatusPanel.getCirclePanel().setColor(Color.ORANGE);
                    engineStatusPanel.getCirclePanel().repaint();
                    final Token token = tokenList.pop();
                    pddl4G.getSolver().displayResult(this.resolve(token), error);
                }
                else {
                    engineStatusPanel.getEngineLabel().setText("waiting for token");
                    engineStatusPanel.getCirclePanel().setColor(Color.GREEN);
                    engineStatusPanel.getCirclePanel().repaint();
                }
                sleep(1000);
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
                if(!errorManager.getMessages(token.getDomainFile()).isEmpty()) {
                    error = error.concat("Error in " + token.getDomainFile() + "\n");
                    for (Message message : errorManager.getMessages(token.getDomainFile())) {
                        error = error.concat(message.getContent() + "\n");
                    }
                }
                if(!errorManager.getMessages(token.getProblemFile()).isEmpty()) {
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
