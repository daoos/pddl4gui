package pddl4gui.pddl;

import pddl4gui.gui.tools.HighlightText;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class implements the PDDLContext class of <code>PDDL4GUI</code>.
 * This object contains all specifications of the PDDL language used by the Editor.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class PDDLContext implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The HighlightText object used to highlight the text in text area.
     */
    private HighlightText highlighter = new HighlightText(Color.LIGHT_GRAY);

    /**
     * The String list of PDDL keywords.
     */
    private String[] pddlKeywords = {"domain", "assign", "scale-up", "scale-down", "increase", "decrease", "start",
            "define", "problem", "requirements", "types", "constants", "predicates", "functions", "action", "init",
            "goal", "objects", "domain", "strips", "typing", "negative-preconditions", "disjunctive-preconditions",
            "equality", "existential-preconditions", "universal-preconditions", "quantified-preconditions",
            "conditional-effects", "fluents", "numeric-fluents", "object-fluents", "adl", "durative-actions",
            "duration-inequalities", "continuous-effects", "derived-predicates", "timed-initial-literals",
            "preferences", "constraints", "action-costs", "parameters", "duration", "condition", "effect",
            "precondition"};

    /**
     * The String list of PDDL syntaxe delimiter (bracket).
     */
    private String[] brackets = {"("};

    /**
     * The String list of PDDL syntaxe delimiter (bracket) for auto completion.
     */
    private String[] bCompletions = {")"};

    /**
     * The extension of PDDL files.
     */
    private String extensionPddlFile = ".pddl";

    /**
     * Returns the HighlightText object.
     *
     * @return the HighlightText object.
     */
    public HighlightText getHighlighter() {
        return highlighter;
    }

    /**
     * Returns the extension used by PDDL files.
     *
     * @return the extension used by PDDL files.
     */
    public String getExtensionFile() {
        return extensionPddlFile;
    }

    /**
     * Returns the PDDL keywords.
     *
     * @return the PDDL keywords.
     */
    public String[] getPDDLKeywords() {
        return pddlKeywords;
    }

    /**
     * Returns an ArrayList of PDDL syntaxe delimiter for auto completion (bracket).
     *
     * @return an ArrayList of PDDL syntaxe delimiter for auto completion (bracket).
     */
    public ArrayList<String> getbracketCompletions() {
        ArrayList<String> al = new ArrayList<>();
        Collections.addAll(al, bCompletions);
        return al;
    }

    /**
     * Returns an ArrayList of PDDL syntaxe delimiter (bracket).
     *
     * @return an ArrayList of PDDL syntaxe delimiter (bracket).
     */
    public ArrayList<String> getbrackets() {
        ArrayList<String> al = new ArrayList<>();
        Collections.addAll(al, brackets);
        return al;
    }

    /**
     * Adds keywords to the PDDL language.
     * @param arr the keywords to add.
     *
     * @return an ArrayList of the new keywords.
     */
    public ArrayList<String> addKeywords(String[] arr) {
        ArrayList<String> al = new ArrayList<>();
        Collections.addAll(al, arr);
        return al;
    }
}