package pddl4gui.context.planner;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.util.Plan;

import java.util.LinkedList;

public interface Planner {

    int DEFAULT_TIMEOUT = fr.uga.pddl4j.planners.Planner.DEFAULT_TIMEOUT;

    enum Type {
        EHC,
        HSP,
        FF,
        FFAnytime,
        HCAnytime
    }

    boolean isAnytime();

    Statistics getStatistics();

    Plan search(CodedProblem problem);

    LinkedList<Node> getAnytimeSolutions();
}
