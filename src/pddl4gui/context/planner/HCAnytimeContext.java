package pddl4gui.context.planner;

/*import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.planners.hc.HCAnytime;
import fr.uga.pddl4j.util.Plan;

import java.util.Vector;

import static pddl4gui.context.planner.PlannerDefaultValues.HCANYTIME_DEFAULT_HEURISTIC;
import static pddl4gui.context.planner.PlannerDefaultValues.HCANYTIME_DEFAULT_WEIGHT;
import static pddl4gui.context.planner.PlannerDefaultValues.PLANNER_DEFAULT_TIMEOUT;

public class HCAnytimeContext implements Planner {

    private HCAnytime hcAnytime;

    public boolean isAnytime() {
        return true;
    }

    public HCAnytimeContext() {
        hcAnytime = new HCAnytime();
        this.setHCAnytime(HCANYTIME_DEFAULT_HEURISTIC, HCANYTIME_DEFAULT_WEIGHT, PLANNER_DEFAULT_TIMEOUT);
    }

    public HCAnytimeContext(Heuristic.Type heuristic, double weight, double timeout) {
        hcAnytime = new HCAnytime();
        this.setHCAnytime(heuristic, weight, timeout);
    }

    public void setHCAnytime(Heuristic.Type heuristic, double weight, double timeout) {
        hcAnytime.setHeuristicType(heuristic);
        hcAnytime.setWeight(weight);
        hcAnytime.setTimeOut((int) timeout);
    }

    public int getTimeOut() {
        return hcAnytime.getTimeout();
    }

    public Statistics getStatistics() {
        return hcAnytime.getStatistics();
    }

    public Plan search(CodedProblem problem) {
        return hcAnytime.search(problem);
    }

    public Vector<Node> getAnytimeSolutions() {
        return hcAnytime.getSolutionNodes();
    }
}
*/