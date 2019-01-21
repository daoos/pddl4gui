package pddl4gui.token;

import java.io.File;
import java.io.Serializable;

/**
 * This class implements the <code>RestToken</code> for the REST solver.
 * A token is an object (domain and problem files, etc) solved by a REST planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 24.10.2018
 */
public class RestToken extends Token implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The id of the computation.
     */
    private int id;

    /**
     * The solution plan of this RestToken.
     */
    private String solutionPlan;

    /**
     * Returns the id of the computation.
     *
     * @return id of the computation.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the solution plan of this RestToken.
     *
     * @return the solution plan of this RestToken.
     */
    public String getSolutionPlan() {
        return solutionPlan;
    }

    /**
     * Sets the solution plan of this RestToken.
     *
     * @param solutionPlan the solution plan of this RestToken.
     */
    public void setSolutionPlan(String solutionPlan) {
        this.solutionPlan = solutionPlan;
    }

    /**
     * Creates a new REST token.
     *
     * @param domain  the domain file.
     * @param problem the problem file.
     * @param id      the id of the computation.
     */
    public RestToken(final File domain, final File problem, final int id) {
        super(domain, problem);
        this.id = id;
    }
}
