package world.gear;

import org.json.JSONObject;
import world.DatabaseItem;
import world.Movable;
import world.Room;
import world.gear.Gear;

import java.util.List;

public interface GearList extends DatabaseItem {
    public boolean addGear(Gear item);

    public boolean addGear(Movable movableToNotify, Gear gear);

    public void removeGear(Gear gear);

    public boolean giveGear(Movable movableToNotify, String gear, String otherName);

    public void dropGear(String itemName, Room room, Movable movableToNotify);

    public Gear getGear(String itemName);

    public boolean canBeCarried();

    public int getMaxGearCount();

    public void setMaxGearCount(int max);

    public String inspect();

    public List<Gear> listGear();

    public JSONObject toJSONObject();
}
