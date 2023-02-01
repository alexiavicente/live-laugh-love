package view;

import model.Observable;
import model.Observer;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    private class StatLabel extends JPanel {
        private JProgressBar bar;
        private JLabel statLabel;
        private JLabel valueLabel;

        public StatLabel(String stat, int value) {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

            statLabel = new JLabel(stat, JLabel.LEFT);
            statLabel.setPreferredSize(new Dimension(70, 15));
            statLabel.setMaximumSize(new Dimension(70, 15));
            statLabel.setMinimumSize(new Dimension(70, 15));
            this.add(statLabel);

            this.add(Box.createRigidArea(new Dimension(10, 0)));

            bar = new JProgressBar(0,100);
            bar.setValue(value);
            bar.setPreferredSize(new Dimension(80, 5));
            bar.setMinimumSize(new Dimension(80, 5));
            bar.setMaximumSize(new Dimension(80, 5));
            this.add(bar);

            this.add(Box.createRigidArea(new Dimension(10, 0)));

            valueLabel = new JLabel(value + "");
            valueLabel.setMinimumSize(new Dimension(75, 20));
            this.add(valueLabel);
            this.setSize(getPreferredSize());
        }

        public void setValue(int value) {
            bar.setValue(value);
            valueLabel.setText(value + "");
        }
    }

    private class NumericLabel extends JPanel {
        private JLabel statLabel;
        private JLabel valueLabel;

        public NumericLabel(String title, int value) {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));

            statLabel = new JLabel(title);
            statLabel.setMaximumSize(new Dimension(150, 15));
            statLabel.setMinimumSize(new Dimension(70, 15));
            this.add(statLabel);

            this.add(Box.createRigidArea(new Dimension(25, 0)));

            valueLabel = new JLabel(value + "");
            valueLabel.setMaximumSize(new Dimension(150, 15));
            this.add(valueLabel);
        }

        public void setValue(int value) {
            valueLabel.setText(value + "");
        }
    }

    private StatLabel beauty;
    private StatLabel physique;
    private StatLabel style;
    private StatLabel charisma;
    private StatLabel sexAppeal;
    private NumericLabel money;
    private NumericLabel lovers;

    public StatsPanel(){
        init();
    }

    private void init(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentY(Component.CENTER_ALIGNMENT);

        beauty = new StatLabel("Beauty", 50);
        this.add(beauty);
        beauty.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(new JToolBar.Separator());

        physique = new StatLabel("Physique",100);
        this.add(physique);
        physique.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(new JToolBar.Separator());

        style = new StatLabel("Style",50);
        this.add(style);
        style.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(new JToolBar.Separator());

        charisma = new StatLabel("Charisma", 50);
        this.add(charisma);
        charisma.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(new JToolBar.Separator());

        sexAppeal = new StatLabel("Sex appeal", 50);
        this.add(sexAppeal);
        sexAppeal.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(new JToolBar.Separator());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        money = new NumericLabel("Money", 50);
        panel.add(money);
        money.setAlignmentX(Component.LEFT_ALIGNMENT);

        lovers = new NumericLabel("Lovers",50);
        panel.add(lovers);
        lovers.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.add(panel);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

    }

    public void setStats(JSONObject stats){
        beauty.setValue(stats.getInt("beauty"));
        physique.setValue(stats.getInt("phisique"));
        style.setValue(stats.getInt("style"));
        charisma.setValue(stats.getInt("charisma"));
        sexAppeal.setValue(stats.getInt("sexAppeal"));
        money.setValue(stats.getInt("money"));
        lovers.setValue(stats.getInt("lovers"));
    }
}
