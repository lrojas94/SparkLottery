package Classes.Routers;


import static spark.Spark.*;

import Classes.Data.*;
import Classes.Data.Game;
import Classes.Game.RandomGenerator;
import Classes.HelperClasses.Utilities;
import Classes.Main;
import Classes.PersistenceHandlers.GameHandler;
import Classes.PersistenceHandlers.TicketHandler;
import Classes.PersistenceHandlers.TransactionHandler;
import Classes.PersistenceHandlers.UserHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MEUrena on 7/19/16.
 * All rights reserved.
 */
public class API {

    public static void Routes() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserSerializer());
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
                user = userHandler.findUserByUsername(request.queryParams("username"));
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
            String betString = request.queryParams("bet");
            double bet = 0.0;
            if (betString != null) {
                bet = Double.parseDouble(betString);
            }
            String type = request.queryParams("type");
            String nums = request.queryParams("nums");
            User user = null;

            try {
                user = userHandler.findUserByUsername(request.queryParams("username"));
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
                Map<String, Object> playStatus = new HashMap<>();
                switch (type) {
                    case "PALE":
                        playStatus = playPale(nums, user, bet);
                        break;
                    case "LOTO":
                        Pattern regex = Pattern.compile("((0*(?:[1-9][0-9]?))(,\\s*(0*(?:[1-9][0-9]?))){19})");
                        Matcher matcher = regex.matcher(nums);
                        if (!matcher.matches()) {
                            playStatus.put("error", "Se requiere insertar una cadena de 20 numeros separados por coma y menores a 100.");
                        } else {
                            playStatus = playLoto(nums, user);
                        }
                        break;
                    default:
                        break;
                }
                playStatus.put("user", user);
                return playStatus;
            }


        }, gson::toJson);
    }

    private static Map<String, Object> playPale(String ticketPlay, User user, double bet) {
        Map<String, Object> status = new HashMap<>();

        GameHandler gameHandler = GameHandler.getInstance();
        TicketHandler ticketHandler = TicketHandler.getInstance();
        TransactionHandler transactionHandler = TransactionHandler.getInstance();
        try {
            Game activePale = gameHandler.findActivePale();
            Ticket ticket = new Ticket();
            ticket.setNumbers(ticketPlay);
            ticket.setBetAmount(bet);
            ticket.setOwner(user);
            ticket.setEmitDate(new Date());
            ticket.setIssuedIn(activePale);

            Transaction trans = Main.createTicketTransaction(ticket);
            user.getAccount().getTransactions().add(trans);
            ticketHandler.insertIntoDatabase(ticket);
            transactionHandler.insertIntoDatabase(trans);

            //SIMULATE GAME:
            RandomGenerator gen = new RandomGenerator();
            int[] winningNumbers = gen.getNumbers(3);
            int[] intArray = Utilities.stringToIntArray(ticket.getNumbers());

            if (winningNumbers != null) {
                //We can work with this:
                if ((winningNumbers[0] == intArray[0] &&
                        winningNumbers[1] == intArray[1] &&
                        winningNumbers[2] == intArray[2]) || (intArray[0] + intArray[2] == 100)) {
                    //WINNER o/
                    activePale.setWinningTicket(ticket);
                    gameHandler.updateObject(activePale);
                    //create new Pale:
                    Game pale = new Game();
                    pale.setBaseAmmount(0);
                    pale.setType(Game.GameType.PALE);
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
                    status.put("status", "Win");
                } else {
                    status.put("status", "Lose");
                }
            } else {
                status.put("error", "Error retrieving random numbers from the API.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            status.put("error", "There was an error with the request. Check your numbers.");
        }

        return status;
    }

    private static Map<String, Object> playLoto(String ticketPlay, User user) {
        Map<String, Object> status = new HashMap<>();

        GameHandler gameHandler = GameHandler.getInstance();
        TicketHandler ticketHandler = TicketHandler.getInstance();
        TransactionHandler transactionHandler = TransactionHandler.getInstance();
        try {
            Game activeLoto = gameHandler.findActiveLoto();
            Ticket ticket = new Ticket();
            ticket.setOwner(user);
            ticket.setBetAmount(50.00);
            ticket.setIssuedIn(activeLoto);
            ticket.setEmitDate(new Date());
            ticket.setNumbers(ticketPlay);

            ticketHandler.insertIntoDatabase(ticket);
            Transaction trans = Main.createTicketTransaction(ticket);
            user.getAccount().getTransactions().add(trans);
            transactionHandler.insertIntoDatabase(trans);

            //SIMULATE GAME:
            RandomGenerator gen = new RandomGenerator();
            int[] winningNumbers = gen.getNumbers(5);

            if (winningNumbers != null) {
                //We can work with this:
                boolean isWinner = true;
                int[] ticketNums = Utilities.stringToIntArray(ticket.getNumbers());
                for (int n : winningNumbers) {
                    boolean exist = false;
                    for (int t : ticketNums) {
                        if (n == t) {
                            exist = true;
                        }
                    }
                    if (!exist) {
                        isWinner = false;
                        break;
                    }
                }

                if (isWinner || (ticketNums[0] + ticketNums[19] == 100)) {
                    activeLoto.setWinningTicket(ticket);
                    gameHandler.updateObject(activeLoto);
                    //create new Pale:
                    Game loto = new Game();
                    loto.setBaseAmmount(1000000);
                    loto.setType(Game.GameType.LOTO);
                    //save:
                    gameHandler.insertIntoDatabase(loto);
                    //Create winning trans:
                    Transaction winningTrans = new Transaction();
                    winningTrans.setMethod(Transaction.Method.WINNER);
                    winningTrans.setAmmount(activeLoto.getWinnersAmmount());
                    winningTrans.setOwner(user.getAccount());
                    winningTrans.setDescription("Loto Ganado con apuesta de: " + ticket.getBetAmount());
                    transactionHandler.insertIntoDatabase(winningTrans);
                    user.getAccount().getTransactions().add(winningTrans);
                    status.put("status", "Win");
                } else {
                    status.put("status", "Lose");
                }
            } else {
                status.put("error", "Error retrieving random numbers from the API.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            status.put("error", "There was an error with the request. Check your numbers.");

        }
        return status;
    }

}
