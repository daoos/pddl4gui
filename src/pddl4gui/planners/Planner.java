package pddl4gui.planners;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.util.Plan;

public interface Planner {

    enum Type {
        EHC,
        HSP,
        FF,
        FFAnytime,
        HCAnytime
    }

    boolean isAnytime();

    Type getType();

    int getTimeOut();

    Statistics getStatistics();

    Plan search(CodedProblem problem);
}
