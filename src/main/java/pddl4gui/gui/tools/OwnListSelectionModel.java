package pddl4gui.gui.tools;

import javax.swing.DefaultListSelectionModel;

/**
 * This class implements the OwnListSelectionModel class that extends DefaultListSelectionModel
 * for JList of <code>PDDL4GUI</code>.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class OwnListSelectionModel extends DefaultListSelectionModel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new OwnListSelectionModel.
     */
    public OwnListSelectionModel() {
        super();
    }

    /**
     * Sets interval selection.
     *
     * @param index0 the first index.
     * @param index1 the second index.
     */
    @Override
    public void setSelectionInterval(int index0, int index1) {
        if (index0 == index1) {
            if (isSelectedIndex(index0)) {
                removeSelectionInterval(index0, index0);
                return;
            }
        }
        super.setSelectionInterval(index0, index1);
    }

    /**
     * Adds interval selection.
     *
     * @param index0 the first index.
     * @param index1 the second index.
     */
    @Override
    public void addSelectionInterval(int index0, int index1) {
        if (index0 == index1) {
            if (isSelectedIndex(index0)) {
                removeSelectionInterval(index0, index0);
                return;
            }
            super.addSelectionInterval(index0, index1);
        }
    }
}