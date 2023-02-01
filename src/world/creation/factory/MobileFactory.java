package world.creation.factory;

import world.Mobile;
import world.state.MobileState;
import world.state.State;

public interface MobileFactory {

    public Mobile buildMobile(String name, String image, MobileState state);

}
