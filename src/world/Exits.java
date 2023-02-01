package world;

import java.io.Serializable;

public class Exits implements Serializable {

    private static final long serialVersionUID = 1L;
    private Room north;
    private Room east;
    private Room south;
    private Room west;
    private Room up;
    private Room down;

    public Exits() {
        north = null;
        east = null;
        south = null;
        west = null;
        up = null;
        down = null;
    }

    public void setDestination(Direction dir, Room destination) {
        if (dir == Direction.NORTH) {
            north = destination;
        } else if (dir == Direction.EAST) {
            east = destination;
        } else if (dir == Direction.SOUTH) {
            south = destination;
        } else if (dir == Direction.WEST) {
            west = destination;
        } else if (dir == Direction.UP) {
            up = destination;
        } else if (dir == Direction.DOWN) {
            down = destination;
        }

    }

    public Room getDestination(Direction dir) {
        if (dir == Direction.NORTH) {
            return north;
        } else if (dir == Direction.EAST) {
            return east;
        } else if (dir == Direction.SOUTH) {
            return south;
        } else if (dir == Direction.WEST) {
            return west;
        } else if (dir == Direction.UP) {
            return up;
        } else if (dir == Direction.DOWN) {
            return down;
        }
        return null;
    }

    public String getExits() {
        String result = "Exits: ";
        if (north != null)
            result += "north ";
        if (east != null)
            result += "east ";
        if (south != null)
            result += "south ";
        if (west != null)
            result += "west ";
        if (up != null)
            result += "up ";
        if (down != null)
            result += "down ";
        if (result == "Exits: ")
            result += "None";
        return result;
    }

}
