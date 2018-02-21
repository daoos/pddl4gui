package solver.context.planner;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.util.Plan;

public interface Planner {

    int DEFAULT_TIMEOUT = fr.uga.pddl4j.planners.Planner.DEFAULT_TIMEOUT;

    enum Type {
        EHC,
        HSP,
        FF
    }

    Statistics getStatistics();

    Plan search(CodedProblem problem);
}
