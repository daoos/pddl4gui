package pddl4gui.token;

import java.util.Vector;

public class Queue {

    private Vector<Token> tokenListToSolve;

    public Queue() {
        tokenListToSolve = new Vector<>();
    }

    public void addToken(Token token) {
        tokenListToSolve.add(token);
    }

    public Token getToken() {
        final Token token = tokenListToSolve.firstElement();
        removeSolvedTokenFromQueue(token);
        return token;
    }

    public void clearList() {
        tokenListToSolve.clear();
    }

    public int remainingTokens() {
        return tokenListToSolve.size();
    }

    public void removeSolvedTokenFromQueue(Token token) {
        tokenListToSolve.remove(token);
    }
}
