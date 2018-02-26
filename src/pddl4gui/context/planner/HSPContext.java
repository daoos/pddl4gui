package pddl4gui.context.planner;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.planners.hsp.HSP;
import fr.uga.pddl4j.util.Plan;

import java.util.LinkedList;

import static fr.uga.pddl4j.planners.hsp.HSP.DEFAULT_HEURISTIC;
import static fr.uga.pddl4j.planners.hsp.HSP.DEFAULT_WEIGHT;

public class HSPContext implements Planner {

    private HSP hsp;

    public boolean isAnytime() {
        return false;
    }

    public HSPContext() {
        hsp = new HSP();
        this.setHSP(DEFAULT_HEURISTIC, DEFAULT_WEIGHT, DEFAULT_TIMEOUT);
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

    public Statistics getStatistics() {
        return hsp.getStatistics();
    }

    public Plan search(CodedProblem problem) {
        return hsp.search(problem);
    }

    public LinkedList<Node> getAnytimeSolutions() {
        return new LinkedList<>();
    }
}
