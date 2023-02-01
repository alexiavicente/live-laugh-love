package interpreter.commands;

import world.Player;
import world.World;

public class CommandSave implements Command {
    Player player;

    public CommandSave() {

    }

    public CommandSave(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        World.getInstance().saveWorld();
        player.sendToPlayer("World saved");
    }
}
