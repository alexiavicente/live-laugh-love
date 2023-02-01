package world.gear;

import world.Movable;

public class PersonalGear extends Gear{
    public PersonalGear(String name, String description, String image) {
        super(name, description, image);
    }

    public PersonalGear(String name, String description, String image, int price) {
        super(name, description, image, price);
    }

    @Override
    public void getDefaultBehavior(Movable movable) {
        movable.sendToPlayer("You are using your " + getName() + ".");
    }

    @Override
    public Gear cloneGear() {
        return new PersonalGear(getName(), getDescription(), getImage() ,getPrice());
    }
}
