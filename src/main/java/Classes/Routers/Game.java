package Classes.Routers;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import Classes.Data.*;
import Classes.HelperClasses.AuthFilter;
import Classes.Main;
import Classes.PersistenceHandlers.*;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.awt.image.RescaleOp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
            Classes.Data.Game activePale = gameHandler.findActiveLoto();

            if(activeLoto == null){
                activeLoto = new Classes.Data.Game();
                activeLoto.setType(Classes.Data.Game.GameType.LOTO);
                gameHandler.insertIntoDatabase(activeLoto);
            }

            if(activePale == null){
                activePale = new Classes.Data.Game();
                activePale.setType(Classes.Data.Game.GameType.PALE);
                gameHandler.insertIntoDatabase(activeLoto);
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


            Pattern regex = Pattern.compile("((\\d+)(,\\s*\\d+){19})");
            Matcher matcher = regex.matcher(request.queryParams("nums"));
            if(!matcher.matches()){
                attributes.put("nums",request.queryParams("nums"));
                attributes.put("errors","Se requiere insertar una cadena de 20 numeros separados por coma.");
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

                //Should redirect to randomizer but now we will redirect to user:
                request.session(true).attribute("message","Usted ha jugado la Loto satisfactoriamente!");
                response.redirect("/user/" + user.getId());
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

                Classes.Data.Game activePale = gameHandler.findActiveLoto();

                String ticketPlay = numa + "," + numb + "," + numc;
                Ticket ticket = new Ticket();
                ticket.setNumbers(ticketPlay);
                ticket.setBetAmount(bet);
                ticket.setOwner(user);
                ticket.setIssuedIn(activePale);

                Transaction trans = Main.createTicketTransaction(ticket);
                ticketHandler.insertIntoDatabase(ticket);
                transactionHandler.insertIntoDatabase(trans);

                request.session(true).attribute("message","Usted ha jugado la Pale!");
                response.redirect("/user/" + user.getId());

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
