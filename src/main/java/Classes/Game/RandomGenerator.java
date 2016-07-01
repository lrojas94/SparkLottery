package Classes.Game;

import Classes.HelperClasses.Utilities;
import com.google.gson.JsonArray;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;


/**
 * Created by Edward on 30-Jun-16.
 */
public class RandomGenerator {

    public int[] getNumbers(int quantity)
    {
        GsonRequest request = new GsonRequest("generateIntegers",quantity,0,99);
        JsonArray numbers = null;
        try {
            numbers = request.getNumbers(request.RequestNumbers());
            String nums = numbers.toString();
            nums = nums.substring(1,nums.length()-2);
            return Utilities.stringToIntArray(nums);
        }
        catch (Exception e){
            System.err.println(e);
        }

        return null;
    }

    public JsonArray loto()
    {
        GsonRequest request = new GsonRequest("generateIntegers",5,0,99);
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
