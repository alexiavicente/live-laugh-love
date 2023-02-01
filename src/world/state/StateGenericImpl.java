package world.state;

import world.*;
import world.strategy.StrategyFlirtBehaviour;
import world.strategy.StrategyReactToSend;
import world.strategy.flirt.StrategyFlirtNoSeducible;
import world.strategy.send.StrategySendNoSeducible;
import world.strategy.send.StrategySendSingle;

import java.util.Random;

public class StateGenericImpl extends State implements Runnable {

    private static final long serialVersionUID = -5403231979587631263L;

    protected transient Thread thread;
    protected Mobile mobile;

    StrategyFlirtBehaviour flirt;
    StrategyReactToSend send;

    public StateGenericImpl() { }

    public StateGenericImpl(Mobile mobile) {
        this.mobile = mobile;
        this.thread = new Thread(this);
        this.thread.start();

        flirt = new StrategyFlirtNoSeducible();
        send = new StrategySendNoSeducible();
    }

    public void restart(){
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public State cloneState() {
        return new StateGenericImpl();
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    @Override
    public void flirtBehavior(Mobile me, Player seducer) {
        flirt.flirtBehavior(me, seducer);
    }

    @Override
    public void reactToSend(String sent, Mobile mob) {
        send.reactToSend(sent, mob);
    }

    public void run() {
        try {
            while (true) {

                Thread.sleep(60000);

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

                    boolean moved = false;
                    while (!moved) {
                        Random timeToMove = new Random();
                        int moveNum = timeToMove.nextInt(5);

                        switch (moveNum) {
                            case 0:
                                if (((Room) this.mobile.getLocation())
                                        .getExitDestination(Direction.NORTH) != null) {

                                    ((Room) this.mobile.getLocation())
                                            .sendToRoom(this.mobile.getName()
                                                    + " exits North.");
                                    this.mobile.moveToRoom(((Room) this.mobile
                                            .getLocation())
                                            .getExitDestination(Direction.NORTH));
                                    ((Room) this.mobile.getLocation())
                                            .sendToRoom(this.mobile.getName()
                                                    + " arrives from the South.");
                                    moved = true;
                                }
                                break;
                            case 1:
                                if (((Room) this.mobile.getLocation())
                                        .getExitDestination(Direction.SOUTH) != null) {

                                    ((Room) this.mobile.getLocation())
                                            .sendToRoom(this.mobile.getName()
                                                    + " exits South.");
                                    this.mobile.moveToRoom(((Room) this.mobile
                                            .getLocation())
                                            .getExitDestination(Direction.SOUTH));
                                    ((Room) this.mobile.getLocation())
                                            .sendToRoom(this.mobile.getName()
                                                    + " arrives from the North.");
                                    moved = true;
                                }
                                break;
                            case 2:
                                if (((Room) this.mobile.getLocation())
                                        .getExitDestination(Direction.EAST) != null) {

                                    ((Room) this.mobile.getLocation())
                                            .sendToRoom(this.mobile.getName()
                                                    + " exits East.");
                                    this.mobile.moveToRoom(((Room) this.mobile
                                            .getLocation())
                                            .getExitDestination(Direction.EAST));
                                    ((Room) this.mobile.getLocation())
                                            .sendToRoom(this.mobile.getName()
                                                    + " arrives from the West.");
                                    moved = true;
                                }
                                break;
                            case 3:
                                if (((Room) this.mobile.getLocation())
                                        .getExitDestination(Direction.WEST) != null) {

                                    ((Room) this.mobile.getLocation())
                                            .sendToRoom(this.mobile.getName()
                                                    + " exits West.");
                                    this.mobile.moveToRoom(((Room) this.mobile
                                            .getLocation())
                                            .getExitDestination(Direction.WEST));
                                    ((Room) this.mobile.getLocation())
                                            .sendToRoom(this.mobile.getName()
                                                    + " arrives from the East.");
                                    moved = true;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
