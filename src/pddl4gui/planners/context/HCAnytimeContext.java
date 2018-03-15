package pddl4gui.planners.context;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.planners.hc.HCAnytime;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.planners.PlannerAnytime;
import pddl4gui.planners.PlannerFactory;

import java.util.Vector;

public class HCAnytimeContext implements PlannerAnytime {

    private HCAnytime hcAnytime;

    public boolean isAnytime() {
        return true;
    }

    public HCAnytimeContext() {
        hcAnytime = new HCAnytime();
        this.setHCAnytime(PlannerFactory.getHcanytimeDefaultHeuristic(),
                PlannerFactory.getHcanytimeDefaultWeight(), PlannerFactory.getPlannerDefaultTimeout());
    }

    public HCAnytimeContext(Heuristic.Type heuristic, double weight, double timeout) {
        hcAnytime = new HCAnytime();
        this.setHCAnytime(heuristic, weight, timeout);
    }

    private void setHCAnytime(Heuristic.Type heuristic, double weight, double timeout) {
        hcAnytime.setHeuristicType(heuristic);
        hcAnytime.setWeight(weight);
        hcAnytime.setTimeOut((int) timeout);
    }

    public Type getType() {
        return Type.HCAnytime;
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
