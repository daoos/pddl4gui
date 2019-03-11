package pddl4gui.gui;

import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.rest.InfoRestPanel;
import pddl4gui.gui.panel.rest.MenuRestPanel;
import pddl4gui.gui.panel.rest.SetupRestPanel;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;

import javax.swing.JFrame;

/**
 * This class implements the RestSolver class of <code>PDDL4GUI</code>.
 * This JFrame displays all the Panel used in PDDL4GUI (REST). It's the main JFrame.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class RestSolver extends JFrame {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new RestSolver.
     */
    public RestSolver() {
        setLayout(null);
        setSize(WindowsManager.getWidth(), WindowsManager.getHeight());
        setTitle(WindowsManager.NAME + " | REST solver");
        WindowsManager.setPoint(this.getLocation());

        final SetupRestPanel setupRestPanel;
        final MenuRestPanel menuRestPanel;
        final InfoRestPanel infoRestPanel;
        final ResultPanel resultPanel;

        setupRestPanel = new SetupRestPanel();
        setupRestPanel.setBounds(10, 10, 360, 570);
        add(setupRestPanel);

        menuRestPanel = new MenuRestPanel();
        this.setJMenuBar(menuRestPanel);

        infoRestPanel = new InfoRestPanel();
        infoRestPanel.setBounds(380, 10, 300, 570);
        add(infoRestPanel);

        resultPanel = new ResultPanel();
        resultPanel.setBounds(690, 10, 630, 570);
        add(resultPanel);

        TriggerAction.setupPanel(resultPanel, menuRestPanel, infoRestPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
