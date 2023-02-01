package server;

import java.io.Serializable;

public class Communication implements Serializable {

    private static final long serialVersionUID = 1L;
    private String text;

    public Communication(String text) {
        this.text = text.trim();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
