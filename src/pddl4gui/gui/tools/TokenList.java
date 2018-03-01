package pddl4gui.gui.tools;

import pddl4gui.token.Token;

import javax.swing.*;

public class TokenList {

    private static DefaultListModel<Token> listModel = new DefaultListModel<>();

    public static DefaultListModel<Token> getListModel() {
        return listModel;
    }

    private TokenList() {
    }
}
