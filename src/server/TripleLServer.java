package server;

import world.World;
import world.creation.builder.CreateWorld;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TripleLServer {

    private static final int LISTENER_PORT = 4000;
    private static ArrayList<Client> clients;
    private ServerSocket serverSocket;
    private int port;

    /**
     * server.MudServer constructor will set the port that is read in, which
     * will always be 4000 and initialize the ArrayList of clients.
     *
     * @param port
     *            Port of the server
     */
    public TripleLServer(int port) {
        this.port = port;
        clients = new ArrayList<server.Client>();
    }

    public static void main(String[] args)  {

        try {
            World.getInstance().loadWorld();
        } catch (Exception e) {
            CreateWorld builder = new CreateWorld();
            builder.getWorld();
        }

        if (!World.getInstance().confirmPlayer(
                "administrator", "password")) {
            World.getInstance().createPlayer("administrator",
                    "password");
            World.getInstance().saveWorld();
            World.getInstance().savePlayer(World.getInstance().getPlayer("administrator"));
        }
        TripleLServer tripleLServer = new TripleLServer(LISTENER_PORT);
        World.getInstance().saveWorld();
        tripleLServer.start();
    }

    /**
     * This method returns the List of clients connected to the server.
     *
     * @return - The list of clients connected to the server.
     */
    public static List<Client> getClients() {
        return clients;
    }

    /**
     * Start is the main method for the server. It will be called from main()
     * and once it starts it will create a new ServerSocket and a new socket.
     * The new socket will be used once the server gets a request to connect.
     * Once it gets a request it will call accept on the connect, make a new
     * client and call start on that client. It will do this until the server
     * disconnects.
     */
    public void start() {
        List<Client> removalList = new ArrayList<Client>();

        try {
            serverSocket = new ServerSocket(port);
            Socket connection;
            Client c;
            InetAddress address = InetAddress.getLocalHost();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showOptionDialog(null, address.getHostAddress() + "/" + serverSocket.getLocalPort(),
                            "Server IP", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null,
                            new Object[]{}, null);
                }
            });

            t.start();

            while (true) {

                connection = serverSocket.accept();

                c = new server.Client(connection, this);
                System.out.println("Got connection: " + connection);
                clients.add(c);
                c.start();

                // Check for disconnected clients
                removalList.clear();

                for (server.Client c2 : clients) {
                    if (c2.getState() == ClientState.DONE) {
                        removalList.add(c2);
                    }
                }
                clients.removeAll(removalList);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ioe2) {
                    ioe2.printStackTrace();
                }
            }
        }
    }

    /**
     * messageAllCleints will be used when a command is called that needs to be
     * sent to all clients. The client ArrayList will be used here to get access
     * to all connected clients.
     *
     * @param msg
     *            String to be sent to players
     */
    public void messageAllClients(String msg) {
        for (Client c : clients) {
            if (c.getState() != ClientState.ERROR) {
                c.sendMessageReply(msg);
            }
        }
    }
}

