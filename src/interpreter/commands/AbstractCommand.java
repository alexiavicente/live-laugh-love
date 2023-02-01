package interpreter.commands;

import world.World;

public abstract class AbstractCommand implements Command {

    protected World world;

    public AbstractCommand() {
        world = World.getInstance();
    }

    public abstract void execute();
}
