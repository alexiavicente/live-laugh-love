package interpreter.commands;

import world.Player;

public class CommandShutdown extends AbstractCommand {

    Player player;

    public CommandShutdown() {

    }

    public CommandShutdown(Player player) {
        this.player = player;

    }

    @Override
    public void execute() {

        if(player.getName().equals("administrator")) {
            world.saveWorld();
            System.exit(0);
        }
    }
}
