package Classes.Routers;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import Classes.Data.*;
import Classes.Game.RandomGenerator;
import Classes.HelperClasses.AuthFilter;
import Classes.HelperClasses.Utilities;
import Classes.Main;
import Classes.PersistenceHandlers.*;
import com.google.gson.JsonArray;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.awt.image.RescaleOp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luis on 6/30/16.
 */
public class Game {


    public static void Routes() {
        GameHandler gameHandler = GameHandler.getInstance();
        TicketHandler ticketHandler = TicketHandler.getInstance();
        TransactionHandler transactionHandler = TransactionHandler.getInstance();

        try{
            Classes.Data.Game activeLoto = gameHandler.findActiveLoto();
            Classes.Data.Game activePale = gameHandler.findActivePale();

            if(activeLoto == null){
                activeLoto = new Classes.Data.Game();
                activeLoto.setType(Classes.Data.Game.GameType.LOTO);
                gameHandler.insertIntoDatabase(activeLoto);
            }

            if(activePale == null){
                activePale = new Classes.Data.Game();
                activePale.setType(Classes.Data.Game.GameType.PALE);
                gameHandler.insertIntoDatabase(activePale);
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }
        before("game/*", new AuthFilter(new FreeMarkerEngine(), new HashSet<AuthRoles>()));

        get("game/play", (request, response) -> {
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            Classes.Data.Game activeLoto = gameHandler.findActiveLoto();
            attributes.put("accumulated",activeLoto.getWinnersAmmount());
            attributes.put("multiplier", Constants.PALE_MULTIPLIER);
            attributes.put("template_name","game/play.ftl");
            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        }, new FreeMarkerEngine());

        get("game/loto", (request, response) -> {
            //This takes 20 nums T.T... COMMA SEPARATED :V
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            User user = request.session().attribute("user");

            if(user.getAccount().getBalance() < 50.00){
                request.session(true).attribute("message","Su balance no es suficiente para jugar Loto. Agregue a su cuenta almenos RD$50.00");
                request.session(true).attribute("message_type","danger");
                response.redirect("/user/" + user.getId());
            }

            Classes.Data.Game activeLoto = gameHandler.findActiveLoto();
            attributes.put("prize",activeLoto.getWinnersAmmount() + user.getAccount().getBalance());

            attributes.put("template_name","game/loto.ftl");
            return new ModelAndView(attributes, Main.BASE_LAYOUT );
        }, new FreeMarkerEngine());

        post("/game/loto",(request, response) -> {
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            User user = request.session().attribute("user");


            Pattern regex = Pattern.compile("((0*(?:[1-9][0-9]?))(,\\s*(0*(?:[1-9][0-9]?))){19})");
            Matcher matcher = regex.matcher(request.queryParams("nums"));
            if(!matcher.matches()){
                attributes.put("nums",request.queryParams("nums"));
                attributes.put("errors","Se requiere insertar una cadena de 20 numeros separados por coma y menores a 100.");
                attributes.put("template_name","game/loto.ftl");
                return new ModelAndView(null, Main.BASE_LAYOUT);
            }
            else{
                Classes.Data.Game activeLoto = gameHandler.findActiveLoto();

                //Check if you're the winner :D!
                Ticket ticket = new Ticket();
                ticket.setOwner(user);
                ticket.setBetAmount(50.00);
                ticket.setIssuedIn(activeLoto);
                ticket.setEmitDate(new Date());
                ticket.setNumbers(request.queryParams("nums"));

                ticketHandler.insertIntoDatabase(ticket);
                Transaction trans = Main.createTicketTransaction(ticket);
                user.getAccount().getTransactions().add(Main.createTicketTransaction(ticket));
                transactionHandler.insertIntoDatabase(trans);

                //SIMULATE GAME:
                RandomGenerator gen = new RandomGenerator();
                int[] winningNumbers = gen.getNumbers(5);
                //72,133
                if(winningNumbers != null){
                    //We can work with this:
                    attributes.put("winningNumbers",winningNumbers);
                    boolean isWinner = true;
                    int[] ticketNums = Utilities.stringToIntArray(ticket.getNumbers());
                    for(int n: winningNumbers){
                        boolean exist = false;
                        for(int t: ticketNums){
                            if(n == t)
                                exist = true;
                        }
                        if(!exist){
                            isWinner = false;
                            break;
                        }
                    }
                    if(isWinner || (ticketNums[0] + ticketNums[19] == 100)){
                        //WINNER o/
                        activeLoto.setWinningTicket(ticket);
                        gameHandler.updateObject(activeLoto);
                        //create new Pale:
                        Classes.Data.Game loto = new Classes.Data.Game();
                        loto.setBaseAmmount(1000000);
                        loto.setType(Classes.Data.Game.GameType.LOTO);
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
                        request.session(true).attribute("user",user); //
                        attributes.put("didWin", true);
                    }
                    else{
                        attributes.put("didWin",false);
                    }
                    attributes.put("template_name","winner/selection.ftl");
                    return new ModelAndView(attributes,Main.BASE_LAYOUT);
                }
                else{
                    request.session(true).attribute("message","El API de Numeros Aleatorios fallo en responder. Lo sentimos.");
                    response.redirect("/user/" + user.getId());
                }
            }

            return null;
        }, new FreeMarkerEngine());

        get("game/pale", (request, response) -> {
            //This takes 20 nums T.T... COMMA SEPARATED :V
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            User user = request.session().attribute("user");

            if(user.getAccount().getBalance() <= 0.00){
                request.session(true).attribute("message","No tiene balance alguno para jugar PALE");
                request.session(true).attribute("message_type","danger");
                response.redirect("/user/" + user.getId());
            }


            attributes.put("template_name","game/pale.ftl");
            return new ModelAndView(attributes, Main.BASE_LAYOUT );
        }, new FreeMarkerEngine());

        post("game/pale", (request, response) -> {
            //This takes 20 nums T.T... COMMA SEPARATED :V
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            User user = request.session().attribute("user");
            try{
                int numa = Integer.parseInt(request.queryParams("numa"));
                int numb = Integer.parseInt(request.queryParams("numb"));
                int numc = Integer.parseInt(request.queryParams("numc"));
                double bet = Double.parseDouble(request.queryParams("ammount"));

                if(bet > user.getAccount().getBalance()){

                    String errors = "No puede apostar mas de lo que tiene en su cuenta.";
                    attributes.put("errors",errors);
                    attributes.put("template_name","game/pale.ftl");
                    return new ModelAndView(attributes, Main.BASE_LAYOUT );

                }

                Classes.Data.Game activePale = gameHandler.findActivePale();

                String ticketPlay = numa + "," + numb + "," + numc;
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
                //72,133
                if(winningNumbers != null){
                    //We can work with this:
                    attributes.put("winningNumbers",winningNumbers);
                    if((winningNumbers[0] == numa &&
                            winningNumbers[1] == numb &&
                            winningNumbers[2] == numc) || (numa + numc == 100)){
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
                        request.session(true).attribute("user",user); //
                        attributes.put("didWin", true);
                    }
                    else{
                        attributes.put("didWin",false);
                    }
                    attributes.put("template_name","winner/selection.ftl");
                    return new ModelAndView(attributes,Main.BASE_LAYOUT);
                }
                else{
                    request.session(true).attribute("message","El API de Numeros Aleatorios fallo en responder. Lo sentimos.");
                    response.redirect("/user/" + user.getId());
                }


                //response.redirect("/user/" + user.getId());

            }
            catch (Exception e){
                e.printStackTrace();
                String errors = "Hubo un problema con su solicitud. Revise sus numeros.";
                attributes.put("errors",errors);
                attributes.put("template_name","game/pale.ftl");
                return new ModelAndView(attributes, Main.BASE_LAYOUT );
            }

            return  null;

        }, new FreeMarkerEngine());
    }

}
