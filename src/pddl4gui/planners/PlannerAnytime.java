package pddl4gui.planners;

import fr.uga.pddl4j.planners.ff.Node;

import java.util.Vector;

public interface PlannerAnytime extends Planner {

    Vector<Node> getAnytimeSolutions();
}