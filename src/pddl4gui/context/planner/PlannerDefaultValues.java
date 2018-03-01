package pddl4gui.context.planner;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;

public class PlannerDefaultValues {

    public static Heuristic.Type EHC_DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.hc.EHC.DEFAULT_HEURISTIC;
    //public static Heuristic.Type HCANYTIME_DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.hc.HCAnytime.DEFAULT_HEURISTIC;
    public static Heuristic.Type FF_DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.ff.FF.DEFAULT_HEURISTIC;
    //public static Heuristic.Type FFANYTIME__DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.ff.FFAnytime.DEFAULT_HEURISTIC;
    public static Heuristic.Type HSP_DEFAULT_HEURISTIC = fr.uga.pddl4j.planners.hsp.HSP.DEFAULT_HEURISTIC;

    public static double EHC_DEFAULT_WEIGHT = fr.uga.pddl4j.planners.hc.EHC.DEFAULT_WEIGHT;
    //public static double HCANYTIME_DEFAULT_WEIGHT = fr.uga.pddl4j.planners.hc.HCAnytime.DEFAULT_WEIGHT;
    public static double FF_DEFAULT_WEIGHT = fr.uga.pddl4j.planners.ff.FF.DEFAULT_WEIGHT;
    //public static double FFANYTIME__DEFAULT_WEIGHT = fr.uga.pddl4j.planners.ff.FFAnytime.DEFAULT_WEIGHT;
    public static double HSP_DEFAULT_WEIGHT = fr.uga.pddl4j.planners.hsp.HSP.DEFAULT_WEIGHT;

    public static int PLANNER_DEFAULT_TIMEOUT = Planner.DEFAULT_TIMEOUT;

    private PlannerDefaultValues() {}

}
