package pddl4gui.planners;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import pddl4gui.planners.context.EHCContext;
import pddl4gui.planners.context.FFAnytimeContext;
import pddl4gui.planners.context.FFContext;
import pddl4gui.planners.context.HCAnytimeContext;
import pddl4gui.planners.context.HSPContext;

public class PlannerFactory {

    static public Planner create(Planner.Type plannerType, Heuristic.Type heuristicType,
                                 double weight, double timeout) {
        Planner planner;

        switch (plannerType) {
            case FF: planner = new FFContext(heuristicType, weight, timeout);
                break;
            case FFAnytime: planner = new FFAnytimeContext(heuristicType, weight, timeout);
                break;
            case HSP: planner = new HSPContext(heuristicType, weight, timeout);
                break;
            case EHC: planner = new EHCContext(heuristicType, weight, timeout);
                break;
            case HCAnytime: planner = new HCAnytimeContext(heuristicType, weight, timeout);
                break;
            default: planner = new HSPContext(heuristicType, weight, timeout);
                break;
        }

        return planner;
    }

    private PlannerFactory() {}
}
