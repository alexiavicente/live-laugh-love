package world.state;

import world.*;
import world.strategy.flirt.StrategyFlirtInCouple;
import world.strategy.send.StrategySendInCouple;

import java.util.Random;

public class StateInCouple extends StateGenericImpl {
    public StateInCouple(Mobile mobile) {
        super(mobile);
        flirt = new StrategyFlirtInCouple();
        send = new StrategySendInCouple();
    }
}
