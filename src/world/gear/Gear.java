package world.gear;

import org.json.JSONArray;
import org.json.JSONObject;
import world.DatabaseObject;
import world.Movable;
import world.Room;

public abstract class Gear extends DatabaseObject implements Comparable<Gear> {

    private static final long serialVersionUID = 1L;
    private int itemLevel;
    private int price;


    public Gear(String name, String description, String image) {
        super(name);
        setDescription(description);
        setImage(image);
        this.setItemLevel(1);
    }

    public Gear(String name, String description, String image, int price) {
        super(name);
        setDescription(description);
        setImage(image);
        this.setItemLevel(1);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setItemLevel(int lvl) {
        this.itemLevel = lvl;
    }

    public int getItemLevel() {
        return this.itemLevel;
    }

    @Override
    public int compareTo(Gear gear) {
        return this.getName().compareTo(((Gear) gear).getName());
    }

    public abstract void getDefaultBehavior(Movable movable);

    public String toString() {
        String text = super.toString();
        if(getPrice() > 0) {
            text += "(Price: " + getPrice() + "LCoins)";
        }
        return text;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject gear = new JSONObject();
        gear.put("name", getName());
        gear.put("desc", getDescription() + ". Price: " + price + " LCoins");
        gear.put("image", getImage());
        return gear;
    }

    public abstract Gear cloneGear();

 }
