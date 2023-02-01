package interpreter;

import java.util.*;

public class CommandList {

    private static Map<String, String> commandList;

    public static Set<String> getCommands() {
        if(commandList == null)
            commandList = buildCommandList();
        return commandList.keySet();
    }

    public static List<String> getCommandDescriptions() {

        if(commandList == null)
            commandList = buildCommandList();

        List<String> commands = new ArrayList<String>();

        for (String command : commandList.values())
            commands.add(command);

        Collections.sort(commands);
        return commands;
    }

    private static Map<String, String> buildCommandList() {

        Map<String, String> commandList = new HashMap<String, String>();

        commandList.put(
                "look",
                "- look: shows description of the room that the player is in, "
                        + "or if an argument is provided, such as an item/player/MOB in the room, "
                        + "it should provide the description of said item/player/MOB). "
                        + "This command gives a 360 degree report of the environment ");

        commandList.put("north", "- north: moves the player North.");
        commandList.put("south", "- south: moves the player South.");
        commandList.put("east", "- east: moves player east.");
        commandList.put("west", "- west: moves player west.");
        commandList.put("up", "- up: moves player up.");
        commandList.put("down", "- down: moves player down.");
        commandList.put("n", "- n: moves the player North.");
        commandList.put("s", "- s: moves the player South.");
        commandList.put("e", "- e: moves player east.");
        commandList.put("w", "- w: moves player west.");
        commandList.put("u", "- u: moves player up.");
        commandList.put("d", "- d: moves player down.");

        commandList.put("who", "- who: lists all players that are logged in.");
        commandList.put(
                "say",
                "- say: sends a message to all players in the same room as the player executing the command.");
        commandList.put("give",
                "- give <item> <player>: gives item in your inventory to player/MOB.");
        commandList.put(
                "get",
                "- get <item>: gets item from room. Also: get <item> <target>: gets item from player/MOB/item.");
        commandList.put("inventory",
                "- inventory: lists the items that you are carrying.");
        commandList.put("i", "- i: lists the items that you are carrying.");
        commandList.put("drop",
                "- drop <item>: drops an item from your inventory to the room.");
        commandList.put("use",
                "- use <item>: executes the item�s default behavior.");
        commandList.put("quit",
                "- quit: allows a player to exit the system. Will not shut MUD down.");
        commandList.put(
                "shutdown",
                "- shutdown: saves the MUD�s data and then shuts the system down. (only game administrator's can use this.");
        commandList.put("save", "- save: saves player state in the game.");
        commandList.put("describeme",
                "- describeme <description>: sets your (the player's) description.");
        commandList.put("commands",
                "- commands: lists all the commands useable by a player.");
        commandList.put(
                "inspect",
                "- inspect <player,mob,item>: lists all the items being held or contained in a player, mob, or other item.");

        commandList.put("flirt", "- flirt: flirts with a character.");

        commandList.put("new", "- new: creates new player on log in.");

        return commandList;
    }

}
