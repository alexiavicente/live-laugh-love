package world.state;

import world.Mobile;
import world.Movable;
import world.Player;

import java.io.Serializable;

public abstract class State implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract void reactToSend(String sent, Mobile mob);

    public abstract void flirtBehavior(Mobile me, Player seducer);

    public abstract State cloneState();

    public abstract void restart();

}
