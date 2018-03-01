package pddl4gui.token;

import pddl4gui.context.planner.Planner;

import java.io.File;

/**
 * This class implements the pddl4g method of <code>Token</code>.
 * A token is an object given to a context to be solve by a planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Token {
    /**
     * The domain file.
     */
    private File domainFile;

    /**
     * The problem file.
     */
    private File problemFile;

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
    private Planner planner;

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
     * Returns the problem file of the token.
     *
     * @return the problem file of the token.
     */
    public File getProblemFile() {
        return problemFile;
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
    public Planner getPlanner() {
        return planner;
    }

    /**
     * Creates a new token.
     */
    public Token(File domainFile, File problemFile, Planner planner) {
        this.domainFile = domainFile;
        this.problemFile = problemFile;
        this.planner = planner;
        this.runnable = isTokenRunnable();
        this.solved = false;
        this.error = "";
    }
}
