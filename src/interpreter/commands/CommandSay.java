package interpreter.commands;

import world.Player;
import world.Room;

public class CommandSay extends AbstractCommand {

    Player player;
    String message;

    public CommandSay() {

    }

    public CommandSay(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public void execute() {

        ((Room) this.world.getDatabaseObject(player.getRoomId()))
                .sendToRoom(player.getName() + " says: " + message);
    }
}
