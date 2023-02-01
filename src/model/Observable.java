package model;

import org.json.JSONObject;

public interface Observable {

    public void registerObserver(Observer o);

    public void notifyChange();

    public JSONObject getState();

}
