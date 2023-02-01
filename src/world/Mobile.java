package world;

import org.json.JSONObject;
import world.gear.Gear;
import world.gear.GearContainer;
import world.gear.GearList;
import world.phrases.PhrasesList;
import world.relations.RelationshipContainer;
import world.relations.RelationshipList;
import world.state.State;

import java.util.List;

public class Mobile extends DatabaseObject implements Movable, RelationshipContainer {

    private static final long serialVersionUID = 1L;

    private GearList gearList;
    private int roomId;
    private State myState;

    private boolean flirting;

    private RelationshipList relationships;

    private PhrasesList phrases;

    public Mobile(String name) {
        super(name);

        flirting = false;

        this.gearList = new GearContainer(name + "'s bag", name + "'s bag:",
                20, false);
        gearList.setLocation(this);

        relationships = new RelationshipList(this);
        phrases = new PhrasesList();
    }

    @Override
    public void setRelation(Player player, int value) {
        relationships.setRelation(player, value);
    }

    @Override
    public int getRelation(Player player) {
        return relationships.getRelation(player);
    }

    public Player getCouple() {
        return relationships.getCouple();
    }

    public boolean isFlirting() {
        return flirting;
    }

    public void setFlirting(boolean flirting) {
        this.flirting = flirting;
    }

    @Override
    public void flirt(Movable movable) {
        myState.flirtBehavior(this, (Player) movable);
    }

    @Override
    public boolean addGear(Gear item) {
        return this.addGear(this, item);
    }

    @Override
    public boolean addGear(Movable movableToNotify, Gear gear) {
        return this.gearList.addGear(gear);
    }

    @Override
    public void removeGear(Gear gear) {
        this.gearList.removeGear(gear);
    }

    @Override
    public boolean giveGear(Movable movableToNotify, String gear, String otherName) {
        return this.gearList.giveGear(movableToNotify, gear, otherName);
    }

    @Override
    public void dropGear(String itemName, Room room, Movable movableToNotify) { }

    @Override
    public Gear getGear(String itemName) {
        return null;
    }

    @Override
    public boolean canBeCarried() {
        return false;
    }

    @Override
    public int getMaxGearCount() {
        return 0;
    }

    @Override
    public void setMaxGearCount(int max) {}

    public void setState(State myState) {
        this.myState = myState;
    }

    @Override
    public String inspect() {
        return gearList.inspect();
    }

    @Override
    public synchronized List<Gear> listGear() {
        return this.gearList.listGear();
    }

    @Override
    public void sendToPlayer(String message) {
        if (myState != null)
            myState.reactToSend(message, this);
    }
    @Override
    public void sendToPlayer(JSONObject info) {
        if (myState != null && info.has("msg"))
            myState.reactToSend(info.getString("msg"), this);
    }

    @Override
    public void moveToRoom(Room destination) {

        synchronized (World.getInstance()) {
            if (this.getLocation() != null) {
                ((Room) this.getLocation()).remove(this.getName());
            }
            destination.add(this);
        }

        this.roomId = destination.getDatabaseRef();
        this.setLocation(destination);
        sendToPlayer(((Room) destination).generateDescription());
    }

    @Override
    public void setStat(int value, Trait stat) {}

    @Override
    public int getStat(Trait stat) {
        return 0;
    }

    @Override
    public void setClothes(Gear clotheToEquip) {}

    public State getState() {
        return myState;
    }

    public void restartState(){
        if(myState != null)
            this.myState.restart();
    }
    @Override
    public void use(String itemName) {}

    @Override
    public int getRoomId() {
        return this.roomId;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject player = new JSONObject();
        player.put("name", getName());
        player.put("type", "npc");
        player.put("desc", getDescription());
        player.put("image", getImage());

        return player;
    }

    public Mobile clone() {

        Mobile clone = new Mobile(this.getName());

        clone.setDescription(this.getDescription());
        clone.setState(getState().cloneState());

        return clone;
    }

    @Override
    public void addSentence(String sentence) {
        phrases.addSentence(sentence);
    }

    @Override
    public String getRandomSentence() {
        return phrases.getRandomSentence();
    }
}
