package Classes.PersistenceHandlers;

import Classes.Data.Ticket;
import Classes.Data.Transaction;
import Classes.HelperClasses.DatabaseHandler;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class TransactionHandler extends DatabaseHandler<Transaction> {

    public static TransactionHandler instance;

    public TransactionHandler() {super(Transaction.class); }

    public static TransactionHandler getInstance() {
        if (instance == null) {
            instance = new TransactionHandler();
        }
        return instance;
    }

}
