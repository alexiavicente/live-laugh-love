package world.strategy;

import view.Panel;
import world.Mobile;
import world.Movable;
import world.Player;

import java.io.Serializable;

public abstract class StrategyFlirtBehaviour implements Serializable {

    private static final long serialVersionUID = -7531598655067542151L;

    public abstract void flirtBehavior(Mobile me, Player seductor);
}
