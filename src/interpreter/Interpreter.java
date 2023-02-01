package interpreter;

import interpreter.commands.Command;
import world.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Interpreter {

    private static Interpreter instance;

    public static Interpreter getInstance() {
        if(instance == null) {
            instance =  new Interpreter();
        }
        return instance;
    }

    public synchronized void processCommand(Player player, String textCommand) {

        List<String> parsedCommandSequence = parseCommand(textCommand);

        if(!parsedCommandSequence.isEmpty()) {

            Command command = CommandInstantiator.instantiate(player, parsedCommandSequence);
            if (command != null)
                command.execute();

        }
    }
    public static List<String> parseCommand(String textCommand){

        Scanner scanner = new Scanner(textCommand);

        List<String> parsedCommandSequence = new LinkedList<String>();

        while(scanner.hasNext()) {

            String command = scanner.next().trim().toLowerCase();
            parsedCommandSequence.add(command);
        }
        scanner.close();

        return parsedCommandSequence;
    }

}
