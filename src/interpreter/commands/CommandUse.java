package interpreter.commands;

import world.Player;

public class CommandUse extends AbstractCommand {

    Player player;
    String target;

    public CommandUse() {}

    public CommandUse(Player player) {
        this.player = player;
    }

    public CommandUse(Player player, String target) {
        this.player = player;
        this.target = target;
    }

    @Override
    public void execute() {

        if(target == null) {
            player.sendToPlayer("Use what?");
        }
        else {
            player.use(target);
        }
    }

}
