package Classes.PersistenceHandlers;

import Classes.Data.Winner;
import Classes.HelperClasses.DatabaseHandler;

/**
 * Created by MEUrena on 6/30/16.
 * All rights reserved.
 */
public class WinnerHandler extends DatabaseHandler<Winner> {

    private static WinnerHandler instance;

    private WinnerHandler() { super(Winner.class); }

    public static WinnerHandler getInstance() {
        if (instance == null) {
            instance = new WinnerHandler();
        }

        return instance;
    }

}
