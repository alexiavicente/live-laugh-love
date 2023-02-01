package world.strategy.send;

import world.Mobile;
import world.Room;
import world.World;
import world.relations.Relationship;
import world.strategy.StrategyReactToSend;

import java.util.Scanner;

public class StrategySendSingle extends StrategyReactToSend {

    private static final long serialVersionUID = -6093630352576037706L;

    @Override
    public void reactToSend(String sent, Mobile mob) {

        Scanner sentStuff = new Scanner(sent);
        String aPlayer = sentStuff.next().trim();
        String nextWord = sentStuff.next().trim();
        if (nextWord.equalsIgnoreCase("enters")) {
            if (World.getInstance().playerExists(aPlayer)) {
                ((Room) mob.getLocation()).sendToRoom(mob.getName() + " waves to " +
                        Relationship.getRelation(mob.getRelation(World.getInstance().getPlayer(aPlayer))) +
                        " " + aPlayer + ".");
            }
        }

        sentStuff.close();
    }
}