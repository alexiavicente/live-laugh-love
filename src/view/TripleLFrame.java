package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import model.*;
import network.Client;
import view.decorator.NpcDecorator;
import view.gearPanel.GearsPanel;
import view.gearPanel.PlayerGearsPanel;
import view.gearPanel.RoomGearsPanel;

@SuppressWarnings("serial")
public class TripleLFrame extends JFrame {

    private ConsolePanel consolePanel;
    private InputPanel inputPanel;
    private PlayerPanel playerPanel;
    private GearsPanel playerGearsPanel;
    private GearsPanel roomGearsPanel;
    private Panel roomPanel;

    private Controller _ctrl;

    public TripleLFrame() {
        _ctrl = Controller.getController();
    }
    public static void main(String[] args) {
        TripleLFrame frame = new TripleLFrame();
        if (frame.init() != true) {
            JOptionPane.showMessageDialog(null, "Couldn't connect to server");
            System.exit(1);
        }
    }


    private boolean init() {

        String ip = JOptionPane.showInputDialog("Input the server IP: ");
        Client client = null;

        if (ip != null && !ip.equals(""))
             client = new network.Client(ip, 4000);

        if ((client != null) && _ctrl.setClient(client)) {
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setTitle("Live laugh love");
            this.setPreferredSize(new Dimension(1015, 700));
            this.setLocation(25, 50);
            this.setBackground(Color.WHITE);
            this.setLayout(null);

            // Input panel
            inputPanel = new InputPanel(_ctrl);
            inputPanel.setLocation(new Point(15, 600));
            inputPanel.setSize(850,30);
            this.add(inputPanel);

            //Console Panel
            consolePanel = new ConsolePanel();
            consolePanel.setSize(new Dimension(550, 220));
            consolePanel.setLocation(new Point(15, 325));
            this.add(consolePanel);

            //Map Button and Dialog
            MapWindow mapWindow = new MapWindow(this);
            MapButton mapButton = new MapButton(mapWindow);
            mapButton.setSize(200,200);
            mapButton.setLocation(new Point(600, 50));
            this.add(mapButton);

            //Room gears panel
            roomGearsPanel = new RoomGearsPanel(_ctrl);
            roomGearsPanel.setSize(175,90);
            roomGearsPanel.setLocation(new Point(825, 50));
            this.add(roomGearsPanel);

            //Player gears Panel
            playerGearsPanel = new PlayerGearsPanel(_ctrl);
            playerGearsPanel.setSize(175,90);
            playerGearsPanel.setLocation(new Point(825, 160));
            this.add(playerGearsPanel);

            //Player panel
            playerPanel = new PlayerPanel();
            playerPanel.setSize(400,277);
            playerPanel.setLocation(new Point(600, 268));
            this.add(playerPanel);

            //Room panel
            roomPanel = new NpcDecorator(new RoomPanel());
            roomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            roomPanel.setSize(550,250);
            roomPanel.setLocation(new Point(15, 50));
            this.add(roomPanel);

            this.setResizable(false);
            this.setSize(1000, 500);

            inputPanel.requestFocusInWindow();

            this.pack();
            this.setVisible(true);

            _ctrl.startClient();
            return true;
        } else
            return false;
    }

}
