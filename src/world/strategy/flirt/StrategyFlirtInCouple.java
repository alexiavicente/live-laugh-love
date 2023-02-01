package world.strategy.flirt;

import world.Mobile;
import world.Player;
import world.Room;
import world.Trait;
import world.strategy.StrategyFlirtBehaviour;

import java.util.Random;

public class StrategyFlirtInCouple extends StrategyFlirtBehaviour {

    private static String answersNoCouple[] = {
            "Oh sorry, I am in couple",
            "Thank you! But it's better if we're just friends",
            "no hablo inglis",
            "haha no",
            "Thanks but you're ugly", 
            "Aww that's sweet ",
            "You remind me of my aunt Maria del Carmen",
            "You're like a sibling to me",
            "ok",
            "Ik vind je moeder leuker dan jij, het spijt me",
            "Sorry im dating someone already",
            "Come back when you are rich",
            "No thanks",
            "That's gay",
            "I'm not interested",
            "That's funny!",
            "Bruh",
            "You are very nice!"
    };

    private static String answersCouple[] = {
            "You are my love!"
    };

    @Override
    public void flirtBehavior(Mobile me, Player seducer) {
        int flirtEfecct =  seducer.getStat(Trait.SEXAPEAL)/3;

        Random r = new Random();

        if (me.getCouple() != null && seducer != me.getCouple()) {
            ((Room) me.getLocation()).sendToRoom(me.getName() + " says, \" " + answersNoCouple[r.nextInt(answersNoCouple.length)]+ " \" ");
            Player couple = me.getCouple();
            me.setRelation(couple, -flirtEfecct);
            System.out.println("Relation with couple " + couple.getName() + ": " + me.getRelation(me.getCouple()));
            me.setRelation(seducer, (int) (flirtEfecct/2));
            System.out.println("Relation with " + seducer.getName() + ": " + me.getRelation(seducer));
        } else if (seducer == me.getCouple()) {
            ((Room) me.getLocation()).sendToRoom(me.getName() + " says, \" " + answersCouple[r.nextInt(answersCouple.length)]+ " \" ");
        }


    }
}