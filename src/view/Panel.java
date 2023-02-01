package view;

import model.Observable;
import model.Observer;
import model.TripleLModel;

import javax.swing.*;

public abstract class Panel extends JComponent implements Observer {
    public Panel() {
        TripleLModel.getTripleLModel().registerObserver(this);
    }
}
