package interpreter.commands;

import world.Direction;
import world.Player;
import world.Room;
import world.creation.builder.DirectionEncoder;

public class CommandMove extends AbstractCommand {

    Player player;
    String target;

    public CommandMove() {

    }

    public CommandMove(Player player, String target) {
        this.player = player;
        this.target = target;
    }



    @Override
    public void execute() {

        Direction dir = DirectionEncoder.convertDirection(target);
        this.move(player, dir);
    }

    /*
     * this method moves a player in the specified direction.
     */
    private void move(Player player, Direction dir) {
        Room destination = ((Room) player.getLocation())
                .getExitDestination(dir);
        if (destination == null) {
            player.sendToPlayer("You can't go that way.");
            return;
        }

        ((Room) this.world.getDatabaseObject(player.getRoomId())).sendToRoom(""
                + player.getName() + " exits " + dir.toString().toLowerCase()
                + ".", player);

        player.moveToRoom(destination);

        String from = "";
        if (dir == Direction.NORTH) {
            from = "south";
        } else if (dir == Direction.EAST) {
            from = "west";
        } else if (dir == Direction.SOUTH) {
            from = "north";
        } else if (dir == Direction.WEST) {
            from = "east";
        } else if (dir == Direction.UP) {
            from = "downstairs";
        } else if (dir == Direction.DOWN) {
            from = "upstairs";
        }

        ((Room) this.world.getDatabaseObject(player.getRoomId())).sendToRoom(""
                + player.getName() + " enters from " + from + ".", player);
    }
}

