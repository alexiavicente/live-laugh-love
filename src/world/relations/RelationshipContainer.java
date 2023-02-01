package world.relations;

import world.Movable;
import world.Player;

import java.io.Serializable;

public interface RelationshipContainer extends Serializable {

    public void setRelation(Player player, int value);

    public int getRelation(Player player);

    public Player getCouple();

}
