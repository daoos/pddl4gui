package pddl4gui.token;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.encoding.JsonAdapter;
import fr.uga.pddl4j.util.BitOp;
import fr.uga.pddl4j.util.Plan;

import java.io.Serializable;

/**
 * This class implements the Result class of <code>PDDL4GUI</code>.
 * This object contains all results obtained after token solving process.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Result implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Statistics object which contains all the statistics of the solving process.
     */
    final private Statistics statistics;

    /**
     * The solution Plan found for a token.
     */
    final private Plan solutionPlan;

    /**
     * The CodedProblem associated to the Result object.
     */
    final private CodedProblem codedProblem;

    /**
     * Returns the Statistics object associated to the Result.
     *
     * @return the Statistics object associated to the Result.
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Returns the solution Plan as a String object.
     *
     * @return the solution Plan as a String object.
     */
    public String getSolutionString() {
        return this.codedProblem.toString(solutionPlan);
    }

    /**
     * Returns a detailed solution Plan as a String object.
     *
     * @return a detailed solution Plan as a String object.
     */
    public String getSolutionStringDetailed() {
        return detailedPlan(solutionPlan);
    }

    /**
     * Returns a JSON solution Plan as a String object.
     *
     * @return a JSON solution Plan as a String object.
     */
    public String getSolutionJSON() {
        final JsonAdapter toJson = new JsonAdapter(this.codedProblem);
        return toJson.toJsonString(solutionPlan);
    }

    /**
     * Creates a new Result object.
     *
     * @param statistics   the Statistics object which store statistical information about planner performances.
     * @param codedProblem the CodedProblem associated to the Result.
     * @param solutionPlan the Plan found by the parser.
     */
    public Result(Statistics statistics, CodedProblem codedProblem, Plan solutionPlan) {
        this.statistics = statistics;
        this.codedProblem = codedProblem;
        this.solutionPlan = solutionPlan;
    }

    /**
     * Returns a detailed version of a solution Plan.
     *
     * @param plan the solution Plan.
     * @return a detailed version of a solution Plan.
     */
    private String detailedPlan(final Plan plan) {
        int max = Integer.MIN_VALUE;
        for (Integer t : plan.timeSpecifiers()) {
            for (BitOp a : plan.getActionSet(t)) {
                int length = this.codedProblem.toShortString(a).length();
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
                        str.append(String.format("%0" + timeSpecifierSize + "d: (%" + actionSize + "s) [%4.2f]%n",
                                time, this.codedProblem.toShortString(a), ((float) a.getCost())))));
        return str.toString();
    }
}
