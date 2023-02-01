package interpreter.commands;

import java.util.Iterator;
import java.util.List;

import interpreter.commands.AbstractCommand;
import server.ClientState;
import world.Player;
import world.Room;
import world.Trait;
import world.gear.Gear;
import world.gear.GearContainer;

public class CommandGet extends AbstractCommand {

    Player player;
    List<String> arguments;

    public CommandGet() {
    }

    public CommandGet(Player player) {
        this.player = player;
    }

    public CommandGet(Player player, List<String> arguments) {
        this.player = player;
        this.arguments = arguments;
    }

    @Override
    public void execute() {

        if(arguments.isEmpty()) {
            player.sendToPlayer("Get what?");
        }
        else {
            if(arguments.size()==1) {

                String itemName = arguments.get(0);
                this.get(itemName);
            }
            else {

                String itemName = arguments.get(0);
                String target = arguments.get(1);

                this.get(itemName, target);
            }
        }
    }

    private synchronized void get(String itemName) {

        boolean found = false;

        List<Gear> gearAvailable = ((Room) player.getLocation()).listGear();

        Iterator<Gear> it = gearAvailable.iterator();

        Gear whatIWant = null;

        while(!found && it.hasNext()) {
            Gear roomItem = it.next();

            if (roomItem.getName().equalsIgnoreCase(itemName)) {
                found = true;
                whatIWant = roomItem;
            }
        }

        if(!found) {
            player.sendToPlayer(itemName + " is not in the room.");
        }
        else {
            if (whatIWant instanceof GearContainer && !((GearContainer) whatIWant).canBeCarried()) {
                player.sendToPlayer(itemName + " cannot be carried.");
            } else {
                Gear gear = whatIWant;
                if(gear.getPrice() > 0) {
                    if (gear.getPrice() <= player.getStat(Trait.MONEY)) {
                        if (player.addGear(whatIWant.cloneGear())) {
                            player.payMoney(gear.getPrice());
                            player.sendToPlayer("You have paid " + gear.getPrice() + " LCoins for " + itemName +".");
                        }
                    } else {
                        player.sendToPlayer("You don't have enough money to buy " + itemName +".");
                    }
                } else {
                    player.addGear(whatIWant);
                    ((Room) player.getLocation()).remove(whatIWant);
                }
            }
        }
    }

    private synchronized void get(String itemName, String target) {

        boolean giveable = false;

        if (world.playerExists(target)
                && world.playerIsLoggedOn(target)) {
            if (!world.getPlayer(target).giveGear(
                    world.getPlayer(target), itemName,
                    player.getName())) {
                player.sendToPlayer(target
                        + " does not have that item.");
            }
            else {
                giveable = true;
            }
        }
        if (world.mobileExists(target)) {
            if (!world.getMobile(target).giveGear(
                    world.getMobile(target), itemName,
                    player.getName())) {
                player.sendToPlayer(target
                        + " does not have that item.");
            }
            else {
                giveable = true;
            }
        }

        if(giveable) {

            for (Gear roomItem : ((Room) player.getLocation()).listGear()) {
                if (roomItem instanceof GearContainer && target.equals(roomItem.getName().toLowerCase())) {
                    if (((GearContainer) roomItem).giveGear(null, itemName, player.getName())) {
                        Gear gear = ((GearContainer) roomItem).getGear(itemName);
                        if(gear.getPrice() > 0) {
                            if (gear.getPrice() <= player.getStat(Trait.MONEY)) {
                                player.payMoney(gear.getPrice());
                                player.sendToPlayer("You have paid " + gear.getPrice() + " LCoins for " + itemName +".");
                            } else {
                                player.sendToPlayer("You don't have enough money to buy " + itemName +".");
                            }
                        }
                        return;
                    } else {
                        player.sendToPlayer("Does " + target + " have that item?");
                    }
                }
            }
        }

    }
}
