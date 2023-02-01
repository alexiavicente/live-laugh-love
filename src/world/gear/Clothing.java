package world.gear;

import org.json.JSONObject;
import world.Movable;
import world.Trait;
import world.World;
import world.gear.Gear;

public class Clothing extends Gear {

    private int stylePoints;
    private int charismaPoints;

    public Clothing(String name, String description, String image, int stylePoints, int charismaPoint) {
        super(name, description, image, 0);
        this.stylePoints = stylePoints;
        this.charismaPoints = charismaPoint;
    }

    public Clothing(String name, String description, String image, int price,  int stylePoints, int charismaPoint) {
        super(name, description, image, price);
        this.stylePoints = stylePoints;
        this.charismaPoints = charismaPoint;
    }

    @Override
    public void getDefaultBehavior(Movable movable) {
        movable.setStat(movable.getStat(Trait.STYLE) + stylePoints, Trait.STYLE);
        movable.setStat(movable.getStat(Trait.CHARISMA) + charismaPoints, Trait.CHARISMA);
        movable.setClothes(this);
        movable.removeGear(this);
    }

    @Override
    public Gear cloneGear() {
        Gear clone = new Clothing(getName(), getDescription(), getImage(), getPrice(), stylePoints, charismaPoints);
        World.getInstance().addToWorld(clone);
        return clone;
    }
}
