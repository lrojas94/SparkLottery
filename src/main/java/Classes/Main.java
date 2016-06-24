package Classes;
/**
 * Created by luis on 5/30/16.
 */

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {
    public static void main(String[] args) {
        staticFiles.location("/public");
        enableDebugScreen();

        get("/", (request, response) -> {
            return new ModelAndView(null, "header_footer_layout.ftl");
        }, new FreeMarkerEngine());

    }

}
