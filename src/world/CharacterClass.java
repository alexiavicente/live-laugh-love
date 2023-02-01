package world;

import world.Trait;
import world.gear.Gear;

import java.io.Serializable;

public abstract class CharacterClass implements Serializable {

    private static final long serialVersionUID = 1L;
    private int phisiqueMod;
    private int beautyMod;
    private int charismaMod;
    private String image;
    private Gear defaultClothes;

    protected void setMod(int value, Trait stat) {
        if (stat == Trait.CHARISMA)
            charismaMod = value;
        else if (stat == Trait.PHISIQUE)
            phisiqueMod = value;
        else if (stat == Trait.BEAUTY)
            beautyMod = value;
    }

    public int getMod(Trait stat) {
        if (stat == Trait.CHARISMA)
            return charismaMod;
        else if (stat == Trait.PHISIQUE)
            return phisiqueMod;
        else if (stat == Trait.BEAUTY)
            return beautyMod;
        else
            return -1;
    }

    protected void setImage(String image) {
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public Gear getDefaultClothes() {
        return defaultClothes.cloneGear();
    }

    public void setDefaultClothes(Gear defaultClothes) {
        this.defaultClothes = defaultClothes;
    }
}
