package view;

import model.Observable;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

import view.Panel;

public class RoomPanel extends Panel {

    private Image image;

    public RoomPanel(){
        init();
    }

    private void init(){
        image =  new ImageIcon("./resources/title.png").getImage();
    }

    public void paintComponents(Graphics g) {
        paintComponent(g);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, 550, 250, null);
    }

    private void setImage(String path){
        image = new ImageIcon(path).getImage();
        repaint();
    }

    @Override
    public void update(Observable o) {
        JSONObject info = o.getState();
        if(info.has("room")){
            info = info.getJSONObject("room");
            setImage(info.getString("image"));
        }
    }

}
