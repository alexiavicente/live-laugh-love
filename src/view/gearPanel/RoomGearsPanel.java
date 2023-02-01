package view.gearPanel;

import model.Controller;
import model.Observable;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomGearsPanel extends GearsPanel {

    public RoomGearsPanel(Controller ctrl) {
        super(ctrl);
    }

    @Override
    protected void init() {
        super.init();
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black, 2), "Room's gears",
                TitledBorder.LEFT, TitledBorder.TOP));
        for (JButton b: buttons) {
            b.setName("");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (b != null) {
                        try {
                            String name = b.getName().trim();
                            if (name != null)
                                _ctrl.sendCommand("get " + name);
                        } catch (Exception e1) { }
                    }
                }
            });
        }

    }

    @Override
    public void update(Observable o) {

        JSONObject info = o.getState();

        if(info.has("room")){
            gearImages.clear();
            gearNames.clear();
            gearDescriptions.clear();
            info = info.getJSONObject("room");
            JSONArray gearArray = info.getJSONArray("gears");
            for(int i = 0; i < gearArray.length(); i++){
                gearImages.add(gearArray.getJSONObject(i).getString("image"));
                gearNames.add(gearArray.getJSONObject(i).getString("name"));
                gearDescriptions.add(gearArray.getJSONObject(i).getString("desc"));
            }
            setGearImages();
            repaint();
        }
    }
}
