package pddl4gui.gui.panel.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import pddl4gui.gui.tools.DrawCircle;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.token.RestToken;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

/**
 * This class implements the InfoRestPanel class of <code>PDDL4GUI</code>.
 * This JPanel displays informations about the Rest planner.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class InfoRestPanel extends JPanel implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The DrawCircle to display REST status.
     */
    private final DrawCircle circlePanel;

    /**
     * The url to the RESTFull API.
     */
    private final String url;

    /**
     * The list which contains computations' id.
     */
    private final JList<Integer> list;

    /**
     * The JTextField to display status.
     */
    private final JTextField status;

    /**
     * The current computation id.
     */
    private int id = -1;

    /**
     * Returns the color of the circle corresponding to the status of the REST API.
     *
     * @return the color of the circle corresponding to the status of the REST API.
     */
    public Color getRestStatus() {
        return circlePanel.getColor();
    }

    /**
     * Returns the current computation id.
     *
     * @return the current computation id.
     */
    public int getCurrentComputationId() {
        return this.id;
    }

    /**
     * Sets the status JTextField.
     */
    public void setStatus(final String statusString) {
        status.setText(statusString);
    }

    /**
     * Creates a new SetupRestPanel associated to the RestSolver main JFrame.
     */
    public InfoRestPanel(final String url) {
        this.url = url;

        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver informations"));

        final JLabel statusLabel = new JLabel("REST solver alive ?");
        final JTextField urlRest = new JTextField(this.url, 300);
        status = new JTextField("status", 300);

        circlePanel = new DrawCircle(3, 3, 15);
        circlePanel.setBounds(140, 26, 25, 25);
        add(circlePanel);

        statusLabel.setBounds(15, 25, 120, 25);
        add(statusLabel);

        urlRest.setEditable(false);
        urlRest.setBounds(15, 65, 270, 25);
        add(urlRest);

        list = new JList<>(TriggerAction.getRestModel());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                final Integer selectedValue = list.getSelectedValue();
                if (selectedValue != null) {
                    id = selectedValue;
                    final RestToken token = TriggerAction.getRestTokenFromId(id);
                    if (token != null && token.getSolutionPlan() != null) {
                        TriggerAction.displayResult(token.getSolutionPlan());
                        TriggerAction.enableSaveTxtResultRest(true);
                        TriggerAction.enableSaveJsonResultRest(false);
                        TriggerAction.enableValRest(true);
                    } else {
                        TriggerAction.clearResult();
                        TriggerAction.enableSaveTxtResultRest(false);
                        TriggerAction.enableSaveJsonResultRest(false);
                        TriggerAction.enableValRest(false);
                    }
                }
            }
        });
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setBounds(15, 105, 270, 150);
        add(listScrollPane);

        status.setEditable(false);
        status.setBounds(15, 305, 270, 25);
        add(status);

        final JButton getResultButton = new JButton("Get result (text)");
        final JButton getResultJsonButton = new JButton("Get result (json)");
        final JButton deleteResultButton = new JButton("Delete result");

        getResultButton.setBounds(50, 360, 200, 25);
        getResultButton.setEnabled(true);
        getResultButton.addActionListener(e -> {
            if (id != -1) {
                try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                    final HttpGet httpget = new HttpGet(url + "/" + id);

                    ResponseHandler<String> responseHandler = response -> {
                        int responseCode = response.getStatusLine().getStatusCode();
                        if (responseCode >= 200 && responseCode < 300) {
                            System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                    + "] Get result (txt) request succeeded for id " + id);
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : "error";
                        } else {
                            System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                    + "] Get result (txt) request failed for id " + id);
                            status.setText("[id " + id + "] Get result (txt) failed");
                            return "error";
                        }
                    };
                    String responseBody = httpclient.execute(httpget, responseHandler);

                    if (responseBody.startsWith("error") || responseBody.startsWith("{\"status\":\"error\"}")) {
                        status.setText("[id " + id + "] Get result (txt) failed");

                    } else {
                        TriggerAction.displayResult(responseBody);
                        final RestToken token = TriggerAction.getRestTokenFromId(id);
                        token.setSolutionPlan(responseBody);

                        TriggerAction.enableSaveTxtResultRest(true);
                        TriggerAction.enableSaveJsonResultRest(false);
                        TriggerAction.enableValRest(true);
                        status.setText("[id " + id + "] Get result (txt) succeeded");
                    }
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
            }
        });
        add(getResultButton);

        getResultJsonButton.setBounds(50, 400, 200, 25);
        getResultJsonButton.setEnabled(true);
        getResultJsonButton.addActionListener(e -> {
            if (id != -1) {
                try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                    final HttpGet httpget = new HttpGet(url + "/json/" + id);

                    ResponseHandler<String> responseHandler = response -> {
                        int responseCode = response.getStatusLine().getStatusCode();
                        if (responseCode >= 200 && responseCode < 300) {
                            System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                    + "] Get result (json) request succeeded for id " + id);
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : "error";
                        } else {
                            System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                    + "] Get result (json) request failed for id " + id);
                            status.setText("[id " + id + "] Get result (json) failed");
                            return "error";
                        }
                    };
                    String responseBody = httpclient.execute(httpget, responseHandler);

                    if (responseBody.startsWith("error") || responseBody.startsWith("{\"status\":\"error\"}")) {
                        status.setText("[id " + id + "] Get result (json) failed");

                    } else {
                        TriggerAction.displayResult(responseBody);
                        TriggerAction.enableSaveTxtResultRest(false);
                        TriggerAction.enableSaveJsonResultRest(true);
                        TriggerAction.enableValRest(false);
                        status.setText("[id " + id + "] Get result (json) succeeded");
                    }
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
            }
        });
        add(getResultJsonButton);

        deleteResultButton.setBounds(50, 440, 200, 25);
        deleteResultButton.setEnabled(true);
        deleteResultButton.addActionListener(e -> {
            if (id != -1) {
                try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                    HttpDelete httpDelete = new HttpDelete(url + "/" + id);

                    ResponseHandler<String> responseHandler = response -> {
                        int responseCode = response.getStatusLine().getStatusCode();
                        if (responseCode >= 200 && responseCode < 300) {
                            System.out.println("[Response Code : " + responseCode
                                    + "] Delete request succeeded for id " + id);
                            status.setText("[id " + id + "] Delete succeeded");
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : "error";
                        } else {
                            System.out.println("[Response Code : " + responseCode
                                    + "] Delete request failed for id " + id);
                            status.setText("[id " + id + "] Delete failed");
                            return "error";
                        }
                    };
                    String responseBody = httpclient.execute(httpDelete, responseHandler);

                    if (responseBody.startsWith("true")) {
                        TriggerAction.getRestModel().removeElement(id);
                        TriggerAction.clearResult();
                        TriggerAction.enableSaveTxtResultRest(false);
                        TriggerAction.enableSaveJsonResultRest(false);
                        TriggerAction.enableValRest(false);
                        status.setText("[id " + id + "] Delete succeeded");
                        id = -1;

                    } else {
                        status.setText("[id " + id + "] Delete failed");
                    }
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
            }
        });
        add(deleteResultButton);

        checkRestAlive();
    }

    /**
     * Check if the webapps is alive or not. New Runnable which checks every 2 minutes.
     */
    private void checkRestAlive() {
        final long timeInterval = 120000;
        Runnable runnable = () -> {
            try {
                while (TriggerAction.isPDDL4GUIRunning()) {
                    final HttpClient client = HttpClientBuilder.create().build();
                    final HttpGet request = new HttpGet(url + "/alive");
                    final HttpResponse response = client.execute(request);
                    final int responseCode = response.getStatusLine().getStatusCode();
                    if (responseCode == 200) {
                        System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                + "] Alive request succeeded");
                        circlePanel.setColor(Color.GREEN);
                        circlePanel.repaint();
                    } else {
                        System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                + "] Alive request failed");
                        circlePanel.setColor(Color.RED);
                        circlePanel.repaint();
                    }
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
