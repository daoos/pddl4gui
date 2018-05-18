package pddl4gui.token;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.encoding.JsonAdapter;
import fr.uga.pddl4j.util.BitOp;
import fr.uga.pddl4j.util.Plan;

public class Result {
    final private Statistics statistics;
    final private Plan solutionPlan;
    final private CodedProblem codedProblem;

    public Statistics getStatistics() {
        return statistics;
    }

    public String getSolutionString() {
        return solutionString(codedProblem, solutionPlan);
    }

    public String getSolutionStringDetailed() {
        return detailedPlan(codedProblem,solutionPlan);
    }

    public String getSolutionJSON() {
        return solutionJSON(codedProblem, solutionPlan);
    }

    public Result(Statistics statistics, CodedProblem codedProblem, Plan solutionPlan) {
        this.statistics = statistics;
        this.codedProblem = codedProblem;
        this.solutionPlan = solutionPlan;
    }

    private String solutionString(CodedProblem codedProblem, final Plan plan) {
        return codedProblem.toString(plan);
    }

    private String detailedPlan(CodedProblem codedProblem, final Plan plan) {
        int max = Integer.MIN_VALUE;
        for (Integer t : plan.timeSpecifiers()) {
            for (BitOp a : plan.getActionSet(t)) {
                int length = codedProblem.toShortString(a).length();
                if (max < length) {
                    max = length;
                }
            }
        }
        final int actionSize = max;
        final int timeSpecifierSize = (int) Math.log10(plan.timeSpecifiers().size()) + 1;

        final StringBuilder str = new StringBuilder();
        plan.timeSpecifiers().forEach(time ->
                plan.getActionSet(time).forEach(a ->
                        str.append(String.format("%0" + timeSpecifierSize + "d: (%" + actionSize + "s) [D: %d, C: %8.2f]%n",
                                time, codedProblem.toShortString(a), ((int) a.getDuration()), a.getCost()))));
        return str.toString();
    }

    private String solutionJSON(CodedProblem codedProblem, final Plan plan) {
        final JsonAdapter toJson = new JsonAdapter(codedProblem);
        return toJson.toJsonString(plan);
    }
}
