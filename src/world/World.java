package world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import world.gear.Gear;
import world.gear.GearContainer;
public class World implements Runnable {

    private transient static World instance;
    private transient Thread saveThread;
    private transient boolean threadsLocked;
    private transient Object lockObject = new Object();

    private List<DatabaseObject> databaseArray = new ArrayList<DatabaseObject>();
    private int objectNumbers;
    private Map<String, Player> players = new HashMap<String, Player>();
    private Map<String, Mobile> mobiles = new HashMap<String, Mobile>();
    private Set<String> playersLoggedOn = new TreeSet<String>();

    // This private constructor will initialize necessary variables.
    private World() {
        this.saveThread = new Thread(this);
        this.saveThread.start();
    }

    /**
     * This method returns the Singleton instance of the world.
     *
     * @return - The Instance of the World
     */
    public static World getInstance() {

        if(instance == null) {
            instance = new World();
        }
        return instance;
    }

    public boolean addToWorld(DatabaseObject toAdd) {
        boolean result = databaseArray.add(toAdd);
        if (result) {
            toAdd.setDatabaseRef(objectNumbers);
            objectNumbers++;
            System.out.println(toAdd.getName() + " added to world.");
        }
        return result;
    }

    public boolean removeFromWorld(DatabaseObject toRemove) {
        return databaseArray.remove(toRemove);
    }

    public void replaceWithNew(DatabaseObject toRemove, DatabaseObject toAdd) {
        databaseArray.remove(toRemove.getDatabaseRef());
        databaseArray.add(toRemove.getDatabaseRef(), toAdd);
    }


    public DatabaseObject getDatabaseObject(int objectID) {
        for (DatabaseObject dbobj : databaseArray) {
            if (dbobj.getDatabaseRef() == objectID)
                return dbobj;
        }
        return null;

    }

    public List<Player> getPlayers() {

        List<Player> result = new ArrayList<Player>();

        for (Player player : this.players.values()) {
            result.add(player);
        }

        return result;
    }

    public boolean playerExists(String name) {
        return this.players.containsKey(name.toLowerCase());
    }

    public boolean nameExists(String newName) {
        for (DatabaseObject worldObject : getDatabaseObjects()) {
            if (newName.equalsIgnoreCase(worldObject.getName())) {
                return true;
            }
        }
        return false;
    }

    public Player createPlayer(String name, String password) {

        if (this.playerExists(name))
            return null;

        Player temp = new Player(name);
        temp.setPassword(password);
        temp.setLocation((Room) World.getInstance().getDatabaseObject(0));
        if (this.players.put(temp.getName().toLowerCase(), temp) == null) {
            World.getInstance().addToWorld(temp);
            return temp;
        }
        return temp;
    }

    public Mobile createMobile(Mobile temp) {

        if (this.nameExists(temp.getName().toLowerCase()))
            return null;
        if (this.mobiles.put(temp.getName().toLowerCase(), temp) == null) {
            World.getInstance().addToWorld(temp);
            return temp;
        }

        return null;
    }

    public void addGearToWorld(Gear gear, DatabaseObject location) {
        World.getInstance().addToWorld(gear);
        if (location instanceof Room) {
            ((Room) location).add(gear);
            return;
        }

        if (location instanceof GearContainer) {
            ((GearContainer) location).addGear(gear);
        }

        if (location instanceof Player) {
            ((Player) location).addGear(gear);
        }
        if (location instanceof Mobile) {
            ((Mobile) location).addGear(gear);
        }
        gear.setLocation(location);
    }

    public List<Mobile> getMobiles() {
        List<Mobile> result = new ArrayList<Mobile>();

        for (Mobile mobile : this.mobiles.values()) {
            result.add(mobile);
        }

        return result;
    }

    public Mobile getMobile(String name) {
        return this.mobiles.get(name.toLowerCase());
    }

    public boolean mobileExists(String name) {
        return this.mobiles.containsKey(name);
    }

    public boolean confirmPlayer(String name, String password) {
        if (!this.playerExists(name)) {
            return false;
        }
        return this.getPlayer(name).validatePassword(password);
    }

    public Player getPlayer(String name) {
        return this.players.get(name.toLowerCase());
    }

    @SuppressWarnings("unchecked")
    public void loadWorld() throws WorldNotFoundException {
        boolean loadedFromFile = false;

        try {
            // load database objects
            FileInputStream dFis = new FileInputStream("TripleLWorld\\databaseArray.txt");
            ObjectInputStream dIn = new ObjectInputStream(dFis);
            this.databaseArray = (List<DatabaseObject>) dIn.readObject();
            this.objectNumbers = this.databaseArray.size();

            // load player list.
            FileInputStream pFis = new FileInputStream("TripleLWorld\\players.txt");
            ObjectInputStream pIn = new ObjectInputStream(pFis);
            this.players = (Map<String, Player>) pIn.readObject();

            // load mobile list.
            FileInputStream mFis = new FileInputStream("TripleLWorld\\mobiles.txt");
            ObjectInputStream mIn = new ObjectInputStream(mFis);
            this.mobiles = (Map<String, Mobile>) mIn.readObject();

            // reset player objects.
            for (Player player : this.getPlayers()) {
                Player temp = loadPlayer(player.getName().toLowerCase());
                if (temp != null) {
                    player = temp;
                }

                players.replace(temp.getName().toLowerCase(), temp);
            }

            // reset mobile objects.
            for (Mobile mobile : this.getMobiles()) {
                System.out.println(mobile.getName());
                Mobile temp = (Mobile) this.getDatabaseObject(mobile.getDatabaseRef());
                temp.restartState();
                mobiles.replace(temp.getName().toLowerCase(), temp);
                mobile = temp;
                Room room = (Room) this.getDatabaseObject(mobile.getRoomId());
                room.remove(mobile.getName());
                mobile.setLocation(room);
                room.add(mobile);
                System.out.println("room id: " + room.getDatabaseRef());
                for (Gear gear : mobile.listGear()) {
                    gear = (Gear) this.getDatabaseObject(gear.getDatabaseRef());
                    gear.setLocation(mobile);
                }
            }

            loadedFromFile = true;
        } catch (FileNotFoundException e) {
            System.out
                    .println("World file(s) not found."
                            + e.getMessage());
        } catch (IOException e) {
            System.out
                    .println("World file(s) not reading correctly."
                            + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out
                    .println("World file(s) not casting correctly."
                            + e.getMessage());
        }

        if(!loadedFromFile){
            throw new WorldNotFoundException();
        }
    }
    /**
     * The printAll() method is used to help test game saves and loads.
     */
    public void printAll() {
        for (DatabaseObject DB : databaseArray) {
            System.out.println(DB.getName() + "|" + DB.getDatabaseRef() + "|"
                    + DB.getDescription());
        }
    }

    /**
     * lockThreads is used when ever the certain threads need stopped.It will
     * set the threadsLocked variable to true.
     */
    public void lockThreads() {
        this.threadsLocked = true;
    }

    /**
     * threadsLocked() will simply return true if the threads have been locked.
     * This will be contained in the boolean threadsLocked variable.
     *
     * @return True if threads are locked and false otherwise.
     */
    public boolean threadsLocked() {
        return threadsLocked;
    }

    /**
     * unlockThreads() will be called upon to take all locked threads and unlock
     * them. This will also make use of the lockObject instance variable.
     */
    public void unlockThreads() {
        synchronized (lockObject) {
            this.threadsLocked = false;
            this.lockObject.notifyAll();
        }
    }

    /**
     * Returns the lock object for the World instance.
     *
     * @return - An object used as a lock for threads.
     */
    public Object getLockObject() {
        return this.lockObject;
    }

    /**
     * The run method saves the world every 100 seconds.
     */
    @Override
    public void run() {
        boolean justStarted = true;
        try {
            for (;;) {

                Thread.sleep(60100);

                synchronized (this.getLockObject()) {
                    while (this.threadsLocked()) {
                        try {
                            this.getLockObject().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                for (Player player : this.getPlayers()) {
                    player.receiveMoney(100);
                }

                for (Player player : this.getPlayers()) {
                    player.setStat(player.getStat(Trait.PHISIQUE) - 1, Trait.PHISIQUE);
                    player.setStat(player.getStat(Trait.CHARISMA) - 1, Trait.CHARISMA);
                    player.setStat(player.getStat(Trait.STYLE) - 1, Trait.STYLE);
                    player.setStat(player.getStat(Trait.BEAUTY) - 1, Trait.BEAUTY);
                }

                if (!justStarted) {
                    this.saveWorld();
                }
                if (justStarted) {
                    justStarted = false;
                }
                System.out.println("Save");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The saveWorld() method saves the world instance.
     */
    public void saveWorld() {
        this.lockThreads();

        File databaseArray = new File("TripleLWorld\\databaseArray.txt");

        try {
            FileOutputStream fos;
            fos = new FileOutputStream(databaseArray);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this.databaseArray);
            out.flush();
            out.close();
            fos.close();
            this.unlockThreads();
        } catch (FileNotFoundException e) {
            File folder1 = new File("TripleLWorld");
            folder1.mkdir();
            File folder2 = new File("TripleLWorld\\TripleLPlayers");
            folder2.mkdir();
            this.unlockThreads();
        } catch (IOException e) {
            this.unlockThreads();
            e.printStackTrace();
        }

        this.lockThreads();
        File players = new File("TripleLWorld\\players.txt");
        try {
            FileOutputStream pFos;
            pFos = new FileOutputStream(players);
            ObjectOutputStream pOut = new ObjectOutputStream(pFos);
            pOut.writeObject(this.players);
            pOut.flush();
            pOut.close();
            pFos.close();
            this.unlockThreads();
        } catch (FileNotFoundException e) {
            File folder1 = new File("TripleLWorld");
            folder1.mkdir();
            File folder2 = new File("TripleLWorld\\TripleLPlayers");
            folder2.mkdir();
            this.unlockThreads();
        } catch (IOException e) {
            this.unlockThreads();
            e.printStackTrace();
        }

        this.lockThreads();
        File mobiles = new File("TripleLWorld\\mobiles.txt");
        try {
            FileOutputStream mfos;
            mfos = new FileOutputStream(mobiles);
            ObjectOutputStream mOut = new ObjectOutputStream(mfos);
            mOut.writeObject(this.mobiles);
            mOut.flush();
            mOut.close();
            mfos.close();
            this.unlockThreads();
        } catch (FileNotFoundException e) {
            File folder1 = new File("TripleLWorld");
            folder1.mkdir();
            File folder2 = new File("TripleLWorld\\TripleLPlayers");
            folder2.mkdir();
            this.unlockThreads();
        } catch (IOException e) {
            this.unlockThreads();
            e.printStackTrace();
        }

        this.savePlayers();

        System.out.println("Game saved");
    }

    /**
     * The save player method saves a singled player instance.
     *
     * @param player
     *            - The Player object to save.
     */
    public boolean savePlayer(Player player) {
        this.lockThreads();

        File playerFile = new File("TripleLWorld\\TripleLPlayers\\"
                + player.getName().toLowerCase() + ".txt");

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(playerFile);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(player);
            out.flush();
            out.close();
            fos.close();
            this.unlockThreads();
            return true;
        } catch (FileNotFoundException e) {
            this.unlockThreads();
            e.printStackTrace();
        } catch (IOException e) {
            this.unlockThreads();
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The savePlayers() method saves all players in the player list.
     */
    public void savePlayers() {
        for (Player player : this.getPlayers()) {
            this.savePlayer(player);
        }
    }

    /**
     * This method lists the DatabaseObjects.
     *
     * @return - A List that represents the database objects.
     */
    public List<DatabaseObject> getDatabaseObjects() {
        return this.databaseArray;
    }

    /**
     * loadPlayer will take in a String that is a players name and attempt to
     * load that player from saved files.
     *
     * @param name
     *            Player name that is attempted to be loaded
     * @return The player with the string parameter or null if it doesn't exist
     */
    public Player loadPlayer(String name) {

        // return this.getPlayer(name);
        try {
            FileInputStream pfis = new FileInputStream(
                    "TripleLWorld\\TripleLPlayers\\" + name.toLowerCase() + ".txt");
            ObjectInputStream pIn = new ObjectInputStream(pfis);
            Player temp = (Player) pIn.readObject();
            if (temp != null) {
                this.replaceWithNew(this.getDatabaseObject(temp
                        .getDatabaseRef()), temp);
                Room room = (Room) this.getDatabaseObject(temp.getRoomId());
                room.remove(temp.getName());
                temp.setLocation(room);
                room.add(temp);
                System.out.println("room id: " + room.getDatabaseRef());
                this.players.remove(name.toLowerCase());
                this.players.put(name.toLowerCase(), temp);
                for (Gear gear : temp.listGear()) {
                    gear = (Gear) this.getDatabaseObject(gear.getDatabaseRef());
                }
                return temp;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Player file not found for " + name + ".");
        } catch (IOException e) {
            System.out.println("Player file not read for " + name + ".");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found for " + name + ".");
        }

        return null;
    }

    /**
     * addLoggedOn will take in a string and add it to a list that contains only
     * the players that are currently logged on to the TripleL server.
     *
     * @param name
     *            The player name that logged on
     * @return True if the add was successful, false otherwise
     */
    public boolean addLoggedOn(String name) {
        return this.playersLoggedOn.add(name.toLowerCase());
    }

    /**
     * removeLoggedOn will take in a string of a player that just logged off and
     * remove it from the list of players that are logged on.
     *
     * @param name
     *            Name of the player that logged off
     * @return True if the remove was successful, false otherwise
     */
    public boolean removeLoggedOn(String name) {
        return this.playersLoggedOn.remove(name.toLowerCase());
    }

    /**
     * playerIsLoggedOn will take in a string, and use the string to determine
     * if that player is currently logged on to the TripleL server.
     *
     * @param name
     *            The name of the player
     * @return True if player is logged on, false otherwise
     */
    public boolean playerIsLoggedOn(String name) {
        return this.playersLoggedOn.contains(name.toLowerCase());
    }

    /**
     * getPlayersLoggedOn will take the current set of players that are logged
     * on to the TripleL server and return it.
     *
     * @return The set of players logged on
     */
    public Set<String> getPlayersLoggedOn() {
        return this.playersLoggedOn;
    }
}
