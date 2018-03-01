package pddl4gui.context.planner;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;

public class PlannerDefault {

    private static Heuristic.Type EHC_DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.hc.EHC.DEFAULT_HEURISTIC;
    //private static Heuristic.Type HCANYTIME_DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.hc.HCAnytime.DEFAULT_HEURISTIC;
    private static Heuristic.Type FF_DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.ff.FF.DEFAULT_HEURISTIC;
    //private static Heuristic.Type FFANYTIME__DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.ff.FFAnytime.DEFAULT_HEURISTIC;
    private static Heuristic.Type HSP_DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.hsp.HSP.DEFAULT_HEURISTIC;

    private static double EHC_DEFAULT_WEIGHT = fr.uga.pddl4j.planners.hc.EHC.DEFAULT_WEIGHT;
    //private static double HCANYTIME_DEFAULT_WEIGHT = fr.uga.pddl4j.planners.hc.HCAnytime.DEFAULT_WEIGHT;
    private static double FF_DEFAULT_WEIGHT = fr.uga.pddl4j.planners.ff.FF.DEFAULT_WEIGHT;
    //private static double FFANYTIME__DEFAULT_WEIGHT = fr.uga.pddl4j.planners.ff.FFAnytime.DEFAULT_WEIGHT;
    private static double HSP_DEFAULT_WEIGHT = fr.uga.pddl4j.planners.hsp.HSP.DEFAULT_WEIGHT;

    private static int PLANNER_DEFAULT_TIMEOUT = Planner.DEFAULT_TIMEOUT;

    public static Heuristic.Type getEhcDefaultHeuristic() {
        return EHC_DEFAULT_HEURISTIC;
    }

    public static Heuristic.Type getFfDefaultHeuristic() {
        return FF_DEFAULT_HEURISTIC;
    }

    public static Heuristic.Type getHspDefaultHeuristic() {
        return HSP_DEFAULT_HEURISTIC;
    }

    public static double getEhcDefaultWeight() {
        return EHC_DEFAULT_WEIGHT;
    }

    public static double getFfDefaultWeight() {
        return FF_DEFAULT_WEIGHT;
    }

    public static double getHspDefaultWeight() {
        return HSP_DEFAULT_WEIGHT;
    }

    /*public static Heuristic.Type getHcanytimeDefaultHeuristic() {
        return HCANYTIME_DEFAULT_HEURISTIC;
    }

    public static Heuristic.Type getFfanytime_defaultHeuristic() {
        return FFANYTIME__DEFAULT_HEURISTIC;
    }

    public static double getHcanytimeDefaultWeight() {
        return HCANYTIME_DEFAULT_WEIGHT;
    }

    public static double getFfanytime_defaultWeight() {
        return FFANYTIME__DEFAULT_WEIGHT;
    }*/

    public static int getPlannerDefaultTimeout() {
        return PLANNER_DEFAULT_TIMEOUT;
    }

    private PlannerDefault() {}

}
