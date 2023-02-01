package interpreter.commands;

import world.*;

public class CommandFlirt extends AbstractCommand {

    Player player;
    String target;

    public CommandFlirt() {

    }

    public CommandFlirt(Player player) {
        this.player = player;
    }

    public CommandFlirt(Player player, String target) {
        this.player = player;
        this.target = target;
    }

    @Override
    public void execute() {

        if (player.isFlirting() == true) {
            player.sendToPlayer("You cannot flirt because you are already in flirting");
        }
        else {
            if(target == null) {
                player.sendToPlayer("Who do you want to seduce? (Flirt <victim>)");
            }
            else {
                for (Movable i : ((Room) player.getLocation()).listMovables()) {
                    if (i.getName().equalsIgnoreCase(target) && i instanceof Mobile) {
                        if (i.isFlirting()) {
                            player.sendToPlayer(i.getName() + " is already being seduced");
                        }
                        else {
                            FlirtManager attack = new FlirtManager(player, (Mobile)i);
                        }
                    }
                }
            }
        }
    }

}