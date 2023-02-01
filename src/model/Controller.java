package model;

import network.Client;
import org.json.JSONObject;

public class Controller {

    private static Controller _ctr;
    private static TripleLModel _model;
    private static Client _client;

    public Controller() {
        _model = TripleLModel.getTripleLModel();
    }

    public static void addClient(Client client) {
        _client = client;
    }

    public static Controller getController() {
        if (_ctr == null) {
            _ctr = new Controller();
        }
        return _ctr;
    }

    public void addInfo(String text) {
        _model.addInfo(text);
    }

    public void addInfo(JSONObject info) {
        _model.addInfo(info);
    }

    /*
     *   Client methods
     */
    public boolean setClient(Client client) {
        _client = client;
        return _client.connect();
    }

    public void startClient() {
        _client.start();
    }

    /*
     * This method sends text commands to the network.client.
     */

    public void sendCommand(String command) {
        if (command.equalsIgnoreCase("quit")) {
            _client.sendCommand(command);
            System.exit(0);
        }  else {
            _client.sendCommand(command);
        }
    }
}
