package pddl4gui.context.planner;

/*import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.FFAnytime;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.util.Plan;

import java.util.Vector;

public class FFAnytimeContext implements Planner {

    private FFAnytime ffAnytime;

    public boolean isAnytime() {
        return true;
    }

    public FFAnytimeContext() {
        ffAnytime = new FFAnytime();
        this.setFFAnytime(PlannerDefault.getFfanytime_defaultHeuristic(),
         PlannerDefault.getFfanytime_defaultWeight(), PlannerDefault.getPlannerDefaultTimeout());
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

    public Type getType(){
        return Type.FFANYTIME;
    }

    public int getTimeOut() {
        return ffAnytime.getTimeout();
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