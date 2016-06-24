package Classes.HelperClasses;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class CustomFilter implements Filter {

    protected TemplateEngine templateEngine;
    protected final String forbiddenTemplate = "./forbidden.ftl";

    public CustomFilter(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        // Let elements implement this...
    }

}
