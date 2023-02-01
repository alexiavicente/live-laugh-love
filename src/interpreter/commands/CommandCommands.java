package interpreter.commands;

import interpreter.CommandList;
import world.Player;

public class CommandCommands extends AbstractCommand {

    Player player;

    public CommandCommands() {

    }

    public CommandCommands(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        String result = "";
        for (String command : CommandList.getCommandDescriptions()) {
            result += command + "\n";
            result += command + '\n';
        }
        System.out.println(result);
        player.sendToPlayer(result);
    }
}
