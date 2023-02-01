package world.gear;

import org.json.JSONObject;
import world.Movable;
import world.Player;
import world.Trait;
import world.World;

public class Food extends Gear {

    private int phisiqueMod;

    public Food(String name, String description, String image, int phisiqueMod) {
        super(name, description, image, 0);
        this.phisiqueMod = phisiqueMod;
        this.setPrice(0);
    }

    public Food(String name, String description, String image, int price, int phisiqueMod) {
        super(name, description, image, price);
        this.phisiqueMod = phisiqueMod;
    }

    @Override
    public void getDefaultBehavior(Movable movable) {
        movable.setStat(movable.getStat(Trait.PHISIQUE) + phisiqueMod, Trait.PHISIQUE);
        movable.sendToPlayer("You eat " + this.getName());
        movable.removeGear(this);
    }

    @Override
    public Gear cloneGear() {
        Gear clone = new Food(getName(), getDescription(), getImage(), getPrice(), phisiqueMod);
        World.getInstance().addToWorld(clone);
        return clone;
    }
}
