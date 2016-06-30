package Classes.Routers;


import static Classes.Main.uploadDir;
import static Classes.Main.cloudinary;
import static spark.Spark.*;

import Classes.Data.User;
import Classes.Data.Winner;
import Classes.DataTables.ReturnData;
import Classes.DataTables.SentParameters;
import Classes.Main;
import Classes.PersistenceHandlers.WinnerHandler;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * Created by MEUrena on 6/29/16.
 */
public class Winners {
    private static final WinnerHandler winnerHandler = WinnerHandler.getInstance();

    public static void Routes() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        post("/datatable/winners", (request, response) -> {
            try {
                SentParameters dtParameters = gson.fromJson(request.body(), SentParameters.class);
                ReturnData dtReturnData = new ReturnData();
                List<Winner> winners = winnerHandler.findWinnersWithLimit(dtParameters.getLength(), dtParameters.getStart());
                dtReturnData.setRecordsTotal(winnerHandler.winnerCount());
                dtReturnData.setData(winners.toArray());
                dtReturnData.setDraw(dtParameters.getDraw());
                dtReturnData.setRecordsFiltered(dtReturnData.getRecordsTotal());
                dtReturnData.setError(null);

                return dtReturnData;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }, gson::toJson);

        get("/winner/list", (request, response) -> {
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            attributes.put("template_name","winner/list.ftl");

            // We should implement DataTables to query all winners

            List<Winner> winners = winnerHandler.getAllObjects();

            attributes.put("winners", winners);

            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        }, new FreeMarkerEngine());

        get("/winner/add", (request, response) -> {
            //login form
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);
            attributes.put("template_name","winner/add.ftl");

            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        }, new FreeMarkerEngine());

        post("/winner/upload",(request, response) -> {
            HashMap<String,Object> attributes = request.attribute(Main.MODEL_PARAM);

            if (request.session(true).attribute("user") != null) {
                User currentUser = request.session(true).attribute("user");
                String comment = "";
                Date nowDate = new Date();

                String fileName = "/img_" + currentUser.getId() + "_" + nowDate.getTime();

                Path tempFile = Paths.get(uploadDir.getAbsolutePath() + fileName + ".jpg");
                request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

                try (InputStream input = request.raw().getPart("comment").getInputStream()) {
                    comment = convertStreamToString(input);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }

                try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
                    Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }

                String imageString = "img_" + currentUser.getId() + "_" + nowDate.getTime();
                Map uploadResult = cloudinary.uploader().upload(tempFile.toFile(),
                        ObjectUtils.asMap("public_id", imageString,
                                "transformation", new Transformation().width(200).height(200).crop("fill")));
                String imageUrl = (String) uploadResult.get("url");

                insertWinnerToDatabase(comment, imageUrl, currentUser);

            } else {
                // There's no user. Show error message...
            }

            response.redirect("/");

            return new ModelAndView(attributes,Main.BASE_LAYOUT);
        }, new FreeMarkerEngine());
    }

    private static String convertStreamToString(InputStream input) {
        Scanner scanner = new Scanner(input).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private static void insertWinnerToDatabase(String comment, String imgPath, User player) throws Exception {
        Winner winner = new Winner();

        winner.setComment(comment);
        winner.setPath(imgPath);
        winner.setPlayer(player);

        try {
            winnerHandler.insertIntoDatabase(winner);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
