package world;

import world.creation.factory.GearFactory;
import world.creation.factory.GearFactoryImpl;
import world.gear.Clothing;
import world.gear.Gear;

public class Female extends CharacterClass {

    private static final long serialVersionUID = 1L;

    private Female() {

        setMod(30, Trait.BEAUTY);
        setMod(45, Trait.PHISIQUE);
        setMod(25, Trait.CHARISMA);

        setImage("./resources/players/female.png");

        GearFactory gearFactory = new GearFactoryImpl();
        Clothing dress = gearFactory.buildClothes("Dress", "Fashion dress",
                "./resources/gears/clothing/dress.png", 0, 10, 20);
        World.getInstance().addToWorld(dress);

        setDefaultClothes(dress);
    }

    public static Female getInstance() {
        return new Female();
    }

    public String toString() {
        return "Female";
    }

}
