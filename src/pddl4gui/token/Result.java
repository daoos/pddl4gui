package pddl4gui.token;

import fr.uga.pddl4j.util.Plan;

public class Result {
    private Statistics statistics;
    private Plan solutionPlan;
    private String solutionString;
    private String solutionJSON;

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
