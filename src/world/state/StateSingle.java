package world.state;

import world.Mobile;
import world.Player;
import world.strategy.flirt.StrategyFlirtSingle;
import world.strategy.send.StrategySendInCouple;
import world.strategy.send.StrategySendSingle;

public class StateSingle extends StateGenericImpl {

    private static final long serialVersionUID = -7554048009829308018L;

    public StateSingle(Mobile mobile) {
        super(mobile);
        flirt = new StrategyFlirtSingle();
        send = new StrategySendSingle();
    }
}
