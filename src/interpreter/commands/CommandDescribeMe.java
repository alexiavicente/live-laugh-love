package interpreter.commands;

import world.Player;

public class CommandDescribeMe extends AbstractCommand {

    Player player;
    String description;

    public CommandDescribeMe() {

    }

    public CommandDescribeMe(Player player) {
        this.player = player;
    }

    public CommandDescribeMe(Player player, String description) {
        this.player = player;
        this.description = description;
    }

    @Override
    public synchronized void execute() {
        if (description != null) {

            player.setDescription(description);
        } else {

            player.sendToPlayer("The describeme command should be followed by a description of yourself.");
        }
    }
}
