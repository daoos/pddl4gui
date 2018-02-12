package solver.model;

import fr.uga.pddl4j.util.Plan;

public class Result {
    private Statistics statistics;
    private Plan solutionPlan;
    private String solutionString;
    private String solutionJSON;
    private boolean isSolved = false;

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

    public boolean isSolved() {
        return isSolved;
    }

    public Result(Statistics statistics, Plan solutionPlan, String solutionString, String solutionJSON) {
        this.statistics = statistics;
        this.solutionPlan = solutionPlan;
        this.solutionString = solutionString;
        this.solutionJSON = solutionJSON;
        this.isSolved = true;
    }

    public void resetResult() {
        this.statistics = new Statistics();
        this.solutionPlan = null;
        this.solutionString = "";
        this.solutionJSON = "";
        this.isSolved = false;
    }
}
