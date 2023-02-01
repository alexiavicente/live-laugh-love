package interpreter.commands;

import world.Player;

public class CommandInventory extends AbstractCommand {

    Player player;

    public CommandInventory() {

    }

    public CommandInventory(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.sendToPlayer(player.inspect());
    }
}
