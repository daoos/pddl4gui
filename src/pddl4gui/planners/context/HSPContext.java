package pddl4gui.planners.context;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.hsp.HSP;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.planners.Planner;
import pddl4gui.planners.PlannerFactory;

public class HSPContext implements Planner {

    private HSP hsp;

    public boolean isAnytime() {
        return false;
    }

    public HSPContext() {
        hsp = new HSP();
        this.setHSP(PlannerFactory.getHspDefaultHeuristic(),
                PlannerFactory.getHspDefaultWeight(), PlannerFactory.getPlannerDefaultTimeout());
    }

    public HSPContext(Heuristic.Type heuristic, double weight, double timeout) {
        hsp = new HSP();
        this.setHSP(heuristic, weight, timeout);
    }

    private void setHSP(Heuristic.Type heuristic, double weight, double timeout) {
        hsp.setHeuristicType(heuristic);
        hsp.setWeight(weight);
        hsp.setTimeOut((int) timeout);
    }

    public Type getType(){
        return Type.HSP;
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
}
