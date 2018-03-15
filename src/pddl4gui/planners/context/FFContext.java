package pddl4gui.planners.context;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.FF;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.planners.Planner;
import pddl4gui.planners.PlannerFactory;

public class FFContext implements Planner {

    private FF ff;

    public boolean isAnytime() {
        return false;
    }

    public FFContext() {
        ff = new FF();
        this.setFF(PlannerFactory.getFfDefaultHeuristic(),
                PlannerFactory.getFfDefaultWeight(), PlannerFactory.getPlannerDefaultTimeout());
    }

    public FFContext(Heuristic.Type heuristic, double weight, double timeout) {
        ff = new FF();
        this.setFF(heuristic, weight, timeout);
    }

    private void setFF(Heuristic.Type heuristic, double weight, double timeout) {
        ff.setHeuristicType(heuristic);
        ff.setWeight(weight);
        ff.setTimeOut((int) timeout);
    }

    public Type getType() {
        return Type.FF;
    }

    public int getTimeOut() {
        return ff.getTimeout();
    }

    public Statistics getStatistics() {
        return ff.getStatistics();
    }

    public Plan search(CodedProblem problem) {
        return ff.search(problem);
    }
}
