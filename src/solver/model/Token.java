package solver.model;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;

import java.io.File;

public class Token {
    private File domainFile;
    private File problemFile;
    private Result result;
    private boolean runnable;

    private Heuristic.Type heuristic;
    private double weight;
    private double timeout;

    public File getDomainFile() {
        return domainFile;
    }

    public File getProblemFile() {
        return problemFile;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public boolean isRunnable() {
        return runnable;
    }

    public Heuristic.Type getHeuristic() {
        return heuristic;
    }

    public double getWeight() {
        return weight;
    }

    public double getTimeout() {
        return timeout;
    }

    public Token(File domainFile, File problemFile) {
        this.domainFile = domainFile;
        this.problemFile = problemFile;
        runnable = isTokenRunnable();
    }

    private boolean isTokenRunnable() {
        return (domainFile != null && problemFile != null);
    }

    public void setupToken(Heuristic.Type heuristic, double weight, double timeout) {
        this.heuristic = heuristic;
        this.weight = weight;
        this.timeout = timeout;
    }
}
