package pddl4gui.gui.panel;

import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlanner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import pddl4gui.gui.Editor;
import pddl4gui.gui.tools.DrawCircle;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Vector;

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
     * The url to the RESTFull API
     */
    private String url;

    /**
     * Creates a new SetupRestPanel associated to the RestSolver main JFrame.
     */
    public InfoRestPanel(final String url) {
        this.url = url;

        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Solver informations"));

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        final JLabel statusLabel = new JLabel("REST solver alive ?");
        final JTextField urlRest = new JTextField(this.url, 300);
        final JFormattedTextField resultID = new JFormattedTextField(formatter);
        final JTextField status = new JTextField("status", 300);

        circlePanel = new DrawCircle(3, 3, 15);
        circlePanel.setBounds(140, 21, 25, 25);
        add(circlePanel);

        checkRestAlive();

        statusLabel.setBounds(15, 20, 120, 25);
        add(statusLabel);

        urlRest.setEditable(false);
        urlRest.setBounds(15, 55, 270, 25);
        add(urlRest);

        resultID.setEditable(true);
        resultID.setBounds(15, 95, 270, 25);
        add(resultID);

        status.setEditable(false);
        status.setBounds(15, 135, 270, 25);
        add(status);

        final JButton getResultButton = new JButton("Get result (text)");
        final JButton getResultJsonButton = new JButton("Get result (json)");
        final JButton deleteResultButton = new JButton("Delete result");

        getResultButton.setBounds(50, 175, 200, 25);
        getResultButton.setEnabled(true);
        getResultButton.addActionListener(e -> {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                final HttpGet httpget = new HttpGet(url + "/" + resultID.getValue());

                ResponseHandler<String> responseHandler = response -> {
                    int responseCode = response.getStatusLine().getStatusCode();
                    if (responseCode >= 200 && responseCode < 300) {
                        System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                + "] Get result (txt) request succeeded for id " + resultID.getValue());
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : "error";
                    } else {
                        System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                + "] Get result (txt) request failed for id " + resultID.getValue());
                        status.setText("[id " + resultID.getValue() + "] Get result (txt) failed");
                        return "error";
                    }
                };
                String responseBody = httpclient.execute(httpget, responseHandler);

                if (responseBody.startsWith("error") || responseBody.startsWith("{\"status\":\"error\"}") ) {
                    status.setText("[id " + resultID.getValue() + "] Get result (txt) failed");

                } else {
                    TriggerAction.displayResult(responseBody);
                    status.setText("[id " + resultID.getValue() + "] Get result (txt) succeeded");
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        });
        add(getResultButton);

        getResultJsonButton.setBounds(50, 215, 200, 25);
        getResultJsonButton.setEnabled(true);
        getResultJsonButton.addActionListener(e -> {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                final HttpGet httpget = new HttpGet(url + "/json/" + resultID.getValue());

                ResponseHandler<String> responseHandler = response -> {
                    int responseCode = response.getStatusLine().getStatusCode();
                    if (responseCode >= 200 && responseCode < 300) {
                        System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                + "] Get result (json) request succeeded for id " + resultID.getValue());
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : "error";
                    } else {
                        System.out.println("[Response Code : " + response.getStatusLine().getStatusCode()
                                + "] Get result (json) request failed for id " + resultID.getValue());
                        status.setText("[id " + resultID.getValue() + "] Get result (json) failed");
                        return "error";
                    }
                };
                String responseBody = httpclient.execute(httpget, responseHandler);

                if (responseBody.startsWith("error") || responseBody.startsWith("{\"status\":\"error\"}") ) {
                    status.setText("[id " + resultID.getValue() + "] Get result (json) failed");

                } else {
                    TriggerAction.displayResult(responseBody);
                    status.setText("[id " + resultID.getValue() + "] Get result (json) succeeded");
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        });
        add(getResultJsonButton);

        deleteResultButton.setBounds(50, 255, 200, 25);
        deleteResultButton.setEnabled(true);
        deleteResultButton.addActionListener(e -> {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpDelete httpDelete = new HttpDelete(url + "/" + resultID.getValue());

                ResponseHandler<String> responseHandler = response -> {
                    int responseCode = response.getStatusLine().getStatusCode();
                    if (responseCode >= 200 && responseCode < 300) {
                        System.out.println("[Response Code : " + responseCode
                                + "] Delete request succeeded for id " + resultID.getValue());
                        status.setText("[id " + resultID.getValue() + "] Delete succeeded");
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : "error";
                    } else {
                        System.out.println("[Response Code : " + responseCode
                                + "] Delete request failed for id " + resultID.getValue());
                        status.setText("[id " + resultID.getValue() + "] Delete failed");
                        return "error";
                    }
                };
                String responseBody = httpclient.execute(httpDelete, responseHandler);

                if (responseBody.startsWith("true")) {
                    status.setText("[id " + resultID.getValue() + "] Delete succeeded");

                } else {
                    status.setText("[id " + resultID.getValue() + "] Delete failed");
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        });
        add(deleteResultButton);
    }

    /**
     * Check if the webapps is alive or not. New Runnable which checks every 2 minutes.
     */
    private void checkRestAlive() {
        final long timeInterval = 120000;
        Runnable runnable = () -> {
            try {
                while (true) {
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
