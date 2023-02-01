package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TripleLModel implements Observable {

    private static TripleLModel model;
    private String textModel;
    private JSONObject infoModel;

    private List<Observer> observers;

    private TripleLModel() {
        textModel = "";
        observers = new ArrayList<>();
        infoModel = new JSONObject();
    }

    public static TripleLModel getTripleLModel() {
        if (model == null) {
            model = new TripleLModel();
        }
        return model;
    }

    public void addInfo(String text) {
        textModel += text;
        notifyChange();
    }

    public void addInfo(JSONObject info) {
        updateInfo(info);
    }

    private void updateInfo(JSONObject info) {
        infoModel = info;
        notifyChange();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyChange() {
        for (Observer o : observers)
            o.update(this);
    }

    @Override
    public JSONObject getState() {
        return infoModel;
    }

}
