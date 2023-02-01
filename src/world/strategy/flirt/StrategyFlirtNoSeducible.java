package world.strategy.flirt;

import view.Panel;
import world.Mobile;
import world.Movable;
import world.Player;
import world.Room;
import world.strategy.StrategyFlirtBehaviour;

import java.util.ArrayList;
import java.util.Random;

public class StrategyFlirtNoSeducible extends StrategyFlirtBehaviour {

    @Override
    public void flirtBehavior(Mobile me, Player seducer) {
        Random r = new Random();
        ((Room) me.getLocation()).sendToRoom(me.getName() + " says, \" " + me.getRandomSentence() + " \" ");
    }
}
