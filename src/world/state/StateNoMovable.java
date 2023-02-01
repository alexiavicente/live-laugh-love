package world.state;

import world.*;
import world.strategy.flirt.StrategyFlirtNoSeducible;
import world.strategy.flirt.StrategyFlirtSingle;
import world.strategy.send.StrategySendNoSeducible;
import world.strategy.send.StrategySendSingle;

import java.util.Random;

public class StateNoMovable extends StateGenericImpl {
    public StateNoMovable(Mobile mobile) {
        super(mobile);
        flirt = new StrategyFlirtNoSeducible();
        send = new StrategySendNoSeducible();
    }

    public void run() {
        try {
            while (true) {

                Thread.sleep(100000);

                synchronized (World.getInstance().getLockObject()) {
                    while (World.getInstance().threadsLocked()) {
                        try {
                            World.getInstance().getLockObject().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (this.mobile != null && !mobile.isFlirting()) {

                    Room currentRoom = (Room) mobile.getLocation();
                    if (currentRoom == null) {
                        System.out.println("NPC room not sent.");
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
