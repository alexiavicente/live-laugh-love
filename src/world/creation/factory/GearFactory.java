package world.creation.factory;

import world.gear.Clothing;
import world.gear.Food;
import world.gear.Gift;
import world.gear.PersonalGear;

public interface GearFactory {

    public Clothing buildClothes(String name, String description, String image, int price,  int charisma, int style);

    public Food buildFood(String name, String description, String image, int price, int physiqueMod);

    public Gift buildGift(String name, String description, String image, int price, int relationMod);

    public PersonalGear buildPersonalGear(String name, String description, String image, int price);
}
