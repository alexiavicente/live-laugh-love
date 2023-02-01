package view;

import model.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputPanel extends JPanel {

    private Controller _ctrl;
    private JTextField _textField;
    private JButton _enterBut;
    private JButton _saveBut;
    private JButton _exitBut;

    public InputPanel(Controller _ctrl) {
        this._ctrl = _ctrl;
        init();
    }

    private void init() {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        // Text field
        _textField = new JTextField();
        _textField.addKeyListener(new EnterListener());
        _textField.setPreferredSize(new Dimension(550, 18));
        _textField.setMaximumSize(new Dimension(550, 18));
        _textField.setLocation(new Point(15, 600));
        _textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(_textField);

        this.add(Box.createRigidArea(new Dimension(35, 10)));

        //Buttons
        _enterBut = new JButton("Enter");
        _enterBut.addActionListener((e) -> sendCommand());
        _enterBut.setSize(new Dimension(70, 30));
        _enterBut.setLocation(new Point(600, 593));
        this.add(_enterBut);

        _saveBut  = new JButton("Save");
        _saveBut.addActionListener((e) -> sendCommand("save"));
        _saveBut.setSize(new Dimension(70, 30));
        _saveBut.setLocation(new Point(670, 593));
        this.add(_saveBut);

        _exitBut = new JButton("Exit");
        _exitBut.addActionListener((e) -> sendCommand("quit"));
        _exitBut.setPreferredSize(new Dimension(70, 30));
        _exitBut.setLocation(new Point(740, 593));
        this.add(_exitBut);
    }

    public boolean requestFocusInWindow() {
        return _textField.requestFocusInWindow();
    }
    /*
     * AboutListener the the ActionListener the the About menu item. it will
     * display the programmers names and a short description of the program.
     */
    private class AboutListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String about = "Live Laugh Love"
                    + "\n\n Authors: Alexia, Pablo, Marcos, Arturo and Javier";
            JOptionPane.showMessageDialog(null, about);
        }

    }

    /*
     * ExitListener is the ActionListener for the Exit menu item. It will simple
     * call System.exit(0) which will exit out of the current program.
     */
    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            System.exit(0);
        }
    }

    /*
     * This method sends text commands to the network.client.
     */
    private void sendCommand() {
        String temp = _textField.getText();
        _ctrl.sendCommand(temp);
        _textField.setText("");
        if (temp.equalsIgnoreCase("quit")) {
            System.exit(0);
        }
    }

    public void sendCommand(String command) {
        _textField.setText("");
        if (command.equalsIgnoreCase("quit")) {
            _ctrl.sendCommand(command);
            System.exit(0);
        }  else {
            _ctrl.sendCommand(command);
        }
    }


    /*
     * EnterListener is the KeyListener that waits for the Enter key to be
     * pressed. This Listener will perform the exact same operation as the
     * ButtonListener for the Enter button on the panel. It will take the
     * command and execute it, clear the field and reset the focus back to the
     * field.
     */
    private class EnterListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                sendCommand();
            }
        }

        public void keyReleased(KeyEvent e) { }

    }
}
