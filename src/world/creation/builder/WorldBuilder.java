package world.creation.builder;

import world.Direction;
import world.World;

public interface WorldBuilder {

    public World getWorld();

    public void buildRoom(String id, String name, String description, String image, String map);

    public void buildExit(String roomFrom, Direction direction, String roomTo);

    public void nestRooms(String outerRoom, String roomWithin);

    public void addGear(String where, Object gear);

    public void addMobile(String where, Object mobile);

}
