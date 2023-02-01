package server;

import interpreter.CommandList;
import world.*;

public class UserRegistrationHandler {

    server.Client client;

    public UserRegistrationHandler() {

    }

    public UserRegistrationHandler(server.Client client) {
        this();

        this.client= client;
    }

    public void checkPlayerAccess() {

        boolean playerConfirmed = false;

        // confirm player
        while (!playerConfirmed) {

            String name = "", tempName = "", password = "";

            // get player name
            while (name == null || name.length() < 1 || name.equals("")) {

                while (tempName.equals("") || tempName.equals("commands")) {
                    this.client.sendMessageReply("Input your player name:\n" + "(or type "
                            + '"' + "new" + '"' + " for new player):");
                    tempName = client.receiveCommand();
                }

                if (!validEntry(tempName) && !tempName.equalsIgnoreCase("new"))
                    tempName = "";

                // if new player, get name and password
                if (tempName.equals("new")) {

                    String newName = "";

                    while (newName.equals("") || newName.equals("commands")) {
                        this.client.sendMessageReply("What player name would you like?\n"
                                + "(please no spaces and cannot be a command keyword,\n"
                                + "type " + '"' + "commands" + '"'
                                + " to see a list of commands)");
                        newName = this.client.receiveCommand();
                    }

                    if (World.getInstance().nameExists(newName)) {
                        this.client.sendMessageReply("That name is reserved.");
                        newName = "";
                    }

                    if (!newName.equals("") && this.validEntry(newName)) {
                        this.getNewPlayerPasswordAndAddToWorld(newName);
                        return;
                    } else {
                        newName = "";
                        tempName = "";
                    }
                }

                name = tempName;
            }

            // get password of existing player
            while (password.equals("")) {
                this.client.sendMessageReply("Please type in your password:");
                password = this.client.receiveCommand();
            }

            // confirm player
            if (World.getInstance().confirmPlayer(name, password)) {
                if (this.loadPlayerFromFile(name)) {
                    this.addToWorld();
                    playerConfirmed = true;
                }
            } else {
                this.client.sendMessageReply("Invalid user name or password.");
            }
        }
    }

    /*
     * This private method checks to make sure text being entered as names or
     * passwords are not commands.
     *
     * @return - True if word is a command word.
     */
    private boolean startsWithCommand(String text) {
        for (String command : CommandList.getCommands()) {
            if (text.equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }

    /*
     * This private methods compares the first password entered to the confirm
     * password entered for new players and also determines if the password has
     * valid characters and length.
     *
     * @return A boolean, true if password is a valid password.
     */
    private boolean confirmPassword(String firstPassword, String confirmPassword) {
        return !startsWithCommand(firstPassword)
                && firstPassword.equals(confirmPassword)
                && !firstPassword.contains(" ") && firstPassword.length() > 5;

    }

    /*
     * This method checks to see if new name has space, already exists, or is a
     * command return false.
     */
    private boolean validEntry(String name) {

        boolean result = !name.contains(" ") && !this.startsWithCommand(name);
        if (!result && !name.equals("new")) {
            this.client.sendMessageReply("That entry is invalid or reserved.");
            return false;
        }
        for (int i = 0; i< name.length();i++) {
            int Ascii = name.charAt(i);
            if (Ascii < 65 || Ascii > 90 && Ascii <97 || Ascii >122){
                this.client.sendMessageReply("That entry is invalid or reserved.");
                return false;
            }
        }


        return true;
    }

    /*
     * This method gets the new player password and adds the player to the
     * world.
     */
    private void getNewPlayerPasswordAndAddToWorld(String newName) {

        String firstPassword = "", confirmPassword = "";
        boolean done = false;
        while (!done) {

            // get password.
            this.client.sendMessageReply("Please type a six or more character password:\n(no spaces and no commands):");

            firstPassword = this.client.receiveCommand();

            // confirm password
            this.client.sendMessageReply("Please confirm your password:");
            confirmPassword = this.client.receiveCommand();

            // check password
            if (this.confirmPassword(firstPassword, confirmPassword)) {
                // add to world

                Player player2use = World.getInstance().createPlayer(newName,firstPassword);
                if (player2use != null) {
                    this.client.setPlayer(player2use);
                    World.getInstance().savePlayer(player2use);
                    String characterClass = "";
                    while (!(characterClass.equalsIgnoreCase("female") || characterClass
                            .equalsIgnoreCase("male"))
                            && (this.client.getState() != ClientState.DONE)) {
                        this.client.sendMessageReply("What character class"
                                + " would you like to be:"
                                + " Male or Female?");
                        characterClass = this.client.receiveCommand();
                    }
                    if (this.client.getState() != ClientState.DONE) {
                        if (characterClass.equalsIgnoreCase("male")) {
                            this.client.getPlayer().setClient(this.client);
                            this.client.getPlayer().setCharacterClass(Male.getInstance());
                        }
                        if (characterClass.equalsIgnoreCase("female")) {
                            this.client.getPlayer().setClient(this.client);
                            this.client.getPlayer().setCharacterClass(Female.getInstance());
                        }
                        this.addToWorld();
                    } else {
                        this.client.setPlayer(null);					}
                    done = true;
                }
            } else {
                this.client.sendMessageReply("Invalid password.");
            }
        }
    }


    /*
     * This method initializes the player instance variable from a loaded file.
     *
     * @return true if sucessfully loaded.
     */
    private boolean loadPlayerFromFile(String name) {
        this.client.setPlayer(World.getInstance().loadPlayer(name));
        if(this.client.getPlayer() != null){
            this.client.getPlayer().setClient(this.client);
        }
        return this.client.getPlayer() != null;
    }

    /*
     * This method sets the player instance variable up with the necessary
     * setting required for gameplay.
     */
    private void addToWorld() {

        if (this.client.getPlayer() != null) {
            World.getInstance().addLoggedOn(this.client.getPlayer().getName().toLowerCase());
            this.client.setState(ClientState.PLAYING);
            this.client.getPlayer().moveToRoom((Room) World.getInstance().getDatabaseObject(this.client.getPlayer().getRoomId()));
            ((Room) this.client.getPlayer().getLocation()).sendToRoom(this.client.getPlayer().getName() + " enters game.",this.client.getPlayer());
        }
    }


}
