package world.strategy;

import world.Mobile;

import java.io.Serializable;

public abstract class StrategyReactToSend implements Serializable {

    private static final long serialVersionUID = -1486999206981070030L;

    public abstract void reactToSend(String sent, Mobile mob);

}
