package pddl4gui.planners;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import pddl4gui.planners.context.EHCContext;
import pddl4gui.planners.context.FFAnytimeContext;
import pddl4gui.planners.context.FFContext;
import pddl4gui.planners.context.HCAnytimeContext;
import pddl4gui.planners.context.HSPContext;

public class PlannerFactory {
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
        return fr.uga.pddl4j.planners.Planner.DEFAULT_TIMEOUT;
    }

    static public Planner create(Planner.Type plannerType, Heuristic.Type heuristicType,
                                 double weight, double timeout) {
        Planner planner;

        switch (plannerType) {
            case FF:
                planner = new FFContext(heuristicType, weight, timeout);
                break;
            case FFAnytime:
                planner = new FFAnytimeContext(heuristicType, weight, timeout);
                break;
            case HSP:
                planner = new HSPContext(heuristicType, weight, timeout);
                break;
            case EHC:
                planner = new EHCContext(heuristicType, weight, timeout);
                break;
            case HCAnytime:
                planner = new HCAnytimeContext(heuristicType, weight, timeout);
                break;
            default:
                planner = new HSPContext(heuristicType, weight, timeout);
                break;
        }

        return planner;
    }

    private PlannerFactory() {
    }
}
