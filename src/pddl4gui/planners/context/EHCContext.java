package pddl4gui.planners.context;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.hc.EHC;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.planners.Planner;
import pddl4gui.planners.PlannerDefault;

public class EHCContext implements Planner {

    private EHC ehc;

    public boolean isAnytime() {
        return false;
    }

    public EHCContext() {
        ehc = new EHC();
        this.setEHC(PlannerDefault.getEhcDefaultHeuristic(),
                PlannerDefault.getEhcDefaultWeight(), PlannerDefault.getPlannerDefaultTimeout());
    }

    public EHCContext(Heuristic.Type heuristic, double weight, double timeout) {
        ehc = new EHC();
        this.setEHC(heuristic, weight, timeout);
    }

    private void setEHC(Heuristic.Type heuristic, double weight, double timeout) {
        ehc.setHeuristicType(heuristic);
        ehc.setWeight(weight);
        ehc.setTimeOut((int) timeout);
    }

    public Type getType(){
        return Type.EHC;
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
}
