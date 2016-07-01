package Classes;
/**
 * Created by luis on 5/30/16.
 */

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import Classes.Data.User;
import Classes.PersistenceHandlers.UserHandler;
import Classes.Routers.Admin;
import Classes.Routers.Winners;
import Classes.Routers.Users;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public final static String MODEL_PARAM = "model";
    public static final String BASE_LAYOUT = "header_footer_layout.ftl";
    public static File uploadDir = new File("uploads");

    public static void main(String[] args) {

        staticFiles.location("/public");
        staticFiles.externalLocation("uploads");

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
            return new ModelAndView(attributes, "header_footer_layout.ftl");
        }, new FreeMarkerEngine());

        Winners.Routes(); // Creates Winners Routes
        Users.Routes(); // Creates Users Routes
        Admin.Routes(); //create Admin Routes

    }

}
