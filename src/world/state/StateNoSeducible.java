package world.state;

import world.Mobile;
import world.strategy.flirt.StrategyFlirtNoSeducible;
import world.strategy.send.StrategySendNoSeducible;

public class StateNoSeducible extends StateGenericImpl{

    public StateNoSeducible(Mobile mobile) {
        super(mobile);
        flirt = new StrategyFlirtNoSeducible();
        send = new StrategySendNoSeducible();
    }
}
