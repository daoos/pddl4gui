package pddl4gui.token;

import fr.uga.pddl4j.util.Plan;

public class Result {
    final private Statistics statistics;
    final private Plan solutionPlan;
    final private String solutionString;
    final private String solutionJSON;

    public Statistics getStatistics() {
        return statistics;
    }

    public Plan getSolutionPlan() {
        return solutionPlan;
    }

    public String getSolutionString() {
        return solutionString;
    }

    public String getSolutionJSON() {
        return solutionJSON;
    }

    public Result(Statistics statistics, Plan solutionPlan, String solutionString, String solutionJSON) {
        this.statistics = statistics;
        this.solutionPlan = solutionPlan;
        this.solutionString = solutionString;
        this.solutionJSON = solutionJSON;
    }
}
