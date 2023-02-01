package world;

import world.relations.Relationship;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class FlirtManager {

    private FlirtManager.FlirtTimerListener attackTimerListener = new FlirtManager.FlirtTimerListener();

    private Timer attackTimer;
    private Timer attackTimer2;

    private Player flirterOne;
    private Mobile flirterTwo;

    private Relationship initialRelation;

    private int rounds;

    public FlirtManager(Player flirter1, Mobile flirter2) {

        this.flirterOne = flirter1;
        this.flirterTwo = flirter2;
        this.rounds = 0;

        ((Room)this.flirterOne.getLocation()).sendToRoom(flirterOne.getName() + " has started flirting ");

        this.flirterOne.setFlirting(true);
        this.flirterTwo.setFlirting(true);

        flirterTwo.setRelation(flirterOne, 0);

        initialRelation = Relationship.getRelation(flirterTwo.getRelation(flirterOne));

        attackTimer = new Timer(3000, attackTimerListener);

        flirterOne.sendToPlayer("You started flirting with " + flirter2.getName());

        flirterOne.flirt(flirterTwo);
        flirterTwo.flirt(flirterOne);

        attackTimer.start();
    }

    public boolean isFighting() {
        attackTimer.setDelay(3000);
        rounds++;

        if (!World.getInstance().playerIsLoggedOn(flirterOne.getName())){
            attackTimer.stop();
            this.flirterOne.setFlirting(false);
            this.flirterTwo.setFlirting(false);
            return false;
        }

        if (Relationship.getRelation(flirterTwo.getRelation(flirterOne)) == Relationship.lover){
            flirterOne.sendToPlayer(flirterOne.getName() + " has seduced " + flirterTwo.getName() + " <3");
            attackTimer.stop();
            this.flirterOne.setFlirting(false);
            this.flirterTwo.setFlirting(false);
            return false;
        }

        if(flirterOne.getRoomId() != flirterTwo.getRoomId()){
            attackTimer.stop();
            this.flirterOne.setFlirting(false);
            this.flirterTwo.setFlirting(false);
            return false;
        }

        if(rounds > 3){
            flirterOne.sendToPlayer("You stopped flirting with " + flirterTwo.getName());
            attackTimer.stop();
            this.flirterOne.setFlirting(false);
            this.flirterTwo.setFlirting(false);
            return false;
        }

        return true;
    }


    private class FlirtTimerListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (isFighting()){
                flirterOne.flirt(flirterTwo);
                flirterTwo.flirt(flirterOne);
            } else {
                attackTimer.stop();
            }
        }
    }
}