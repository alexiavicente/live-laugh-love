package world;

import org.json.JSONObject;
import world.gear.Gear;
import world.gear.GearList;
import world.phrases.PhrasesContainer;
import world.relations.RelationshipContainer;

public interface Movable extends DatabaseItem, GearList, PhrasesContainer {
    public void sendToPlayer(String message);

    public void sendToPlayer(JSONObject info);

    public void moveToRoom(Room destination);

    public void setStat(int value, Trait stat);

    public int getStat(Trait stat);

    public void setClothes(Gear clotheToEquip);

    public void use(String itemName);

    public int getRoomId();

    public boolean isFlirting();

    public void setFlirting(boolean flirting);

    public void flirt(Movable movable);
}
