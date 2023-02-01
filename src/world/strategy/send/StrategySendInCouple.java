package world.strategy.send;

import world.Mobile;
import world.Room;
import world.World;
import world.strategy.StrategyReactToSend;

import java.util.Locale;
import java.util.Scanner;

public class StrategySendInCouple extends StrategyReactToSend {

    private static final long serialVersionUID = -6093630352576037706L;

    @Override
    public void reactToSend(String sent, Mobile mob) {

        Scanner sentStuff = new Scanner(sent);
        String aPlayer = sentStuff.next().trim();
        if (sentStuff.next().trim().equalsIgnoreCase("enters")){
            if (World.getInstance().playerExists(aPlayer) && World.getInstance().playerIsLoggedOn(aPlayer)) {
                if (World.getInstance().getPlayer(aPlayer).getName().toLowerCase(Locale.ROOT)
                        .equalsIgnoreCase(mob.getCouple().getName().trim().toLowerCase(Locale.ROOT)))
                    ((Room) mob.getLocation()).sendToRoom(mob.getName() + " waves at their love");
                else
                    ((Room) mob.getLocation()).sendToRoom(mob.getName() + " see's you, but doesn't appear to be single.");
            }
        }

        sentStuff.close();
    }
}