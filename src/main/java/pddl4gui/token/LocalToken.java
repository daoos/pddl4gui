package pddl4gui.token;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.encoding.JsonAdapter;
import fr.uga.pddl4j.planners.statespace.StateSpacePlanner;
import fr.uga.pddl4j.planners.statespace.search.strategy.Node;
import fr.uga.pddl4j.util.Plan;
import pddl4gui.gui.tools.FileTools;

import java.io.File;
import javax.swing.DefaultListModel;

/**
 * This class implements the <code>LocalToken</code> for the local solver.
 * A token is an object (domain and problem files, etc) to be solve by a planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class LocalToken extends Token {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The domain filename.
     */
    private String domainFileName;

    /**
     * The problem filename.
     */
    private String problemFileName;

    /**
     * The boolean used to check if a token could be solved.
     */
    private boolean runnable;

    /**
     * The boolean used to check if a token is solved.
     */
    private boolean solved;

    /**
     * The String containing error during solving step.
     */
    private String error;

    /**
     * The planner used to solve the token.
     */
    private StateSpacePlanner planner;

    /**
     * The name of the planner used.
     */
    private String plannerName;

    /**
     * The CodedProblem associated to the token.
     */
    private CodedProblem codedProblem;

    /**
     * The ListModel for solution node.
     */
    private DefaultListModel<Node> solutioNodeListModel;

    /**
     * The Statistics object which contains all the statistics of the solving process.
     */
    private Statistics statistics;

    /**
     * The solution Plan found for a token.
     */
    private Plan solutionPlan;

    /**
     * Sets if a token could be solved by checking if files are not null.
     *
     * @return if a token could be solved.
     */
    private boolean isTokenRunnable() {
        return (this.getDomainFile() != null && this.getProblemFile() != null && planner != null);
    }

    /**
     * Returns the domain filename of the token.
     *
     * @return the domain filename of the token.
     */
    public String getDomainFileName() {
        return domainFileName;
    }

    /**
     * Returns the problem filename of the token.
     *
     * @return the problem filename of the token.
     */
    public String getProblemFileName() {
        return problemFileName;
    }

    /**
     * Returns if a token could be solved by checking if files are not null.
     *
     * @return if a token could be solved.
     */
    public boolean isRunnable() {
        return runnable;
    }

    /**
     * Returns if a token is solved.
     *
     * @return if a token is solved.
     */
    public boolean isSolved() {
        return solved;
    }

    /**
     * Sets if a token is solved.
     *
     * @param solved if a token is solved.
     */
    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    /**
     * Returns error message.
     *
     * @return error message.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error message.
     *
     * @param error error message.
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Returns the planner to use to solve the token.
     *
     * @return the planner to use to solve the token.
     */
    public StateSpacePlanner getPlanner() {
        return planner;
    }

    /**
     * Returns the planner name.
     *
     * @return Returns the planner name.
     */
    public String getPlannerName() {
        return plannerName;
    }

    /**
     * Returns the CodedProblem associated to the token.
     *
     * @return the CodedProblem associated to the token.
     */
    public CodedProblem getCodedProblem() {
        return codedProblem;
    }

    /**
     * Sets the CodedProblem associated to the token.
     *
     * @param codedProblem the CodedProblem associated to the token.
     */
    public void setCodedProblem(CodedProblem codedProblem) {
        this.codedProblem = codedProblem;
    }

    /**
     * Returns the ListModel for solution node.
     *
     * @return the ListModel for solution node.
     */
    public DefaultListModel<Node> getSolutioNodeListModel() {
        return this.solutioNodeListModel;
    }

    /**
     * Sets the ListModel for solution node.
     *
     * @param solutioNodeListModel the ListModel for solution node.
     */
    public void setSolutioNodeListModel(DefaultListModel<Node> solutioNodeListModel) {
        this.solutioNodeListModel = solutioNodeListModel;
    }

    /**
     * Returns the Statistics object associated to the Result.
     *
     * @return the Statistics object associated to the Result.
     */
    public Statistics getStatistics() {
        return this.statistics;
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
        return this.codedProblem.toStringCost(solutionPlan);
    }

    /**
     * Returns a JSON solution Plan as a String object.
     *
     * @return a JSON solution Plan as a String object.
     */
    public String getSolutionJson() {
        final JsonAdapter toJson = new JsonAdapter(this.codedProblem);
        return toJson.toJsonString(solutionPlan);
    }

    /**
     * Sets results (statistics and solution plan) for token.
     *
     * @param statistics   the statistics of the solving process.
     * @param solutionPlan the solution plan.
     */
    public void setResult(Statistics statistics, Plan solutionPlan) {
        this.statistics = statistics;
        this.solutionPlan = solutionPlan;
    }

    /**
     * Creates a new token.
     *
     * @param domainFile the domain file.
     * @param problemFile the problem file.
     * @param planner the planner to use.
     * @param plannerName the planner's name.
     */
    public LocalToken(File domainFile, File problemFile, StateSpacePlanner planner, String plannerName) {
        super(domainFile, problemFile);
        this.domainFileName = FileTools.removeExtension(domainFile.getName());
        this.problemFileName = FileTools.removeExtension(problemFile.getName());
        this.planner = planner;
        this.plannerName = plannerName;
        this.runnable = isTokenRunnable();
        this.solved = false;
        this.error = "";
    }

    /**
     * Token toString method.
     */
    @Override
    public String toString() {
        return domainFileName + " - " + problemFileName;
    }
}
