package interpreter.commands;

import world.DatabaseObject;
import world.Player;
import world.gear.GearList;

public class CommandInspect extends AbstractCommand {

    Player player;
    String target;

    public CommandInspect() {

    }

    public CommandInspect(Player player) {
        this.player = player;
    }

    public CommandInspect(Player player, String target) {
        this.player = player;
        this.target = target;
    }

    @Override
    public void execute() {

        if(target == null) {
            player.sendToPlayer("Inspect what?");
        }
        else {
            inspect(player, target);
        }
    }

    /*
     * this method inspect the contents of an item container.
     */
    private void inspect(Player player, String objName) {
        for (DatabaseObject item : world.getDatabaseObjects()) {
            if (item.getName().toLowerCase().equals(objName.toLowerCase().trim())
                    && (item instanceof GearList)) {
                player.sendToPlayer(((GearList) item).inspect());
                return;
            }
        }

        player.sendToPlayer(objName + " does not exist or cannot be inspected.");
    }
}