package view;

import model.Observable;
import model.TripleLModel;
import org.json.JSONObject;

import java.awt.*;
import java.util.*;
import model.Observer;

import javax.swing.*;

public class ConsolePanel extends JPanel implements Observer {
    private JTextArea worldText;
    private JScrollPane allTextPane;
    private final static String newline = "\n";

    public ConsolePanel() {
        init();
        TripleLModel.getTripleLModel().registerObserver(this);
    }

    private void init() {
        this.setLayout(null);
        worldText = new JTextArea();
        worldText.setFont(new Font("Lucida Console", Font.PLAIN, 16));
        worldText.setEditable(false);
        worldText.setLineWrap(true);
        worldText.setWrapStyleWord(true);
        worldText.setSize(new Dimension(400, 150));
        worldText.setBackground(Color.BLACK);
        allTextPane = new JScrollPane(worldText);
        allTextPane.setSize(new Dimension(550, 220));
        allTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        allTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        allTextPane.setBackground(Color.BLACK);

        worldText.setCaretPosition(worldText.getText().length());
        this.add(allTextPane);

    }

    public void clearText() {
        worldText.setText("");
    }

    @Override
    public void update(Observable o) {
        JSONObject info = o.getState();
        if(info.has("msg")){
            worldText.setForeground(Color.WHITE);
            worldText.append(info.getString("msg") + newline + newline);
            worldText.setCaretPosition(worldText.getText().length() - 1);
            repaint();
        }
    }
}
