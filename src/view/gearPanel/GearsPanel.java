package view.gearPanel;

import model.Controller;
import model.Observable;
import model.Observer;
import model.TripleLModel;
import view.Panel;
import view.TripleLFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public abstract class GearsPanel extends JPanel implements Observer {

    private final int _CELL_HEIGHT = 25;
    private final int _CELL_WIDTH = 25;

    protected List<String> gearImages;
    protected List<String> gearNames;
    protected List<String> gearDescriptions;
    protected List<JButton> buttons;

    protected Controller _ctrl;

    private final int cells = 10;

    public GearsPanel(Controller ctrl) {

        _ctrl = ctrl;
        this.gearImages = new ArrayList<>();
        this.gearNames = new ArrayList<>();
        this.gearDescriptions = new ArrayList<>();
        this.buttons = new ArrayList<>();

        fillGears();
        init();

        TripleLModel.getTripleLModel().registerObserver(this);
    }

    protected void fillGears() {
        for(int i = 0; i < cells; i++) {
            gearImages.add("");
            gearNames.add(null);
            gearDescriptions.add("");
        }
    }

    protected void init() {
        JPanel mainPanel = new JPanel(new GridLayout(2,5, 5,5));

        for (int i = 0;  i < cells; i++) {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(_CELL_WIDTH, _CELL_HEIGHT));
            b.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            buttons.add(b);
            mainPanel.add(b);
        }
        this.add(mainPanel);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    protected void setGearImages() {
        for(JButton b: buttons) {
            b.setIcon(null);
            b.setName(null);
        }

        for(int i = 0; i < gearImages.size(); i++) {
            Image img = new ImageIcon(gearImages.get(i)).getImage();
            buttons.get(i).setIcon(new ImageIcon(img.getScaledInstance(_CELL_WIDTH - 2, _CELL_HEIGHT - 2, Image.SCALE_SMOOTH)));
            buttons.get(i).setName(gearNames.get(i));
            buttons.get(i).setToolTipText(gearNames.get(i) + ": " + gearDescriptions.get(i));
        }

        repaint();
    }

    @Override
    public abstract void update(Observable o);
}
