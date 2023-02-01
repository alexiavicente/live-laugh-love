package interpreter.commands;

import world.Player;

public class CommandWho extends AbstractCommand {

    Player player;

    public CommandWho() {

    }

    public CommandWho(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        String result = "chat Right now, ";
        for (String name : world.getPlayersLoggedOn()) {
            result += " " + name + ",";
        }
        result = result.substring(0, result.length()-1);
        if (world.getPlayersLoggedOn().size() > 1) {
            result += " are connected.";
        } else {
            result += " is connected.";
        }

        player.sendToPlayer(result);
    }
}
