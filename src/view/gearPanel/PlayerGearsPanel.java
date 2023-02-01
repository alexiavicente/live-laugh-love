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

public class PlayerGearsPanel extends GearsPanel {

    public PlayerGearsPanel(Controller ctrl) {
        super(ctrl);
    }

    @Override
    protected void init() {
        super.init();
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black, 2), "Player's gears",
                TitledBorder.LEFT, TitledBorder.TOP));

        for (JButton b: buttons)
            b.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (b != null) {
                        try {
                            String name = b.getName().trim();
                            if (name != null)
                                _ctrl.sendCommand("use " + name);
                        } catch (Exception e1) { }
                    }
                }
            });
    }

    @Override
    public void update(Observable o) {
        JSONObject info = o.getState();
        if(info.has("player")){
            gearImages.clear();
            gearNames.clear();
            gearDescriptions.clear();
            info = info.getJSONObject("player");
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
