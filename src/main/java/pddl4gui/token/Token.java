package pddl4gui.token;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.planners.statespace.StateSpacePlanner;
import fr.uga.pddl4j.planners.statespace.search.strategy.Node;
import pddl4gui.gui.tools.FileTools;

import javax.swing.DefaultListModel;
import java.io.File;
import java.io.Serializable;

/**
 * This class implements the <code>Token</code>.
 * A token is an object (domain and problem files, etc) to be solve by a planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Token implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The domain file.
     */
    private File domainFile;

    /**
     * The domain filename.
     */
    private String domainFileName;

    /**
     * The problem file.
     */
    private File problemFile;

    /**
     * The problem filename.
     */
    private String problemFileName;

    /**
     * The result object containing all the result from the solving step.
     */
    private Result result;

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
     * Sets if a token could be solved by checking if files are not null.
     *
     * @return if a token could be solved.
     */
    private boolean isTokenRunnable() {
        return (domainFile != null && problemFile != null && planner != null);
    }

    /**
     * Returns the domain file of the token.
     *
     * @return the domain file of the token.
     */
    public File getDomainFile() {
        return domainFile;
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
     * Returns the problem file of the token.
     *
     * @return the problem file of the token.
     */
    public File getProblemFile() {
        return problemFile;
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
     * Returns the result object of a token.
     *
     * @return the result object of a token.
     */
    public Result getResult() {
        return result;
    }

    /**
     * Sets the result object of a token.
     *
     * @param result the result object of a token.
     */
    public void setResult(Result result) {
        this.result = result;
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
        return solutioNodeListModel;
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
     * Creates a new token.
     *
     * @param domainFile the domain file.
     * @param problemFile the problem file.
     * @param planner the planner to use.
     * @param plannerName the planner's name.
     */
    public Token(File domainFile, File problemFile, StateSpacePlanner planner, String plannerName) {
        this.domainFile = domainFile;
        this.domainFileName = FileTools.removeExtension(domainFile.getName());
        this.problemFile = problemFile;
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
