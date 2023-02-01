package world.creation.factory;

import world.gear.Clothing;
import world.gear.Food;
import world.gear.Gift;
import world.gear.PersonalGear;

public class GearFactoryImpl implements GearFactory {
    @Override
    public Clothing buildClothes(String name, String description, String image, int price, int charisma, int style) {
        return new Clothing(name, description, image, price, style, charisma);
    }

    @Override
    public Food buildFood(String name, String description, String image, int price, int physiqueMod) {
        return new Food(name, description, image, price, physiqueMod);
    }

    @Override
    public Gift buildGift(String name, String description, String image, int price, int relationMod) {
        return new Gift(name, description, image, price, relationMod);
    }

    @Override
    public PersonalGear buildPersonalGear(String name, String description, String image, int price) {
        return new PersonalGear(name, description, image, price);
    }


}
