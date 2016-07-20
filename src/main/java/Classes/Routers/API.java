package Classes.Routers;


import static spark.Spark.*;

import Classes.Data.*;
import Classes.Game.RandomGenerator;
import Classes.Main;
import Classes.PersistenceHandlers.GameHandler;
import Classes.PersistenceHandlers.TicketHandler;
import Classes.PersistenceHandlers.TransactionHandler;
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
        TransactionHandler transactionHandler = TransactionHandler.getInstance();
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

        post("/api/game/create", (request, response) -> {
            HashMap<String,Object> errorMessage = new HashMap<>();
            double bet = Double.parseDouble(request.queryParams("bet"));
            String type = request.queryParams("type");
            String nums = request.queryParams("nums");
            User user = null;

            try {
                user = userHandler.findUserByUsername("lrojas");
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage.put("error", "Error retrieving the user. The requested user doesn't exists in the database.");
                return errorMessage;
            }

            if (user.getAccount().getBalance() <= 0.00) {
                errorMessage.put("balance_error", "No tiene balance alguno para jugar");
                return errorMessage;
            } else if (bet > user.getAccount().getBalance()) {
                errorMessage.put("balance_error", "No puede apostar mas de lo que tiene en su cuenta.");
                return errorMessage;
            } else {
                String playStatus = null;
                switch (type) {
                    case "PALE":
                        playStatus = playPale(nums, user, bet);
                        break;
                    case "LOTO":
//                        playStatus = playLoto(nums, user, bet);
                        break;
                    default:
                        break;
                }
                return playStatus;
            }


        }, gson::toJson);
    }

    private static String playPale(String ticketPlay, User user, double bet) {
        String status;

        GameHandler gameHandler = GameHandler.getInstance();
        TicketHandler ticketHandler = TicketHandler.getInstance();
        TransactionHandler transactionHandler = TransactionHandler.getInstance();
        try {
            Classes.Data.Game activePale = gameHandler.findActivePale();
            Ticket ticket = new Ticket();
            ticket.setNumbers(ticketPlay);
            ticket.setBetAmount(bet);
            ticket.setOwner(user);
            ticket.setIssuedIn(activePale);

            Transaction trans = Main.createTicketTransaction(ticket);
            ticketHandler.insertIntoDatabase(ticket);
            transactionHandler.insertIntoDatabase(trans);

            //SIMULATE GAME:
            RandomGenerator gen = new RandomGenerator();
            int[] winningNumbers = gen.getNumbers(3);
            String[] numsArray = ticketPlay.split(",");
            int[] intArray = new int[numsArray.length];
            for(int i = 0; i < numsArray.length; i++) {
                intArray[i] = Integer.parseInt(numsArray[i]);
            }

            if(winningNumbers != null){
                //We can work with this:
                if ((winningNumbers[0] == intArray[0] &&
                        winningNumbers[1] == intArray[1] &&
                        winningNumbers[2] == intArray[2]) || (intArray[0] + intArray[2] == 100)) {
                    //WINNER o/
                    activePale.setWinningTicket(ticket);
                    gameHandler.updateObject(activePale);
                    //create new Pale:
                    Classes.Data.Game pale = new Classes.Data.Game();
                    pale.setBaseAmmount(0);
                    pale.setType(Classes.Data.Game.GameType.PALE);
                    //save:
                    gameHandler.insertIntoDatabase(pale);
                    //Create winning trans:
                    Transaction winningTrans = new Transaction();
                    winningTrans.setMethod(Transaction.Method.WINNER);
                    winningTrans.setAmmount(activePale.getWinnersAmmount());
                    winningTrans.setOwner(user.getAccount());
                    winningTrans.setDescription("Pale Ganado con apuesta de: " + ticket.getBetAmount());
                    transactionHandler.insertIntoDatabase(winningTrans);
                    user.getAccount().getTransactions().add(winningTrans);
                    status = "Win";
                } else {
                    status = "Lose";
                }
            } else {
                status = "Error retrieving random numbers from the API.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            status = "There was an error with the request. Check your numbers.";
        }

        return status;
    }

//    private static String playLoto(String ticketPlay, User user, double bet) {
//
//    }

}
