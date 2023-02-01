package view.decorator;

import view.Panel;

import javax.swing.*;

public abstract class Decorator extends Panel {

    protected Panel roomImage;

    public Decorator(Panel roomImage) {
        this.roomImage = roomImage;
    }
}
