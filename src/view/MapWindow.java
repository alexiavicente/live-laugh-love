package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MapWindow extends JDialog {

    private JLabel mapLabel;

    public MapWindow(Frame parent) {
        super(parent, true); // change true to false for non-modal
        initGUI();
    }

    private void initGUI() {

        JPanel mainPanel = new JPanel();

        mapLabel = new JLabel();
        Image img = new ImageIcon("./resources/maps/mapDetailed.png").getImage();
        mapLabel.setIcon(new ImageIcon(img.getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
        mapLabel.setSize(500,500);
        mainPanel.add(mapLabel);

        mainPanel.setOpaque(true);
        this.setContentPane(mainPanel);

        this.setSize(getPreferredSize());
        this.pack();
        this.setVisible(false);
        setMinimumSize(new Dimension(500, 500));
    }

    public void open() {
        setLocation(getParent().getLocation().x + 600, getParent().getLocation().y + 50);
        setSize(500,500);
        pack();
        setVisible(true);
    }
}
