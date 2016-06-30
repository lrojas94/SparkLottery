package Classes.Routers;


import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import Classes.Data.Account;
import Classes.Data.AuthRoles;
import Classes.Data.Transaction;
import Classes.Data.User;
import Classes.HelperClasses.AuthFilter;
import Classes.Main;
import Classes.PersistenceHandlers.AccountHandler;
import Classes.PersistenceHandlers.TransactionHandler;
import Classes.PersistenceHandlers.UserHandler;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.awt.image.RescaleOp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
/**
 * Created by luis on 6/27/16.
 */
public class Users {
    public static void Routes(){
        final UserHandler userHandler = UserHandler.getInstance();
        final TransactionHandler transactionHandler = TransactionHandler.getInstance();
        final AccountHandler accountHandler = AccountHandler.getInstance();
        //Find admin:
        try{
            User admin = userHandler.findUserByUsername("admin");
            if(admin == null){
                //create him:
                admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin");
                admin.setFirstName("Administrador");
                admin.setLastName("");
                admin.setAccount(null);
                admin.setAdmin(true);
                userHandler.insertIntoDatabase(admin);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        before("/user/:userId/*",new AuthFilter(new FreeMarkerEngine(),new HashSet<AuthRoles>(){{
            add(AuthRoles.OWNER);
        }}));

        get("/user/login",(request, response) -> {
           //login form
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            attributes.put("template_name","user/login.ftl");

            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        },new FreeMarkerEngine());

        post("/user/login",(request, response) -> {
            //login form
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);

            User user = userHandler.loginUser(request.queryParams("username"), request.queryParams("password"));
            if(user == null){
                //not logged in:
                attributes.put("error_message","Usuario o contrasena erroneos.");
            }
            else{
                request.session(true).attribute("user",user);
                response.redirect("/");
                return null;
            }


            attributes.put("template_name","user/login.ftl");

            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        },new FreeMarkerEngine());

        get("/user/create",(request, response) -> {
            //login form
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            attributes.put("template_name","user/signup.ftl");

            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        },new FreeMarkerEngine());

        post("/user/create",(request, response) -> {
            //login form
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            String error_message = "";
            if(request.queryParams("username") == null){
                error_message += "Se ha de incluir un usuario<br/>";
            }
            if(request.queryParams("password") == null){
                error_message += "Se ha de incluir un contrasena<br/>";
            }
            else if (request.queryParams("password_2") == null) {
                error_message += "Se ha de incluir un contrasena<br/>";
            }
            else if(!request.queryParams("password").equals(request.queryParams("password_2"))){
                error_message += "Las contrasenas han de ser coincidir.<br/>";
            }
            if(request.queryParams("firstName") == null){
                error_message += "Se ha de incluir un primer nombre<br/>";
            }
            if(request.queryParams("lastName") == null){
                error_message += "Se ha de incluir un apellido<br/>";
            }
            if(request.queryParams("email") == null){
                error_message += "Se ha de inccluir un email<br/>";
            }

            if(error_message.equals("")){
                User user = new User();
                user.setUsername(request.queryParams("username"));
                user.setPassword(request.queryParams("password"));
                user.setFirstName(request.queryParams("firstName"));
                user.setLastName(request.queryParams("lastName"));
                user.setEmail(request.queryParams("email"));
                Account account = new Account();
                account.setOwner(user);
                user.setAccount(account);
                userHandler.insertIntoDatabase(user);
                //Login user:
                request.session(true).attribute("user",user);
                response.redirect("/");
            }
            attributes.put("error_message",error_message);
            attributes.put("template_name","user/signup.ftl");
            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        },new FreeMarkerEngine());

        get("/user/:id",(request, response) -> {
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            User user = userHandler.findObjectWithId(Integer.parseInt(request.params("id")));
            attributes.put("message",request.session(true).attribute("message"));
            attributes.put("message_type",request.session(true).attribute("message_type"));
            attributes.put("User",user);
            attributes.put("template_name","user/profile.ftl");
            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        },new FreeMarkerEngine());

        get("/user/:id/addfunds",(request, response) -> {
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            try{
                int id = Integer.parseInt(request.params("id"));
                User user = userHandler.findObjectWithId(id);
                attributes.put("User",user);

                attributes.put("template_name","user/addfunds.ftl");
                return new ModelAndView(attributes,Main.BASE_LAYOUT);
            }
            catch (Exception e){
                response.redirect("/");
            }

            return null;
        },new FreeMarkerEngine());

        post("/user/:id/addfunds",(request, response) -> {
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            try{
                int id = Integer.parseInt(request.params("id"));
                User user = userHandler.findObjectWithId(id);

                double amount = Double.parseDouble(request.queryParams("ammountToAdd"));
                String creditCard = request.queryParams("creditCard");
                String month = request.queryParams("month");
                String year = request.queryParams("year");
                String cvc = request.queryParams("cvc");
                String msg = request.queryParams("msg");
                String cardType = request.queryParams("cctype");

                String error = "";
                if(amount <= 100){
                    error += "No se pueden agregar menos de $100.00 pesos.<br/>";
                }
                if(creditCard == null || creditCard.equals("")){
                    error += "La tarjeta de credito no puede estar vacia.<br/>";
                }
                if(month == null || month.equals("")){
                    error += "El Mes no puede estar vacia.<br/>";
                }
                if(year == null || year.equals("")){
                    error += "El anio no puede estar vacio. <br/>";
                }
                if(cvc == null || cvc.equals("")){
                    error += "El cvc no puede estar vacio. <br/>";
                }
                if(cardType == null || cardType.equals("")){
                    error += "El tipo de tarjeta no puede estar vacio. <br/>";
                }


                if(error.equals("")){
                    Transaction t = new Transaction();
                    t.setAmmount(amount);
                    t.setMessage(msg);
                    t.setMethod(Transaction.Method.CREDITCARD);
                    t.setDescription("Transferencia con Tarjeta de Credito terminada en: "
                            + creditCard.substring(creditCard.length() - 4,creditCard.length()));
                    t.setOwner(user.getAccount());
                    user.getAccount().getTransactions().add(t);
                    transactionHandler.insertIntoDatabase(t);
                    request.session(true).attribute("message_type","success");
                    request.session(true).attribute("message","Transaccion realizada correctamente.");
                    response.redirect("/user/"+user.getId());

                }
                else{

                    attributes.put("error",error);
                    attributes.put("User",user);
                    attributes.put("template_name","user/addfunds.ftl");
                    return new ModelAndView(attributes,Main.BASE_LAYOUT);
                }

            }
            catch (Exception e){
                response.redirect("/");
            }

            return null;
        },new FreeMarkerEngine());


    }
}
