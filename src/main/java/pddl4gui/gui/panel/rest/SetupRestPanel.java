package pddl4gui.gui.panel.rest;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.RestToken;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * This class implements the SetupRestPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays options for the Rest planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class SetupRestPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JSpinner to choose weight for the planner.
     */
    private final JSpinner weightSpinner;

    /**
     * The JSpinner to choose timeout for the planner.
     */
    private final JSpinner timeoutSpinner;

    /**
     * The JButton of the SetupSolverPanel.
     */
    private final JButton domainButton;
    private final JButton pbButton;

    /**
     * The PDDL domain file.
     */
    private File domainFile;

    /**
     * The PDDL problem file.
     */
    private File problemFile;

    /**
     * The default Heuristic used by the planner.
     */
    private Heuristic.Type heuristic = Heuristic.Type.FAST_FORWARD;

    /**
     * The default search strategy used.
     */
    private String strategyName = "A*";

    /**
     * Creates a new SetupRestPanel associated to the RestSolver main JFrame.
     */
    public SetupRestPanel(String url) {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver parameters"));

        final JLabel domainLabel = new JLabel("Domain file");
        final JLabel problemLabel = new JLabel("Problem file");
        final JLabel searchStrategyLabel = new JLabel("Search strategy");
        final JLabel heuristicLabel = new JLabel("Heuristic");
        final JLabel weightLabel = new JLabel("Weight");
        final JLabel timeLabel = new JLabel("Time out");

        domainButton = new JButton("Choose domain");
        pbButton = new JButton("Choose problem");

        domainButton.setBounds(140, 25, 150, 25);
        domainButton.setEnabled(true);
        domainButton.addActionListener(e -> {
            final Vector<File> domainTempFiles = FileTools.getFiles(this, 0, false, domainButton);
            if (domainTempFiles.size() == 1) {
                domainFile = domainTempFiles.firstElement();
                if (FileTools.checkFile(domainFile)) {
                    domainButton.setText(domainFile.getName());
                }
            } else {
                domainFile = null;
            }
        });
        add(domainButton);

        domainLabel.setBounds(15, 25, 140, 25);
        add(domainLabel);

        pbButton.setBounds(140, 65, 150, 25);
        pbButton.setEnabled(true);
        pbButton.addActionListener(e -> {
            final Vector<File> problemTempFiles = FileTools.getFiles(this, 0, false, pbButton);
            if (problemTempFiles.size() == 1) {
                problemFile = problemTempFiles.firstElement();
                if (FileTools.checkFile(problemFile)) {
                    pbButton.setText(problemFile.getName());
                }
            } else {
                problemFile = null;
            }
        });
        add(pbButton);

        problemLabel.setBounds(15, 65, 140, 25);
        add(problemLabel);

        final String[] searchStrategyList = {"A*", "Enforced Hill Climbing", "Breadth First Search",
            "Depth First Search", "Greedy Best First Search"};
        final JComboBox strategyComboBox = new JComboBox<>(searchStrategyList);
        strategyComboBox.setBounds(140, 105, 150, 25);
        strategyComboBox.setSelectedIndex(0);
        strategyComboBox.addActionListener(e -> strategyName = (String) strategyComboBox.getSelectedItem());
        add(strategyComboBox);

        searchStrategyLabel.setBounds(15, 105, 140, 25);
        add(searchStrategyLabel);

        final JComboBox heuristicComboBox = new JComboBox<>(Heuristic.Type.values());
        heuristicComboBox.setBounds(140, 145, 150, 25);
        heuristicComboBox.setSelectedItem(heuristic);
        heuristicComboBox.addActionListener(e -> heuristic = (Heuristic.Type) heuristicComboBox.getSelectedItem());
        add(heuristicComboBox);

        heuristicLabel.setBounds(15, 145, 150, 25);
        add(heuristicLabel);

        final SpinnerNumberModel modelWeight = new SpinnerNumberModel(1.0, 0.0, 10.0, 0.1);
        weightSpinner = new JSpinner(modelWeight);
        weightSpinner.setBounds(140, 185, 150, 25);
        add(weightSpinner);

        weightLabel.setBounds(15, 185, 150, 25);
        add(weightLabel);

        final SpinnerNumberModel modelTimeout = new SpinnerNumberModel(Planner.DEFAULT_TIMEOUT,
                0.0, 10000.0, 1);
        timeoutSpinner = new JSpinner(modelTimeout);
        timeoutSpinner.setBounds(140, 225, 150, 25);
        add(timeoutSpinner);

        timeLabel.setBounds(15, 225, 150, 25);
        add(timeLabel);

        final JButton planButton = new JButton("Resolve this problem !");
        planButton.setBounds(80, 305, 200, 25);
        planButton.setEnabled(true);
        planButton.addActionListener(e -> {
            if (TriggerAction.isRestAlive()) {
                if (domainFile != null && problemFile != null) {
                    resolve(url);
                }
            } else {
                TriggerAction.setRestStatus("RESTFull API offline");
            }
        });
        add(planButton);
    }

    /**
     * Returns the search strategy to use according to the given parameter.
     *
     * @param searchStrategy search strategy.
     * @return the search strategy as string keyword.
     */
    private String getSearchStrategy(String searchStrategy) {
        switch (searchStrategy) {
            case "Greedy Best First Search":
                return "GBFS";
            case "Breadth First Search":
                return "BFS";
            case "Depth First Search":
                return "DFS";
            case "Enforced Hill Climbing":
                return "EHC";
            default:
                return "A*";
        }
    }

    /**
     * Creates token and send it to the REST solver.
     *
     * @param url the url to POST the problem to solve.
     */
    private void resolve(final String url) {
        final double weight = (double) weightSpinner.getValue();
        final double timeout = (double) timeoutSpinner.getValue() * 1000;

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            JSONObject json = new JSONObject();

            json.put("domain", FileTools.readFileToString(domainFile));
            json.put("problem", FileTools.readFileToString(problemFile));
            json.put("search", getSearchStrategy(strategyName));
            json.put("heuristic", heuristic.toString());
            json.put("weight", String.valueOf(weight));
            json.put("timeout", String.valueOf(timeout));

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json.toString()));

            ResponseHandler<String> responseHandler = response -> {
                int responseCode = response.getStatusLine().getStatusCode();
                if (responseCode >= 200 && responseCode < 300) {
                    System.out.println("[Response Code : " + responseCode
                            + "] Post solve request succeeded");
                    //status.setText("[id " + resultID.getValue() + "] Delete succeeded");
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : "error";
                } else {
                    System.out.println("[Response Code : " + responseCode
                            + "] Post solve request failed");
                    return "error";
                }
            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            if (responseBody.equals("-1") || responseBody.equals("error")) {
                System.out.println("[id ? ] Error");
                TriggerAction.setRestStatus(responseBody);
            } else {
                final int computationId = Integer.parseInt(responseBody);
                TriggerAction.getRestModel().addElement(computationId);

                final RestToken restToken = new RestToken(domainFile, problemFile, computationId);
                TriggerAction.getRestTokenList().add(restToken);

                System.out.println("[id " + responseBody + "] New solving problem");
                TriggerAction.setRestStatus("[id " + responseBody + "] New solving problem");
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }

    }
}
