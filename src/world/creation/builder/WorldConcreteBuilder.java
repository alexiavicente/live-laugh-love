package world.creation.builder;

import world.*;
import world.gear.Gear;
import world.gear.GearContainer;

import java.util.HashMap;
import java.util.Map;

public class WorldConcreteBuilder implements WorldBuilder {

    private World worldBeingBuilt;

    Map<String, Room> roomSet;
    Map<String, GearContainer> gearContainerSet;
    Map<String, Mobile> mobileSet;

    public WorldConcreteBuilder() {
        worldBeingBuilt =  World.getInstance();

        roomSet = new HashMap<String,Room>();
        gearContainerSet = new HashMap<String,GearContainer>();
        mobileSet = new HashMap<String,Mobile>();
    }

    @Override
    public World getWorld() {
        return worldBeingBuilt;
    }

    @Override
    public void buildRoom(String id, String name, String description, String image, String map) {
        Room room = new Room(name);
        room.setDescription(description);
        room.setImage(image);
        room.setMap(map);
        roomSet.put(id, room);
        worldBeingBuilt.addToWorld(room);
    }

    @Override
    public void buildExit(String roomFrom, Direction direction, String roomTo) {
        Room room1 = roomSet.get(roomFrom);
        Room room2 = roomSet.get(roomTo);

        room1.setExitDestination(direction, room2);
    }

    @Override
    public void nestRooms(String outerRoom, String roomWithin) {

    }

    @Override
    public void addGear(String where, Object gear) {

        worldBeingBuilt.addToWorld((Gear)gear);
        Room room = roomSet.get(where);

        if(room != null) {
            room.add((Gear)gear);
            if(gear instanceof GearContainer) {
                GearContainer container = (GearContainer)gear;
                gearContainerSet.put(container.getName(), container);
            }
        }
        else {
            GearContainer container = gearContainerSet.get(where);
            if(container != null) {
                container.addGear((Gear)gear);
            }
            else {
                Mobile mobile = mobileSet.get(where);
                if(mobile != null) {
                    mobile.addGear((Gear)gear);
                }
                else { }
            }
        }
    }

    @Override
    public void addMobile(String where, Object mobile) {

        Room room = roomSet.get(where);

        if(room != null) {

            if(mobile instanceof Mobile) {
                Mobile mobileRep = (Mobile)mobile;
                mobileSet.put(mobileRep.getName(), mobileRep);

                mobileRep.setLocation(room);
                mobileRep.moveToRoom(room);

                worldBeingBuilt.createMobile(mobileRep);
            }
        }
    }
}
