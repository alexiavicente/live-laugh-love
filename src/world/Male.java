package world;

import world.creation.factory.GearFactory;
import world.creation.factory.GearFactoryImpl;
import world.gear.Clothing;

public class Male extends CharacterClass {

    private static final long serialVersionUID = 1L;

    private Male() {
        setMod(30, Trait.BEAUTY);
        setMod(45, Trait.PHISIQUE);
        setMod(25, Trait.CHARISMA);

        setImage("./resources/players/male.png");

        GearFactory gearFactory = new GearFactoryImpl();
        Clothing suit = gearFactory.buildClothes("Suit", "Elegant suit",
                "./resources/gears/clothing/suit.png", 0, 10, 20);
        World.getInstance().addToWorld(suit);

        setDefaultClothes(suit);

    }

    public static Male getInstance() {
        return new Male();
    }

    public String toString() {
        return "Male";
    }

}
