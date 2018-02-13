package solver.model;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;

import java.io.File;

/**
 * This class implements the main method of <code>Token</code>.
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
     * The heuristic used to solve the token.
     */
    private Heuristic.Type heuristic;

    /**
     * The weight used to solve the token.
     */
    private double weight;

    /**
     * The timeout used to solve the token.
     */
    private double timeout;

    /**
     * Sets if a token could be solved by checking if files are not null.
     *
     * @return if a token could be solved.
     */
    private boolean isTokenRunnable() {
        return (domainFile != null && problemFile != null);
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
     * Returns the heuristic used to solve the token.
     *
     * @return the heuristic used to solve the token.
     */
    public Heuristic.Type getHeuristic() {
        return heuristic;
    }

    /**
     * Returns the weight used to solve the token.
     *
     * @return the weight used to solve the token.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the timeout used to solve the token.
     *
     * @return the timeout used to solve the token.
     */
    public double getTimeout() {
        return timeout;
    }

    /**
     * Creates a new token.
     */
    public Token(File domainFile, File problemFile) {
        this.domainFile = domainFile;
        this.problemFile = problemFile;
        runnable = isTokenRunnable();
    }

    /**
     * Sets the heuristic's, weight's and timeout's token.
     *
     * @param heuristic the heuristic to solve the token.
     * @param weight the weight to solve the token.
     * @param timeout the timeout to solve the token.
     */
    public void setupToken(Heuristic.Type heuristic, double weight, double timeout) {
        this.heuristic = heuristic;
        this.weight = weight;
        this.timeout = timeout;
    }
}
