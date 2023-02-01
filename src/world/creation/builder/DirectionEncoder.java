package world.creation.builder;

import world.Direction;

public class DirectionEncoder {

    public static Direction convertDirection(String direction) {

        Direction dir = null;

        if (direction.equals("north") || direction.equals("n")) {
            dir = Direction.NORTH;
        }

        if (direction.equals("south") || direction.equals("s")) {
            dir = Direction.SOUTH;
        }

        if (direction.equals("east") || direction.equals("e")) {
            dir = Direction.EAST;
        }

        if (direction.equals("west") || direction.equals("w")) {
            dir = Direction.WEST;
        }

        if (direction.equals("up") || direction.equals("u")) {
            dir = Direction.UP;
        }

        if (direction.equals("down") || direction.equals("d")) {
            dir = Direction.DOWN;
        }

        return dir;
    }
}
