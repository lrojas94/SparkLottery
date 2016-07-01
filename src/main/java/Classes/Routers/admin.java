package Classes.Routers;

import Classes.Data.AuthRoles;
import Classes.Data.User;
import Classes.HelperClasses.AuthFilter;
import Classes.HelperClasses.DatabaseHandler;
import Classes.Main;
import Classes.PersistenceHandlers.UserHandler;
import spark.ModelAndView;
import spark.route.Routes;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by Edward on 30-Jun-16.
 */
public class Admin {
    public static void Routes(){
        get("/admin",(request, response) -> {
            List<User> listUsers = UserHandler.getInstance().findAllUsers();
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            attributes.put("users",listUsers);
            attributes.put("template_name","admin/admin.ftl");
            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        },new FreeMarkerEngine());

        post("/admin/delete/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            UserHandler.getInstance().deleteObjectWithId(id);

            response.redirect("/admin");
            return "";
        });

        before("/admin/*",new AuthFilter(new FreeMarkerEngine(),new HashSet<AuthRoles>(){{
            add(AuthRoles.ADMIN);
        }}));
        before("/admin",new AuthFilter(new FreeMarkerEngine(),new HashSet<AuthRoles>(){{
            add(AuthRoles.ADMIN);
        }}));

    }
}
