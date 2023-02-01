package world.relations;

import world.Mobile;
import world.Player;
import world.state.StateInCouple;
import world.state.StateSingle;

import java.util.HashMap;
import java.util.Map;

public class RelationshipList implements RelationshipContainer {

    private Map<Player, Integer> list;

    private Mobile me;
    private Player couple;

    public RelationshipList(Mobile movable) {
        list = new HashMap<>();
        me = movable;
    }

    public int getRelation(Player player) {
        if (list.containsKey(player))
            return list.get(player);
        return  0;
    }

    public Player getCouple() {
        return couple;
    }

    public void setRelation(Player player, int value) {

        boolean modif = false;
        Relationship original = null;

        if (list.containsKey(player)) {
            int n = list.get(player);
            list.replace(player, n + value);
            original = Relationship.getRelation(n);

        } else {
             list.put(player, value);
             modif = true;
        }

        if(list.get(player) < 0)
            list.replace(player, 0);

        else if(list.get(player) > 100)
            list.replace(player, 100);

        if (couple != null && list.get(couple) < 80) {
            couple = null;
            player.removeLover();
            me.setState(new StateSingle(me));
        }

        if(list.get(player) >= 80) {
            if (player != couple) {
                ((Player)player).addLover();
                couple = player;
            }
            me.setState(new StateInCouple(me));
        }

        if (modif || original != Relationship.getRelation(list.get(player)))
            player.sendToPlayer("Now, " + player.getName()+ " is a " + Relationship.getRelation(list.get(player))
                + " of " +  me.getName() );
    }
}
