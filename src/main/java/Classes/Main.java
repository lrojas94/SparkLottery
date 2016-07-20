package Classes;
/**
 * Created by luis on 5/30/16.
 */

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import Classes.Data.Ticket;
import Classes.Data.Transaction;
import Classes.Data.Constants;
import Classes.Data.User;
import Classes.Data.Winner;
import Classes.PersistenceHandlers.UserHandler;
import Classes.Routers.*;
import Classes.PersistenceHandlers.WinnerHandler;
import com.cloudinary.utils.ObjectUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import com.cloudinary.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

public class Main {
    public final static String MODEL_PARAM = "model";
    public static final String BASE_LAYOUT = "header_footer_layout.ftl";
    public static File uploadDir = new File("./src/main/resources/public/uploads");
    public static Cloudinary cloudinary = null;

    private static final HashMap<String, String> corsHeaders = new HashMap<String, String>();

    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }

    public final static void apply() {
        spark.Filter filter = new spark.Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                corsHeaders.forEach((key, value) -> {
                    response.header(key, value);
                });
            }
        };
        Spark.after(filter);
    }


    public static Transaction createTicketTransaction(Ticket ticket){
        Transaction t = new Transaction();
        t.setOwner(ticket.getOwner().getAccount());
        t.setDescription("Juego de " + ticket.getIssuedIn().getType());
        t.setAmmount(-ticket.getBetAmount());
        t.setMethod(Transaction.Method.GAMETICKET);

        return t;
    }

    public static void main(String[] args) {
        setupCloudinary();

        staticFiles.location("/public");
        staticFiles.externalLocation("uploads");
        apply();

        enableDebugScreen();

        uploadDir.mkdir();

        before((request, response) -> {
            //Add base model to everything:
            Map<String,Object> attributes = new HashMap<>();
            attributes.put("logged_in",request.session(true).attribute("user") != null);
            attributes.put("user",request.session(true).attribute("user"));

            if(request.session().attribute("message_type") != null){ //Redirect messages

                attributes.put("message_type",request.session().attribute("message_type"));
                attributes.put("message",request.session().attribute("message"));

            }

            request.session().attribute("message_type",null);
            request.session().attribute("message",null);

            request.attribute(MODEL_PARAM,attributes);
        });

        after((request, response) -> {
            //Update user after requests:
            if(request.session(true).attribute("user") != null){
                //update user:
                User currentUser = request.session().attribute("user");
                UserHandler userHandler = UserHandler.getInstance();
                User user = userHandler.findObjectWithId(currentUser.getId());
                request.session(true).attribute("user",user);
            }
        });

        get("/", (request, response) -> {
            HashMap<String,Object> attributes = request.attribute(MODEL_PARAM);
            attributes.put("template_name","index.ftl");

            WinnerHandler winnerHandler = WinnerHandler.getInstance();
            List<Winner> winners = winnerHandler.getAllObjects();
            attributes.put("winners", winners);

            return new ModelAndView(attributes, BASE_LAYOUT);
        }, new FreeMarkerEngine());

        Winners.Routes(); // Creates Winners Routes
        Users.Routes(); // Creates Users Routes
        Admin.Routes(); //create Admin Routes
        Game.Routes();
        API.Routes();
    }

    private static void setupCloudinary() {
        cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", Constants.CLOUDINARY_CLOUD_NAME,
                                                    "api_key", Constants.CLOUDINARY_API_KEY,
                                                    "api_secret", Constants.CLOUDINARY_API_SECRET));
    }

}
