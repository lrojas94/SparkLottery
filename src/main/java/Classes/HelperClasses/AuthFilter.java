package Classes.HelperClasses;

import Classes.Data.AuthRoles;
import Classes.Data.User;
import Classes.Main;
import spark.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class AuthFilter extends CustomFilter {

    private Set<AuthRoles> roles;

    public AuthFilter(TemplateEngine templateEngine) { super(templateEngine); }

    public AuthFilter(TemplateEngine templateEngine, Set<AuthRoles> roles) {
        super(templateEngine);
        this.roles = roles;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        super.handle(request, response);
        //First, check if logged in:
        User user = request.session().attribute("user");
        Map<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
        attributes.put("template_name",this.forbiddenTemplate);
        if(user == null){
            //Its not even logged in
            attributes.put("forbidden_message","Usted no ha iniciado sesion.");
            spark.Spark.halt(401,templateEngine.render(new ModelAndView(attributes,Main.BASE_LAYOUT)));
        }

        for(AuthRoles role : roles) {
            switch (role) {
                case ADMIN:
                    if (!user.getAdmin()) {
                        attributes.put("forbidden_message", "USTED NO ES ADMINISTRADOR");
                        spark.Spark.halt(401, templateEngine.render(new ModelAndView(attributes, Main.BASE_LAYOUT)));
                    }
                    break;
                case PLAYER:
                    if (user == null) {
                        attributes.put("forbidden_message", "USTED NO ES UN USUARIO REGISTRADO");
                        Spark.halt(401, templateEngine.render(new ModelAndView(attributes, Main.BASE_LAYOUT)));
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
