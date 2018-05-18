package pddl4gui.pddl;

import pddl4gui.gui.tools.HighlightText;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class PDDLContext {

    private HighlightText highlighter = new HighlightText(Color.LIGHT_GRAY);

    private String[] pddlKeywords = {"domain", "assign", "scale-up", "scale-down", "increase", "decrease", "start", "define", "problem",
            "requirements", "types", "constants", "predicates", "functions", "action", "init", "goal", "objects", "domain",
            "strips", "typing", "negative-preconditions", "disjunctive-preconditions", "equality", "existential-preconditions",
            "universal-preconditions", "quantified-preconditions", "conditional-effects", "fluents", "numeric-fluents", "object-fluents", "adl",
            "durative-actions", "duration-inequalities", "continuous-effects", "derived-predicates", "timed-initial-literals", "preferences",
            "constraints", "action-costs", "parameters", "duration", "condition", "effect", "precondition"};

    private String[] brackets = {"("};
    private String[] bCompletions = {")"};

    public HighlightText getHighlighter() {
        return highlighter;
    }

    public String getExtensionFile() {
        return ".pddl";
    }

    public String[] getPDDLKeywords() {
        return pddlKeywords;
    }

    public ArrayList<String> getbracketCompletions() {
        ArrayList<String> al = new ArrayList<>();
        Collections.addAll(al, bCompletions);
        return al;
    }

    public ArrayList<String> getbrackets() {
        ArrayList<String> al = new ArrayList<>();
        Collections.addAll(al, brackets);
        return al;
    }

    public ArrayList<String> setKeywords(String[] arr) {
        ArrayList<String> al = new ArrayList<>();
        Collections.addAll(al, arr);
        return al;
    }
}