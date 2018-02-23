package pddl4gui.context.planner;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.FF;
import fr.uga.pddl4j.util.Plan;

import static fr.uga.pddl4j.planners.ff.FF.DEFAULT_HEURISTIC;
import static fr.uga.pddl4j.planners.ff.FF.DEFAULT_WEIGHT;

public class FFContext implements Planner {

    private FF ff;

    public FFContext() {
        ff = new FF();
        this.setFF(DEFAULT_HEURISTIC, DEFAULT_WEIGHT, DEFAULT_TIMEOUT);
    }

    public FFContext(Heuristic.Type heuristic, double weight, double timeout) {
        ff = new FF();
        this.setFF(heuristic, weight, timeout);
    }

    public void setFF(Heuristic.Type heuristic, double weight, double timeout){
        ff.setHeuristicType(heuristic);
        ff.setWeight(weight);
        ff.setTimeOut((int) timeout);
    }

    public Statistics getStatistics(){
        return ff.getStatistics();
    }

    public Plan search(CodedProblem problem){
        return ff.search(problem);
    }
}
