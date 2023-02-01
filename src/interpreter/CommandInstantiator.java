package interpreter;

import java.util.List;
import interpreter.commands.*;
import world.Player;

public class CommandInstantiator {

    public static Command instantiate(Player player, List<String> parsedCommandSequence) {

        Command command = null;

        String commandWord = parsedCommandSequence.get(0);

        switch(commandWord) {

            case "say":
                if(parsedCommandSequence.size()>1) {

                    String message  =
                            ReformatLine.reformat( parsedCommandSequence.subList(1, parsedCommandSequence.size()));

                    command = new CommandSay(player, message);
                }
                else {
                    command = new CommandSay(player, "Say what?");
                }
                break;

            case "commands":
                command = new CommandCommands(player);
                break;

            case "who":
                command = new CommandWho(player);
                break;

            case "save":
                command = new CommandSave(player);
                break;

            case "describeme":
                if(parsedCommandSequence.size()>1) {
                    String description  =
                            ReformatLine.reformat(
                                    parsedCommandSequence.subList(1, parsedCommandSequence.size())
                            );

                    command = new CommandDescribeMe(player, description);
                }
                else {
                    command = new CommandDescribeMe(player);
                }

                break;

            case "look":
                if(parsedCommandSequence.size()>1) {

                    String target  = parsedCommandSequence.get(1);

                    command = new CommandLook(player, target);
                }
                else {
                    command = new CommandLook(player);
                }
                break;

            case "inspect":
                if(parsedCommandSequence.size()>1) {

                    String target  = parsedCommandSequence.get(1);

                    command = new CommandInspect(player, target);
                }
                else {
                    command = new CommandInspect(player);
                }
                break;

            case "shutdown":
                command = new CommandShutdown(player);
                break;

            case "inventory":
                command = new CommandInventory(player);
                break;

            case "i":
                command = new CommandInventory(player);
                break;

            case "drop":
                if(parsedCommandSequence.size()>1) {

                    String target  = parsedCommandSequence.get(1);

                    command = new CommandDrop(player, target);
                }
                else {
                    command = new CommandDrop(player);
                }
                break;

            case "use":
                if(parsedCommandSequence.size()>1) {

                    String target  = parsedCommandSequence.get(1);

                    command = new CommandUse(player, target);
                }
                else {
                    command = new CommandUse(player);
                }
                break;

            case "get":
                command =
                        new CommandGet(
                                player,
                                parsedCommandSequence.subList(1, parsedCommandSequence.size()));
                break;

            case "give":
                command =
                        new CommandGive(
                                player,
                                parsedCommandSequence.subList(1, parsedCommandSequence.size()));
                break;

            //  movement commands

            case "north":
                command = new CommandMove(player, "n");
                break;

            case "n":
                command = new CommandMove(player, "n");
                break;

            case "south":
                command = new CommandMove(player, "s");
                break;

            case "s":
                command = new CommandMove(player, "s");
                break;

            case "east":
                command = new CommandMove(player, "e");
                break;

            case "e":
                command = new CommandMove(player, "e");
                break;

            case "west":
                command = new CommandMove(player, "w");

                break;

            case "w":
                command = new CommandMove(player, "w");
                break;

            case "up":
                command = new CommandMove(player, "u");
                break;

            case "u":
                command = new CommandMove(player, "u");
                break;

            case "down":
                command = new CommandMove(player, "d");
                break;

            case "d":
                command = new CommandMove(player, "d");
                break;

            //  combat commands

            case "flirt":
                if(parsedCommandSequence.size()>1) {

                    String target  = parsedCommandSequence.get(1);

                    command = new CommandFlirt(player, target);
                }
                else {
                    command = new CommandFlirt(player);
                }
                break;

            default :
                player.sendToPlayer(commandWord + " is not understood.");
                break;
        }

        return command;
    }
}
