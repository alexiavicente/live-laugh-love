package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import interpreter.CommandList;
import interpreter.Interpreter;
import org.json.JSONObject;
import world.Player;
import world.Room;
import world.World;

/**
 * server.Client is created whenever a new player network.Client connects to the
 * server.MudServer. It will call doInit which will request a socket from the
 * server from which it can use to interact with the server, world and other
 * players. The run() method for server.Client's thread call the necessary
 * methods during init and gameplay and disconnect from the MudServer. The Send
 * and receive msg's are controlled in its run command. When a server.Client
 * receives a text command while in game mode, it sends the text to the
 * world.Interpreter's processCommand method.
 *
 * @author Matt Turner, Ross Bottorf, Zach Boe, Jonathan Perrie
 *
 */
public class Client implements Runnable {

    private Thread thread;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private volatile server.ClientState state;
    private world.Player player;
    private TripleLServer server;

    private UserRegistrationHandler registrationHandler;

    /**
     * Client constructor will set the instance variable of socket to the socket
     * that is passed in. This socket will come from the server when the
     * connection is accepted.
     *
     * @param socket
     *            - The Socket to receive and send communications.
     * @param server
     *            - The MudServer the client is associated with.
     *
     */
    public Client(Socket socket, TripleLServer server) {
        this.socket = socket;
        this.server = server;
        registrationHandler = new UserRegistrationHandler(this);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setState(server.ClientState  state) {
        this.state = state;
    }

    /**
     * This method initializes the output and input stream variables and starts
     * the Client thread.
     */
    public void start() {
        this.state = ClientState.INIT;

        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        this.thread = new Thread(this);
        thread.start();
    }

    /**
     * sendMessageReply is a simple method that will take in a string and will print
     * the string to the screen. Based on what command was called it will print
     * accordingly, ie all players or just current.
     *
     * @param reply
     *            String of what to print
     */
    public void sendMessageReply(String reply) {
        JSONObject json = new JSONObject();
        json.put("msg", reply);
        sendReply(json);
    }

    public void sendReply(JSONObject reply) {
        Communication comm = new Communication(reply.toString());
        try {
            output.writeObject(comm);
        } catch (IOException e) {
            shutdown();
        }
    }

    /**
     * run is the main method for the client thread. It will run constantly
     * looking for messages being sent to the client and for any data that the
     * client is sending out.
     */
    public void run() {

        try {
            while (state != ClientState.DONE) {

                synchronized (World.getInstance().getLockObject()) {
                    while (World.getInstance().threadsLocked()) {
                        try {
                            World.getInstance().getLockObject().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (state == ClientState.INIT)
                    doInit();
                else if (state == ClientState.PLAYING)
                    listenForInput();
            }
        } catch (SocketException se) {
            shutdown();
        } catch (IOException ioe) {
            shutdown();
        }
    }

    /**
     * This method gets the current ClientState for the client:
     * DONE,ERROR,PLAYING,INIT.
     *
     * @return - The ClientState Enum that represents the client's state.
     */
    public ClientState getState() {
        return state;
    }

    /*
     * doInit will create a new Client and attempt to connect to the server. If
     * the connection is accepted a new Client will be made and the thread will
     * be started.
     */
    private void doInit() {

        sendMessageReply("Created by: Marcos Colombas, Pablo Campo, Arturo Lopez, Alexia Vicente and  Javier Sande\n");

        sendMessageReply("Type "
                + '"'
                + "commands"
                + '"'
                + " to see a list of available game commands. It will help you gain some knowledge of how to play the game.\n");

        registrationHandler.checkPlayerAccess();
    }

    /*
     * This private method listens for incoming messages from remote client,
     * processes quit commands or ooc commands; otherwise, it sends the command
     * to the intepreter.
     */
    private void listenForInput() throws IOException, SocketException {

        String textIn;

        textIn = receiveCommand();

        if (textIn.equals("quit")) {
            shutdown();
        } else if (textIn.toLowerCase().indexOf("ooc") == 0) {
            // if command text is ooc then message all players.
            textIn = textIn.substring(3);
            server.messageAllClients("chat <ooc>" + this.player.getName()
                    + " says: " + textIn);
        } else if (!textIn.equals("") || textIn != null) {
            Interpreter.getInstance().processCommand(this.player, textIn);
        }
    }

    /*
     * This private method prepares the run method for shutdown.
     */
    private void shutdown() {
        state = ClientState.DONE;
        // remove this client from the list of clients
        Client temp = this;
        TripleLServer.getClients().remove(this);
        if (temp != null) {
            if (temp.player != null) {
                temp.server.messageAllClients(temp.player.getName()
                        + " disconnected.");
                temp.player.setClient(null);
                ((Room) temp.player.getLocation()).remove(this.player.getName().toLowerCase());
                // temp.player.setLocation(null);
                World.getInstance().savePlayer(temp.player);

                // remove player's name from list of logged on players.
                World.getInstance().removeLoggedOn(
                        temp.player.getName().toLowerCase());
            }
            temp = null;
        }
    }

    /*
     * recieveCommand will take in a player ID and a action. The player ID is
     * the id of the player that sent the command and the action is what command
     * they sent. The method will take the proper action based on what the
     * command is, after the command is sent to the interpreter and the world
     * does the action.
     *
     * @return - A String that represents the text passed in the command.
     */
    public String receiveCommand() {
        try {
            String temp = ((Communication) this.input.readObject()).getText();
            if (temp == null)
                return "";
            else if (temp.equalsIgnoreCase("quit")) {
                shutdown();
                return "";
            } else if (temp.equalsIgnoreCase("commands")) {
                String result = "";
                for (String command : CommandList.getCommandDescriptions()) {
                    result += command + '\n';
                }
                this.sendMessageReply(result);
                return "";
            } else {
                return temp;
            }
        } catch (IOException e) {
            shutdown();
        } catch (ClassNotFoundException e) {
            shutdown();
        }
        return "";
    }
}