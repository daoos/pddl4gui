package pddl4gui.token;

import java.io.File;
import java.io.Serializable;

/**
 * This class implements the <code>RestToken</code>.
 * A token is an object (domain and problem files, etc) solved by a REST planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 24.10.2018
 */
public class RestToken implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The domain file.
     */
    private File domainFile;

    /**
     * The problem file.
     */
    private File problemFile;

    /**
     * The id of the computation.
     */
    private int id;

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
     * Returns the id of the computation.
     *
     * @return id of the computation.
     */
    public int getId() {
        return id;
    }

    /**
     * Creates a new REST token.
     *
     * @param domain  the domain file.
     * @param problem the problem file.
     * @param id      the id of the computation.
     */
    public RestToken (final File domain, final File problem, final int id){
        this.domainFile = domain;
        this.problemFile = problem;
        this.id = id;
    }
}
