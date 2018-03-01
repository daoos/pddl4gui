package pddl4gui.context.planner;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.planners.hc.EHC;
import fr.uga.pddl4j.util.Plan;

import java.util.Vector;

import static pddl4gui.context.planner.PlannerDefaultValues.EHC_DEFAULT_HEURISTIC;
import static pddl4gui.context.planner.PlannerDefaultValues.EHC_DEFAULT_WEIGHT;
import static pddl4gui.context.planner.PlannerDefaultValues.PLANNER_DEFAULT_TIMEOUT;

public class EHCContext implements Planner {

    private EHC ehc;

    public boolean isAnytime() {
        return false;
    }

    public EHCContext() {
        ehc = new EHC();
        this.setEHC(EHC_DEFAULT_HEURISTIC, EHC_DEFAULT_WEIGHT, PLANNER_DEFAULT_TIMEOUT);
    }

    public EHCContext(Heuristic.Type heuristic, double weight, double timeout) {
        ehc = new EHC();
        this.setEHC(heuristic, weight, timeout);
    }

    public void setEHC(Heuristic.Type heuristic, double weight, double timeout) {
        ehc.setHeuristicType(heuristic);
        ehc.setWeight(weight);
        ehc.setTimeOut((int) timeout);
    }

    public int getTimeOut() {
        return ehc.getTimeout();
    }

    public Statistics getStatistics() {
        return ehc.getStatistics();
    }

    public Plan search(CodedProblem problem) {
        return ehc.search(problem);
    }

    public Vector<Node> getAnytimeSolutions() {
        return new Vector<>();
    }
}
