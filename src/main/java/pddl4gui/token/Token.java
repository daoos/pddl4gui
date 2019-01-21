package pddl4gui.token;

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
     * The problem file.
     */
    private File problemFile;

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
     * Creates a new token.
     *
     * @param domainFile the domain file.
     * @param problemFile the problem file.
     */
    public Token(File domainFile, File problemFile) {
        this.domainFile = domainFile;
        this.problemFile = problemFile;
    }
}
