package pddl4gui.context.planner;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ehc.EHC;
import fr.uga.pddl4j.util.Plan;

import static fr.uga.pddl4j.planners.ehc.EHC.DEFAULT_HEURISTIC;
import static fr.uga.pddl4j.planners.ehc.EHC.DEFAULT_WEIGHT;

public class EHCContext implements Planner {

    private EHC ehc;

    public EHCContext() {
        ehc = new EHC();
        this.setEHC(DEFAULT_HEURISTIC, DEFAULT_WEIGHT, DEFAULT_TIMEOUT);
    }

    public EHCContext(Heuristic.Type heuristic, double weight, double timeout) {
        ehc = new EHC();
        this.setEHC(heuristic, weight, timeout);
    }

    public void setEHC(Heuristic.Type heuristic, double weight, double timeout){
        ehc.setHeuristicType(heuristic);
        ehc.setWeight(weight);
        ehc.setTimeOut((int) timeout);
    }

    public Statistics getStatistics(){
        return ehc.getStatistics();
    }

    public Plan search(CodedProblem problem){
        return ehc.search(problem);
    }
}
