package world;

import org.json.JSONObject;
import world.gear.*;
import world.phrases.PhrasesList;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Player extends DatabaseObject implements Movable {

    private static final long serialVersionUID = 1L;
    private int phisique;
    private int style;
    private int charisma;
    private int sexAppeal;
    private int beauty;
    private int money;

    private boolean flirting;

    private Clothing clothes;

    private CharacterClass playerClass;

    private int loversCount;

    private Player mySelf;
    private int roomId;

    private GearList gearList;

    private PhrasesList phrases;

    private transient server.Client client;

    private String password;

    public Player(String name) {
        super(name);
        roomId = 0;
        phisique = 0;
        style = 0;
        charisma = 0;
        beauty = 0;
        money = 100;
        sexAppeal = calculateSexAppeal();

        flirting = false;

        setLocation((Room) World.getInstance().getDatabaseObject(0));

        phrases = new PhrasesList();
        phrases.addSentence("Your eyes are beautiful");
        phrases.addSentence("Are you from Tennesse? Because you are the only one I see ;)");
        phrases.addSentence("Como tu por aqui y las carceles vacias?");
        phrases.addSentence("Roses are red, violets are blue, you are very pretty and I like you");
        phrases.addSentence("I wish I were cross eyed so I could see you twice ;)");
        phrases.addSentence("Do you have a map? I keep getting lost in your eyes");
        phrases.addSentence("Are you French? Because Eifel for you");
        phrases.addSentence("If you were a vegetable you would be a cute-cumber");
        phrases.addSentence("If you were a fruit you would be a fine-apple");
        phrases.addSentence("You are so pretty");
        phrases.addSentence("I want you");
        phrases.addSentence("You are the best person I have ever met");
        phrases.addSentence("I have never seen anyone so perfect");

        gearList = new GearContainer(name + "'s bag", name + "'s bag:", 10, false);
        gearList.setLocation(this);

        setImage("./resources/players/male.png");

        mySelf = this;

    }

    @Override
    public void moveToRoom(Room destination) {

        synchronized (World.getInstance()) {
            if (this.getLocation() != null) {
                ((Room) this.getLocation()).remove(getName());
            }
            this.setLocation(destination);
            destination.add(this);
        }

        this.roomId = ((Room) this.getLocation()).getDatabaseRef();
        sendToPlayer(((Room) this.getLocation()).generateDescription());
        sendToPlayer(stateToJSONObject());
    }

    public void setStat(int value, Trait stat) {
        if (stat == Trait.CHARISMA) {
            value = max(value, 0);
            charisma = min(value, 100);
        }
        else if (stat == Trait.PHISIQUE) {
            value = max(value, 0);
            phisique = min(value, 100);
        }
        else if (stat == Trait.STYLE) {
            value = max(value, 0);
            style = min(value, 100);
        }
        else if (stat == Trait.BEAUTY) {
            value = max(value, 0);
            beauty = min(value, 100);
        }

        sexAppeal = calculateSexAppeal();
        sendToPlayer(stateToJSONObject());
    }

    public int getStat(Trait stat) {
        if (stat == Trait.CHARISMA)
            return charisma;
        else if (stat == Trait.PHISIQUE)
            return phisique;
        else if (stat == Trait.STYLE)
            return style;
        else if (stat == Trait.BEAUTY)
            return beauty;
        else if (stat == Trait.SEXAPEAL)
            return sexAppeal;
        else if (stat == Trait.MONEY)
            return money;
        else
            return -1;
    }

    public boolean isFlirting() {
        return flirting;
    }

    public void setFlirting(boolean flirting) {
        this.flirting = flirting;
    }

    @Override
    public void flirt(Movable enemy) {
        ((Room) this.getLocation()).sendToRoom(getName() + " says, \" " + getRandomSentence() + " \" ");
    }

    public void setClothes(Gear clotheToEquip) {
        if (clotheToEquip instanceof Clothing) {

            if (this.listGear().contains(clotheToEquip)) {
                this.clothes = (Clothing) clotheToEquip;
                removeGear(clotheToEquip);
                this.sendToPlayer("Now you wear a " + clotheToEquip.getName());
            } else {
                this .sendToPlayer("You do not have that garment in your inventory");
            }
            sendToPlayer(stateToJSONObject());
        } else {
            this.sendToPlayer("That is a not a clothing item.");
        }
    }

    @Override
    public void use(String itemName) {
        if (this.gearList.getGear(itemName) != null) {
            this.gearList.getGear(itemName).getDefaultBehavior(this);
            sendToPlayer(stateToJSONObject());
        } else
            this.sendToPlayer("You don't have " + itemName);
    }

    public void receiveMoney(int money) {
        this.money += money;
    }

    public boolean payMoney(int money) {
        if(this.money >= money){
            this.money -= money;
            sendToPlayer(stateToJSONObject());
            return true;
        }else
            return false;
    }

    private int calculateSexAppeal(){
        return (phisique+charisma+beauty+style)/4;
    }

    @Override
    public void sendToPlayer(String message) {
        JSONObject json = new JSONObject();
        json.put("msg", message);
        sendToPlayer(json);
    }

    public void sendToPlayer(JSONObject info) {
        if (client != null) {
            client.sendReply(info);
        }
    }

    public void addLover() {
        loversCount++;
        sendToPlayer(stateToJSONObject());
    }

    public void removeLover() {
        loversCount--;
        sendToPlayer(stateToJSONObject());
    }

    @Override
    public int getRoomId() {
        return this.roomId;
    }

    @Override
    public boolean addGear(Gear item) {
        boolean result = this.addGear(this, item);
        item.setLocation(this);
        sendToPlayer(stateToJSONObject());
        return result;
    }

    @Override
    public boolean addGear(Movable movableToNotify, Gear gear) {
        boolean result = this.gearList.addGear(this, gear);
        gear.setLocation(this);
        sendToPlayer(stateToJSONObject());
        return result;
    }

    @Override
    public void removeGear(Gear gear) {
        this.gearList.removeGear(gear);
        sendToPlayer(stateToJSONObject());
    }

    @Override
    public boolean giveGear(Movable movableToNotify, String gear, String otherName) {

        boolean result = this.gearList.giveGear(movableToNotify, gear, otherName);
        sendToPlayer(stateToJSONObject());

        return result;
    }

    @Override
    public Gear getGear(String itemName) {
        return this.gearList.getGear(itemName);
    }

    @Override
    public boolean canBeCarried() {
        return this.gearList.canBeCarried();
    }

    @Override
    public int getMaxGearCount() {
        return this.gearList.getMaxGearCount();
    }

    @Override
    public void setMaxGearCount(int max) {
        this.gearList.setMaxGearCount(max);

    }

    @Override
    public List<Gear> listGear() {
        return this.gearList.listGear();
    }

    @Override
    public String inspect() {
        return this.gearList.inspect();
    }

    @Override
    public void dropGear(String itemName, Room room, Movable movableToNotify) {
        this.gearList.dropGear(itemName, room, this);
        sendToPlayer(stateToJSONObject());
    }

    @Override
    public String toString() {
        return this.getName() + " (DB:" + this.getDatabaseRef() + ")";
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject player = new JSONObject();
        player.put("name", getName());
        player.put("type", "player");
        player.put("desc", getDescription());
        player.put("image", getImage());
        player.put("stats", statsToJSONObject());
        player.put("gears", gearList.toJSONObject().getJSONArray("gears"));
        return player;
    }

    public JSONObject stateToJSONObject() {
        JSONObject state = new JSONObject();
        state.put("player", toJSONObject());
        state.put("room", ((Room)getLocation()).toJSONObject());
        return state;
    }

    private JSONObject statsToJSONObject() {
        JSONObject stats = new JSONObject();
        stats.put("phisique", phisique);
        stats.put("charisma", charisma);
        stats.put("style", style);
        stats.put("beauty", beauty);
        stats.put("sexAppeal", sexAppeal);
        stats.put("money", money);
        stats.put("lovers", loversCount);
        return stats;
    }

    public void dropGear(String itemName) {
        this.dropGear(itemName, (Room) this.getLocation(), this);
        sendToPlayer(stateToJSONObject());
    }

    public void setCharacterClass(CharacterClass classToSet) {
        if (playerClass != null) {
            this.setStat(beauty - playerClass.getMod(Trait.BEAUTY),
                    Trait.BEAUTY);
            this.setStat(phisique - playerClass.getMod(Trait.PHISIQUE),
                    Trait.PHISIQUE);
            this.setStat(charisma - playerClass.getMod(Trait.CHARISMA),
                    Trait.CHARISMA);
        }

        playerClass = classToSet;

        beauty = beauty + playerClass.getMod(Trait.BEAUTY);
        phisique = phisique + playerClass.getMod(Trait.PHISIQUE);
        charisma = charisma + playerClass.getMod(Trait.CHARISMA);
        sexAppeal = calculateSexAppeal();

        setImage(playerClass.getImage());
        addGear(playerClass.getDefaultClothes());

        sendToPlayer(stateToJSONObject());
        this.sendToPlayer("Your class has been set to " + playerClass.toString());
    }

    /**
     * This method sets a player's password.
     *
     * @param password
     *            - A String that represents the players password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method validates a player's password.
     *
     * @param password
     * @return a boolean, true if player password matches passed parameter
     *         password.
     */
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    /**
     * setClient() associates a Client object with the Player object, allowing
     * methods to send text back to the player.
     *
     * @param client
     *            The Client object to associate with the player object.
     */
    public void setClient(server.Client client) {
        this.client = client;
    }

    @Override
    public void addSentence(String sentence) { }

    @Override
    public String getRandomSentence() {
        return phrases.getRandomSentence();
    }
}
