package world.gear;

import world.Movable;
import world.World;

public class Gift extends Gear{

    private int relationMod;

    public Gift(String name, String description, String image, int relationMod) {
        super(name, description, image, 0);
        this.relationMod = relationMod;
    }

    public Gift(String name, String description, String image, int price,  int relationMod) {
        super(name, description, image, price);
        this.relationMod = relationMod;
    }

    public int getRelationMod(){
        return relationMod;
    }

    @Override
    public void getDefaultBehavior(Movable movable) {
        movable.sendToPlayer("This object is to give away.");
    }

    @Override
    public Gear cloneGear() {
        Gear clone = new Gift(getName(), getDescription(), getImage(), getPrice(), relationMod);
        World.getInstance().addToWorld(clone);
        return clone;
    }

}
