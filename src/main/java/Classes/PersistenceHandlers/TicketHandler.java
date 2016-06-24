package Classes.PersistenceHandlers;

import Classes.Data.Ticket;
import Classes.HelperClasses.DatabaseHandler;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class TicketHandler extends DatabaseHandler<Ticket> {

    public static TicketHandler instance;

    public TicketHandler() {super(Ticket.class); }

    public static TicketHandler getInstance() {
        if (instance == null) {
            instance = new TicketHandler();
        }
        return instance;
    }

}
