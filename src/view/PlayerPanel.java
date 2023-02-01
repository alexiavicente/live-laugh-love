package view;

import model.Observable;
import model.Observer;
import model.TripleLModel;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PlayerPanel extends JPanel implements Observer {

    private JLabel playerImage;
    private StatsPanel playerStats;
    private ImageIcon image;
    private String name = "Player";

    public PlayerPanel(){
        init();
        TripleLModel.getTripleLModel().registerObserver(this);
    }

    private void init(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black, 2), name,
                TitledBorder.LEFT, TitledBorder.TOP));

        playerImage = new JLabel();
        playerStats = new StatsPanel();

        Image img = new ImageIcon("./resources/players/female.png").getImage();
        image = new ImageIcon(img.getScaledInstance(107, 185, Image.SCALE_SMOOTH));
        playerImage.setIcon(image);

        //this.add(Box.createRigidArea(new Dimension(30, 0)), Component.CENTER_ALIGNMENT);
        this.add(playerImage, Component.LEFT_ALIGNMENT);
      // this.add(Box.createRigidArea(new Dimension(30, 0)), Component.CENTER_ALIGNMENT);
        this.add(playerStats, Component.RIGHT_ALIGNMENT);

    }

    private void setImage(String path){
        Image img = new ImageIcon(path).getImage();
        image = new ImageIcon(img.getScaledInstance(107, 185, Image.SCALE_SMOOTH));
        playerImage.setIcon(image);
    }

    @Override
    public void update(Observable o) {
        JSONObject info = o.getState();
        if(info.has("player")){
            info = info.getJSONObject("player");
            setImage(info.getString("image"));
            name = info.getString("name");
            playerStats.setStats(info.getJSONObject("stats"));
        }
    }
}
