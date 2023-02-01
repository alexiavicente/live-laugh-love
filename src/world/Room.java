package world;

import org.json.JSONArray;
import org.json.JSONObject;
import world.gear.Gear;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Room extends DatabaseObject{

    private static final long serialVersionUID = 1L;
    private List<Gear> gearList;
    private Set<String> movables;
    private Exits exits;
    private String map;

    /**
     * Room will take in a String that will be its name. This name will be
     * passed all the way to DatabaseObject and will be stored there, which will
     * allow for easy access for printing purposes. All of the lists will also
     * be initialized here.
     *
     * @param name
     *            A String that represents the name of the room.
     */
    public Room(String name) {
        super(name);

        this.exits = new Exits();
        this.gearList = new ArrayList<Gear>();
        this.movables = new TreeSet<String>();
    }

    public void setMap(String map) {
        this.map = map;
    }

    public boolean add(Gear gearToAdd) {
        // Remove argument object from its location/parent, if it has one.
        if (gearToAdd.getLocation() != null) {
            if (gearToAdd.getLocation() instanceof Room) {
                ((world.Room) gearToAdd.getLocation()).remove(gearToAdd);
            }
        }

        // Set hew location/parent.
        gearToAdd.setLocation(this);

        // Add to this Object's list & return the success or failure of the add.
        boolean result = gearList.add(gearToAdd);

        JSONObject info = new JSONObject();
        info.put("room", toJSONObject());
        sendToRoom(info);

        return result;
    }

    public boolean add(Movable movableToAdd) {

        // Add to this Object's list & return the success or failure of the add.
        boolean result = this.movables.add(movableToAdd.getName().toLowerCase());

        // Set hew location/parent.
        if (result) {
            System.out.println(movableToAdd.getName() + " added to room " + this.getName());

            if (movableToAdd instanceof Player) {
                this.refreshMobiles();
			}

            JSONObject info = new JSONObject();
            info.put("room", toJSONObject());
            sendToRoom(info);
        }

        return result;
    }

    /**
     * Adds a Movable to the room without notifying the players (used when refreshing the room)
     * */
    public boolean addNoNotify(Movable movableToAdd) {
        System.out.println(movableToAdd.getName() + " added to room " + this.getName());
        return this.movables.add(movableToAdd.getName().toLowerCase());
    }

    public boolean remove(Gear gearToRemove) {
        gearToRemove.setLocation(null);
        boolean result = gearList.remove(gearToRemove);
        JSONObject info = new JSONObject();
        info.put("room", toJSONObject());
        sendToRoom(info);
        return result;
    }
    public boolean remove(String movableNameToRemove) {
        boolean result = this.movables.remove(movableNameToRemove.toLowerCase());
        JSONObject info = new JSONObject();
        info.put("room", toJSONObject());
        sendToRoom(info);
        return result;
    }

    /**
     * Removes a Movable to the room without notifying the players (used when refreshing the room)
     **/
    public boolean removeNoNotify(String movableNameToRemove) {
        return this.movables.remove(movableNameToRemove.toLowerCase());
    }

    public synchronized List<Gear> listGear() {
        return gearList;
    }

    public synchronized List<Movable> listMovables() {
        List<Movable> result = new ArrayList<Movable>();

        for (String name : movables) {

            if (World.getInstance().playerIsLoggedOn(name.toLowerCase())) {
                result.add(World.getInstance().getPlayer(name.toLowerCase()));
            } else if (World.getInstance().mobileExists(name.toLowerCase())) {
                result.add(World.getInstance().getMobile(name.toLowerCase()));
            }
        }

        return result;
    }

    public String generateDescription() {
        String result = null;
        result = "<<" + this.getName() + ">>\n";
        result += this.getDescription() + "\n";
        result += "\n";
        if (!gearList.isEmpty()) {
            result += "Items Detected: ";
            result += gearList.toString();
            result += "\n";
            result += "\n";
        }

        if (!movables.isEmpty()) {
            result += "The following lifeforms are here: ";

            for (Movable movable : this.listMovables()) {
                result += movable.toString() + " ";
            }

            result += "\n";
            result += "\n";
        }

        result += exits.getExits();
        result += "\n";
        return result;
    }

    public void sendToRoom(String toSend) {

        for (Movable mov : this.listMovables()) {
            mov.sendToPlayer(toSend);
        }
    }

    public void sendToRoom(JSONObject toSend) {

        for (Movable mov : this.listMovables()) {
            mov.sendToPlayer(toSend);
        }
    }


    public void sendToRoom(String toSend, Player player) {

        for (Movable mov : this.listMovables()) {
            if (mov != player) {
                System.out.println(mov.getName() + " should receive message.");
                mov.sendToPlayer(toSend);
            }
        }
    }

    public Room getExitDestination(Direction dir) {
        return exits.getDestination(dir);
    }

    public void setExitDestination(Direction dir, Room destination) {
        exits.setDestination(dir, destination);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject room = new JSONObject();
        room.put("name", getName());
        room.put("desc", getDescription());
        room.put("image", getImage());
        room.put("map", map);

        JSONArray movables = new JSONArray();
        for(Movable m: listMovables() )
            movables.put(m.toJSONObject());
        room.put("movables", movables);

        JSONArray gears = new JSONArray();
        for(Gear g: listGear() )
            gears.put(g.toJSONObject());
        room.put("gears", gears);

        return room;
    }

    /**
     * This method refreshes/reestablishes all Mobiles' room assignment to this
     * room.
     */
    public void refreshMobiles() {
        for (Movable movable : this.listMovables()) {
            if (movable instanceof Mobile) {
                Mobile mobile = (Mobile) movable;
                Room room = (Room) World.getInstance().getDatabaseObject(
                        mobile.getRoomId());

                synchronized (World.getInstance()) {
                    room.removeNoNotify(mobile.getName());
                    mobile.setLocation(room);
                    room.addNoNotify(mobile);
                }

                System.out.println("room id: " + room.getDatabaseRef());
                for (Gear gear : mobile.listGear()) {
                    gear = (Gear) World.getInstance().getDatabaseObject(
                            gear.getDatabaseRef());
                    gear.setLocation(mobile);
                }
            }
        }
    }
}
