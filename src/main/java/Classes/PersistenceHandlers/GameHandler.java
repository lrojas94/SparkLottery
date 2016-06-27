package Classes.PersistenceHandlers;

import Classes.Data.Game;
import Classes.HelperClasses.DatabaseHandler;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class GameHandler extends DatabaseHandler<Game> {

    public static GameHandler instance;

    public GameHandler() { super(Game.class); }

    public static GameHandler getInstance() {
        if (instance == null) {
            instance = new GameHandler();
        }
        return instance;
    }

}
