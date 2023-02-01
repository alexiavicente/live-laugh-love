package view.decorator;

import model.Observable;
import org.json.JSONArray;
import org.json.JSONObject;
import view.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NpcDecorator extends Decorator {

    private List<ImageIcon> npc;
    private String playerName;

    public NpcDecorator(Panel roomImage) {
        super(roomImage);
        npc = new ArrayList<>();
        playerName = "";
    }

    protected void paintComponent(Graphics g) {
        roomImage.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;

        int sections = npc.size() + 1;
        int width = getWidth() / sections;
        int height = getHeight();

        for(int i = 0; i < npc.size(); i++)
            g2.drawImage(npc.get(i).getImage(), width * (i + 1) - 55 , height - 225, 113,225, null);
    }

    @Override
    public void update(Observable o) {
        JSONObject info = o.getState();
        if (info.has("player")) {
            playerName = info.getJSONObject("player").getString("name");
        }

        if(info.has("room")){
            info = info.getJSONObject("room");
            JSONArray movables = info.getJSONArray("movables");
            npc.clear();
            for(int i = 0; i < movables.length(); i++){
                if(!movables.getJSONObject(i).getString("name").equals(playerName))
                    npc.add(new ImageIcon(movables.getJSONObject(i).getString("image")));
            }
            repaint();
        }
    }
}
