package Classes.Routers;


import static spark.Spark.*;

import Classes.Data.JsonGame;
import Classes.Data.Ticket;
import Classes.Data.User;
import Classes.PersistenceHandlers.GameHandler;
import Classes.PersistenceHandlers.TicketHandler;
import Classes.PersistenceHandlers.UserHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by MEUrena on 7/19/16.
 * All rights reserved.
 */
public class API {

    public static void Routes() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        UserHandler userHandler = UserHandler.getInstance();
        TicketHandler ticketHandler = TicketHandler.getInstance();
        GameHandler gameHandler = GameHandler.getInstance();

        post("/api/login", (request, response) -> {
            HashMap<String,Object> errorMessage = new HashMap<>();
            User user = null;
            try {
                user = userHandler.loginUser(request.queryParams("username"), request.queryParams("password"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (user == null) {
                errorMessage.put("error", "Error retrieving the user. The requested user doesn't exists in the database.");
                return errorMessage;
            } else {
                return user;
            }
        }, gson::toJson);

        get("/api/game/list", (request, response) -> {
            HashMap<String,Object> errorMessage = new HashMap<>();
            User user = null;
            try {
                user = userHandler.findUserByUsername("lrojas");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (user == null) {
                errorMessage.put("error", "Error retrieving the user. The requested user doesn't exists in the database.");
                return errorMessage;
            } else {
                Set<Ticket> ticketList = user.getTickets();
                return ticketList;
            }

        }, gson::toJson);

//        post("/api/game/create", (request, response) -> {
//            JsonGame newGame = gson.fromJson(request.queryParams("data"), JsonGame.class);
//
//        }, gson::toJson);
    }

}
