package pddl4gui.token;

import java.io.Serializable;
import java.util.Vector;

/**
 * This class implements the Queue class of <code>PDDL4GUI</code>.
 * This object contains all tokens to be solved by the Engine.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class Queue implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The list of Token.
     */
    private Vector<LocalToken> tokenListToSolve;

    /**
     * Creates a new Queue object.
     */
    public Queue() {
        tokenListToSolve = new Vector<>();
    }

    /**
     * Adds a Token in the Queue.
     *
     * @param token the token to add.
     */
    public void addTokenInQueue(LocalToken token) {
        tokenListToSolve.add(token);
    }

    /**
     * Gets and removes the first token of the Queue.
     *
     * @return the first token of the Queue.
     */
    public LocalToken getToken() {
        final LocalToken token = tokenListToSolve.firstElement();
        removeTokenFromQueue(token);
        return token;
    }

    /**
     * Clears the Queue.
     */
    public void clearList() {
        tokenListToSolve.clear();
    }

    /**
     * Returns the number of remaining token in the Queue.
     *
     * @return the number of remaining token in the Queue.
     */
    public int remainingTokens() {
        return tokenListToSolve.size();
    }

    /**
     * Removes the token in the Queue.
     *
     * @param token the token to remove from the Queue.
     */
    private void removeTokenFromQueue(LocalToken token) {
        tokenListToSolve.remove(token);
    }
}
