package world.strategy.flirt;

import world.*;
import world.strategy.StrategyFlirtBehaviour;

import java.util.Random;

public class StrategyFlirtSingle extends StrategyFlirtBehaviour {

	private static String answers[] = {
            "Oh you are very pretty",
            "Thank you!",
            "I liked that",
            "Hahaha you're cute ;)",
            " Tell me more",
            "That was smooth ;)",
            "*blushes*",
            "uwu",
            "<3",
            "I like you",
            "I adore you",
            "I love you"
    };

    @Override
    public void flirtBehavior(Mobile me, Player seducer) {
        int flirtEfecct =  computeEffect(seducer);

        Random r = new Random();
        ((Room) me.getLocation()).sendToRoom(me.getName() + " says, \" " + answers[r.nextInt(answers.length)]+ " \" ");

        me.setRelation(seducer, flirtEfecct);

        System.out.println("Relation with " + seducer.getName() + ": " + me.getRelation(seducer));
    }

    private int computeEffect(Player seducer) {
        return seducer.getStat(Trait.SEXAPEAL)/3;
    }
}
