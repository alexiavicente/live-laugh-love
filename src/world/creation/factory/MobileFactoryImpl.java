package world.creation.factory;

import world.Mobile;
import world.state.*;

public class MobileFactoryImpl implements MobileFactory{
    @Override
    public Mobile buildMobile(String name, String image, MobileState state) {

        Mobile mobile = new Mobile(name);
        mobile.setImage(image);

        State mobileState = null;

        switch (state) {
            case Single -> mobileState = new StateSingle(mobile);
            case InCouple -> mobileState = new StateInCouple(mobile);
            default -> mobileState = new StateNoMovable(mobile);
        }

        mobile.setState(mobileState);

        return mobile;
    }
}
