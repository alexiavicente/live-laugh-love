package world;

import java.io.IOException;

public class WorldNotFoundException extends IOException {

    private static final long serialVersionUID = 1L;

    public WorldNotFoundException() {
        super("World is null, check databaseArray file in MudWorld folder to see if exists or has errors.");
    }
}
