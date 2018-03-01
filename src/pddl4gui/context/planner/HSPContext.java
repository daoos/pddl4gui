package pddl4gui.context.planner;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.planners.hsp.HSP;
import fr.uga.pddl4j.util.Plan;

import java.util.Vector;

import static pddl4gui.context.planner.PlannerDefaultValues.HSP_DEFAULT_HEURISTIC;
import static pddl4gui.context.planner.PlannerDefaultValues.HSP_DEFAULT_WEIGHT;
import static pddl4gui.context.planner.PlannerDefaultValues.PLANNER_DEFAULT_TIMEOUT;

public class HSPContext implements Planner {

    private HSP hsp;

    public boolean isAnytime() {
        return false;
    }

    public HSPContext() {
        hsp = new HSP();
        this.setHSP(HSP_DEFAULT_HEURISTIC, HSP_DEFAULT_WEIGHT, PLANNER_DEFAULT_TIMEOUT);
    }

    public HSPContext(Heuristic.Type heuristic, double weight, double timeout) {
        hsp = new HSP();
        this.setHSP(heuristic, weight, timeout);
    }

    public void setHSP(Heuristic.Type heuristic, double weight, double timeout) {
        hsp.setHeuristicType(heuristic);
        hsp.setWeight(weight);
        hsp.setTimeOut((int) timeout);
    }

    public int getTimeOut() {
        return hsp.getTimeout();
    }

    public Statistics getStatistics() {
        return hsp.getStatistics();
    }

    public Plan search(CodedProblem problem) {
        return hsp.search(problem);
    }

    public Vector<Node> getAnytimeSolutions() {
        return new Vector<>();
    }
}
