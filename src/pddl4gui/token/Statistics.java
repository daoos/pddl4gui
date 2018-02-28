package pddl4gui.token;

public class Statistics {

    private double timeToParseInSeconds;
    private double timeToEncodeInSeconds;
    private double timeToPlanInSeconds;
    private double totalTimeInSeconds;
    private int numberOfActions;
    private int numberOfFluents;
    private double memoryForProblemInMBytes;
    private double memoryUsedToSearchInMBytes;
    private double totalMemoryInMBytes;
    private int depth;
    private double cost;

    public double getTimeToParseInSeconds() {
        return timeToParseInSeconds;
    }

    public void setTimeToParseInSeconds(double timeToParseInSeconds) {
        this.timeToParseInSeconds = timeToParseInSeconds;
        this.setTotalTimeInSeconds();
    }

    public double getTimeToEncodeInSeconds() {
        return timeToEncodeInSeconds;
    }

    public void setTimeToEncodeInSeconds(double timeToEncodeInSeconds) {
        this.timeToEncodeInSeconds = timeToEncodeInSeconds;
        this.setTotalTimeInSeconds();
    }

    public double getTimeToPlanInSeconds() {
        return timeToPlanInSeconds;
    }

    public void setTimeToPlanInSeconds(double timeToPlanInSeconds) {
        this.timeToPlanInSeconds = timeToPlanInSeconds;
        this.setTotalTimeInSeconds();
    }

    public double getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    private void setTotalTimeInSeconds() {
        this.totalTimeInSeconds = timeToParseInSeconds + timeToEncodeInSeconds + timeToPlanInSeconds;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    public int getNumberOfFluents() {
        return numberOfFluents;
    }

    public void setNumberOfFluents(int numberOfFluents) {
        this.numberOfFluents = numberOfFluents;
    }

    public double getMemoryForProblemInMBytes() {
        return memoryForProblemInMBytes;
    }

    public void setMemoryForProblemInMBytes(double memoryForProblemInMBytes) {
        this.memoryForProblemInMBytes = memoryForProblemInMBytes;
        this.setTotalMemoryInMBytes();
    }

    public double getMemoryUsedToSearchInMBytes() {
        return memoryUsedToSearchInMBytes;
    }

    public void setMemoryUsedToSearchInMBytes(double memoryUsedToSearchInMBytes) {
        this.memoryUsedToSearchInMBytes = memoryUsedToSearchInMBytes;
        this.setTotalMemoryInMBytes();
    }

    public double getTotalMemoryInMBytes() {
        return totalMemoryInMBytes;
    }

    private void setTotalMemoryInMBytes() {
        this.totalMemoryInMBytes = memoryForProblemInMBytes + memoryUsedToSearchInMBytes;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

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
