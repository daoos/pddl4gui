package pddl4gui.planners.context;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.FFAnytime;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.planners.PlannerAnytime;
import pddl4gui.planners.PlannerFactory;

import java.util.Vector;

public class FFAnytimeContext implements PlannerAnytime {

    private FFAnytime ffAnytime;

    public boolean isAnytime() {
        return true;
    }

    public FFAnytimeContext() {
        ffAnytime = new FFAnytime();
        this.setFFAnytime(PlannerFactory.getFfanytime_defaultHeuristic(),
                PlannerFactory.getFfanytime_defaultWeight(), PlannerFactory.getPlannerDefaultTimeout());
    }

    public FFAnytimeContext(Heuristic.Type heuristic, double weight, double timeout) {
        ffAnytime = new FFAnytime();
        this.setFFAnytime(heuristic, weight, timeout);
    }

    private void setFFAnytime(Heuristic.Type heuristic, double weight, double timeout) {
        ffAnytime.setHeuristicType(heuristic);
        ffAnytime.setWeight(weight);
        ffAnytime.setTimeOut((int) timeout);
    }

    public Type getType() {
        return Type.FFAnytime;
    }

    public int getTimeOut() {
        return ffAnytime.getTimeout();
    }

    public Statistics getStatistics() {
        return ffAnytime.getStatistics();
    }

    public Plan search(CodedProblem problem) {
        return ffAnytime.search(problem);
    }

    public Vector<Node> getAnytimeSolutions() {
        return ffAnytime.getSolutionNodes();
    }
}
