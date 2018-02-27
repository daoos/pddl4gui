package pddl4gui.context.planner;

/*import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.FFAnytime;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.util.Plan;

import java.util.Vector;

import static fr.uga.pddl4j.planners.ff.FF.DEFAULT_HEURISTIC;
import static fr.uga.pddl4j.planners.ff.FF.DEFAULT_WEIGHT;

public class FFAnytimeContext implements Planner {

    private FFAnytime ffAnytime;

    public boolean isAnytime() {
        return true;
    }

    public FFAnytimeContext() {
        ffAnytime = new FFAnytime();
        this.setFFAnytime(DEFAULT_HEURISTIC, DEFAULT_WEIGHT, DEFAULT_TIMEOUT);
    }

    public FFAnytimeContext(Heuristic.Type heuristic, double weight, double timeout) {
        ffAnytime = new FFAnytime();
        this.setFFAnytime(heuristic, weight, timeout);
    }

    public void setFFAnytime(Heuristic.Type heuristic, double weight, double timeout) {
        ffAnytime.setHeuristicType(heuristic);
        ffAnytime.setWeight(weight);
        ffAnytime.setTimeOut((int) timeout);
    }

    public Statistics getStatistics() {
        return ffAnytime.getStatistics();
    }

    public Plan search(CodedProblem problem) {
        return ffAnytime.search(problem);
    }

    public Vector<Node> getAnytimeSolutions() {
        return ffAnytime.getSolutionNodes();
    }
}
*/