package Classes.PersistenceHandlers;

import Classes.Data.Account;
import Classes.HelperClasses.DatabaseHandler;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class AccountHandler extends DatabaseHandler<Account> {

    public static AccountHandler instance;

    public AccountHandler() { super(Account.class); }

    public static AccountHandler getInstance() {
        if (instance == null) {
            instance = new AccountHandler();
        }
        return instance;
    }

}
