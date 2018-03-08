package pddl4gui.context.planner;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;

public class PlannerDefault {

    public static Heuristic.Type getEhcDefaultHeuristic() {
        return fr.uga.pddl4j.planners.hc.EHC.DEFAULT_HEURISTIC;
    }

    public static Heuristic.Type getFfDefaultHeuristic() {
        return fr.uga.pddl4j.planners.ff.FF.DEFAULT_HEURISTIC;
    }

    public static Heuristic.Type getHspDefaultHeuristic() {
        return fr.uga.pddl4j.planners.hsp.HSP.DEFAULT_HEURISTIC;
    }

    public static double getEhcDefaultWeight() {
        return fr.uga.pddl4j.planners.hc.EHC.DEFAULT_WEIGHT;
    }

    public static double getFfDefaultWeight() {
        return fr.uga.pddl4j.planners.ff.FF.DEFAULT_WEIGHT;
    }

    public static double getHspDefaultWeight() {
        return fr.uga.pddl4j.planners.hsp.HSP.DEFAULT_WEIGHT;
    }

    public static Heuristic.Type getHcanytimeDefaultHeuristic() {
        return fr.uga.pddl4j.planners.hc.HCAnytime.DEFAULT_HEURISTIC;
    }

    public static Heuristic.Type getFfanytime_defaultHeuristic() {
        return fr.uga.pddl4j.planners.ff.FFAnytime.DEFAULT_HEURISTIC;
    }

    public static double getHcanytimeDefaultWeight() {
        return fr.uga.pddl4j.planners.hc.HCAnytime.DEFAULT_WEIGHT;
    }

    public static double getFfanytime_defaultWeight() {
        return fr.uga.pddl4j.planners.ff.FFAnytime.DEFAULT_WEIGHT;
    }

    public static int getPlannerDefaultTimeout() {
        return Planner.DEFAULT_TIMEOUT;
    }

    private PlannerDefault() {}

}
