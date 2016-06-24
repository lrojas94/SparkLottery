package Classes.PersistenceHandlers;

import Classes.Data.Loto;
import Classes.HelperClasses.DatabaseHandler;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class LotoHandler extends DatabaseHandler<Loto> {

    public static LotoHandler instance;

    public LotoHandler() { super(Loto.class); }

    public static LotoHandler getInstance() {
        if (instance == null) {
            instance = new LotoHandler();
        }
        return instance;
    }

}
