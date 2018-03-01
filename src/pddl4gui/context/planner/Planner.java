package pddl4gui.context.planner;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.planners.ff.Node;
import fr.uga.pddl4j.util.Plan;

import java.util.Vector;

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

    Vector<Node> getAnytimeSolutions();
}
