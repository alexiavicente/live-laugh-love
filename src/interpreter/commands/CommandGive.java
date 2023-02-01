package interpreter.commands;

import world.Mobile;
import world.Player;
import world.World;
import world.gear.Gear;
import world.gear.Gift;

import java.util.List;

public class CommandGive extends AbstractCommand {

    Player player;
    List<String> arguments;

    public CommandGive() {

    }

    public CommandGive(Player player, List<String> arguments) {
        this.player = player;
        this.arguments = arguments;
    }

    @Override
    public synchronized void execute() {

        if(arguments.isEmpty()) {

            player.sendToPlayer("Give what?");
        }
        else {
            if(arguments.size()==1) {

                String itemName = arguments.get(0);

                player.sendToPlayer("Give " + itemName 	+ " to who or what?");
            }
            else {

                String itemName = arguments.get(0);
                String target = arguments.get(1);

                Gear gear = player.getGear(itemName);

                if (gear instanceof Gift) {
                    if (World.getInstance().mobileExists(target)) {
                        Mobile m = World.getInstance().getMobile(target);
                        m.setRelation(player, ((Gift) gear).getRelationMod());
                        player.removeGear(gear);
                    } else {
                        player.sendToPlayer("You can't give a gift to " + target);
                    }
                } else {
                    player.giveGear(player, itemName, target);
                }
            }
        }
    }
}