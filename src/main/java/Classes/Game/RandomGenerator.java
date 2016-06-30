package Classes.Game;

import com.google.gson.JsonArray;


/**
 * Created by Edward on 30-Jun-16.
 */
public class RandomGenerator {

    public JsonArray pale()
    {
        GsonRequest request = new GsonRequest("generateIntegers",3,0,99);
        JsonArray numbers = null;
        try {
            numbers = request.getNumbers(request.RequestNumbers());

        }
        catch (Exception e){
            System.err.println(e);
        }
        return numbers;
    }

    public JsonArray loto()
    {
        GsonRequest request = new GsonRequest("generateIntegers",20,0,99);
        JsonArray numbers = null;
        try {
            numbers = request.getNumbers(request.RequestNumbers());

        }
        catch (Exception e){
            System.err.println(e);
        }
        return numbers;
    }


}
