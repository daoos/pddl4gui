package pddl4gui.token;

import java.io.Serializable;

/**
 * This class implements the Statistics class of <code>PDDL4GUI</code>.
 * This object contains all statistics about token solving process.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Statistics implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The parsing time in s.
     */
    private double timeToParseInSeconds;

    /**
     * The encoding time in s.
     */
    private double timeToEncodeInSeconds;

    /**
     * The searching time in s.
     */
    private double timeToPlanInSeconds;

    /**
     * The total time in s (parsing + encoding + searching).
     */
    private double totalTimeInSeconds;

    /**
     * The number of actions of the problem solved.
     */
    private int numberOfActions;

    /**
     * The number of relevant fluents.
     */
    private int numberOfFluents;

    /**
     * The memory used to store the problem in MBytes.
     */
    private double memoryForProblemInMBytes;

    /**
     * The memory used to search in MBytes.
     */
    private double memoryUsedToSearchInMBytes;

    /**
     * The total memory used (store + search).
     */
    private double totalMemoryInMBytes;

    /**
     * The depth of the solution plan.
     */
    private int depth;

    /**
     * The cost of the solution plan.
     */
    private double cost;

    /**
     * Returns the parsing time in s.
     *
     * @return the parsing time in s.
     */
    public double getTimeToParseInSeconds() {
        return timeToParseInSeconds;
    }

    /**
     * Sets the parsing time in s.
     *
     * @param timeToParseInSeconds the parsing time in s.
     */
    public void setTimeToParseInSeconds(double timeToParseInSeconds) {
        this.timeToParseInSeconds = timeToParseInSeconds;
        this.setTotalTimeInSeconds();
    }

    /**
     * Returns the encoding time in s.
     *
     * @return the encoding time in s.
     */
    public double getTimeToEncodeInSeconds() {
        return timeToEncodeInSeconds;
    }

    /**
     * Sets the encoding time in s.
     *
     * @param timeToEncodeInSeconds the encoding time in s.
     */
    public void setTimeToEncodeInSeconds(double timeToEncodeInSeconds) {
        this.timeToEncodeInSeconds = timeToEncodeInSeconds;
        this.setTotalTimeInSeconds();
    }

    /**
     * Returns the search time in s.
     *
     * @return the search time in s.
     */
    public double getTimeToPlanInSeconds() {
        return timeToPlanInSeconds;
    }

    /**
     * Sets the search time in s.
     *
     * @param timeToPlanInSeconds the search time in s.
     */
    public void setTimeToPlanInSeconds(double timeToPlanInSeconds) {
        this.timeToPlanInSeconds = timeToPlanInSeconds;
        this.setTotalTimeInSeconds();
    }

    /**
     * Returns the total time in s.
     *
     * @return the total time in s.
     */
    public double getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    /**
     * Sets the total time in s.
     */
    private void setTotalTimeInSeconds() {
        this.totalTimeInSeconds = timeToParseInSeconds + timeToEncodeInSeconds + timeToPlanInSeconds;
    }

    /**
     * Returns the number of actions of the problem solved.
     *
     * @return the number of actions of the problem solved.
     */
    public int getNumberOfActions() {
        return numberOfActions;
    }

    /**
     * Sets the number of actions of the problem solved.
     *
     * @param numberOfActions the number of actions of the problem solved.
     */
    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    /**
     * Returns the number of relevant fluents.
     *
     * @return the number of relevant fluents.
     */
    public int getNumberOfFluents() {
        return numberOfFluents;
    }

    /**
     * Sets the number of relevant fluents.
     *
     * @param numberOfFluents the number of relevant fluents.
     */
    public void setNumberOfFluents(int numberOfFluents) {
        this.numberOfFluents = numberOfFluents;
    }

    /**
     * Returns the memory used to store the problem in MBytes.
     *
     * @return the memory used to store the problem in MBytes.
     */
    public double getMemoryForProblemInMBytes() {
        return memoryForProblemInMBytes;
    }

    /**
     * Sets the memory used to store the problem in MBytes.
     *
     * @param memoryForProblemInMBytes the memory used to store the problem in MBytes.
     */
    public void setMemoryForProblemInMBytes(double memoryForProblemInMBytes) {
        this.memoryForProblemInMBytes = memoryForProblemInMBytes;
        this.setTotalMemoryInMBytes();
    }

    /**
     * Returns the memory used to search in MBytes.
     *
     * @return the memory used to search in MBytes.
     */
    public double getMemoryUsedToSearchInMBytes() {
        return memoryUsedToSearchInMBytes;
    }

    /**
     * Sets the memory used to search in MBytes.
     *
     * @param memoryUsedToSearchInMBytes the memory used to search in MBytes.
     */
    public void setMemoryUsedToSearchInMBytes(double memoryUsedToSearchInMBytes) {
        this.memoryUsedToSearchInMBytes = memoryUsedToSearchInMBytes;
        this.setTotalMemoryInMBytes();
    }

    /**
     * Returns the total memory used in MBytes.
     *
     * @return the total memory used in MBytes.
     */
    public double getTotalMemoryInMBytes() {
        return totalMemoryInMBytes;
    }

    /**
     * Sets the total memory used in MBytes.
     */
    private void setTotalMemoryInMBytes() {
        this.totalMemoryInMBytes = memoryForProblemInMBytes + memoryUsedToSearchInMBytes;
    }

    /**
     * Returns the depth of the solution plan.
     *
     * @return the depth of the solution plan.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Sets the depth of the solution plan.
     *
     * @param depth the depth of the solution plan.
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Returns the cost of the solution plan.
     *
     * @return the cost of the solution plan.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the solution plan.
     *
     * @param cost the cost of the solution plan.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Creates a new statistics object to store statistical information about planner performances.
     * The default statistic values are set to 0.
     */
    public Statistics() {
        this.timeToParseInSeconds = 0.0;
        this.timeToEncodeInSeconds = 0.0;
        this.timeToPlanInSeconds = 0.0;
        this.totalTimeInSeconds = 0.0;
        this.numberOfActions = 0;
        this.numberOfFluents = 0;
        this.memoryForProblemInMBytes = 0.0;
        this.memoryUsedToSearchInMBytes = 0.0;
        this.totalMemoryInMBytes = 0.0;
        this.depth = 0;
        this.cost = 0.0;
    }
}
