package pddl4gui.gui;

import pddl4gui.gui.panel.InfoRestPanel;
import pddl4gui.gui.panel.LogPanel;
import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.SetupRestPanel;
import pddl4gui.gui.tools.WindowsManager;

import javax.swing.JFrame;
import java.io.Serializable;

public class RestClient extends JFrame implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    public RestClient(final String url) {
        final int width = 1200;
        final int height = 800;
        final int marging = 10;

        setLayout(null);
        setSize(width, height);
        setTitle(WindowsManager.NAME + " | REST solver");
        WindowsManager.setPoint(this.getLocation());
        WindowsManager.setWidth(width);
        WindowsManager.setHeight(height);

        final SetupRestPanel setupRestPanel;
        final InfoRestPanel infoRestPanel;
        final ResultPanel resultPanel;
        final LogPanel logPanel;

        setupRestPanel = new SetupRestPanel();
        setupRestPanel.setBounds(marging, marging, 360, 320);
        add(setupRestPanel);

        infoRestPanel = new InfoRestPanel(url);
        infoRestPanel.setBounds(380, marging, 300, 320);
        add(infoRestPanel);

        resultPanel = new ResultPanel();
        resultPanel.setBounds(690, marging, 500, 570);
        add(resultPanel);

        logPanel = new LogPanel();
        logPanel.setBounds(marging, 585, 1180, 175);
        add(logPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
