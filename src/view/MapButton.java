package view;

import model.Observable;
import model.Observer;
import model.TripleLModel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapButton extends JPanel implements Observer {

    private JButton mapButton;
    MapWindow mapWindow;

    public MapButton(MapWindow mapWindow) {
        this.mapWindow = mapWindow;
        init();
        TripleLModel.getTripleLModel().registerObserver(this);
    }

    private void init() {
        mapButton = new JButton();
        setImage("./resources/maps/map.png");

        mapButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        mapButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapWindow.open();
            }
        });
        this.add(mapButton);
    }

    private void setImage(String map) {
        Image img = new ImageIcon(map).getImage();
        mapButton.setIcon(new ImageIcon(img.getScaledInstance(190, 190, Image.SCALE_SMOOTH)));
    }

    @Override
    public void update(Observable o) {
        JSONObject info = o.getState();
        if(info.has("room")){
            info = info.getJSONObject("room");
            setImage(info.getString("map"));
        }
    }
}
