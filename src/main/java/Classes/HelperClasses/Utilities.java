package Classes.HelperClasses;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by luis on 7/1/16.
 */
public class Utilities {
    public static String intArrayToString(int[] arr){
        String s = "";
        for(int i = 0; i < arr.length;i++){
            s+= arr[i];
            if(!(i+1 < arr.length)){
                s += ",";
            }
        }

        return s;
    }

    public static int[] stringToIntArray(String arr){
        String[] str = arr.split(",");
        int[] result = new int[str.length];
        int i = 0;
        for(String s : str){
            result[i] = Integer.parseInt(s);
            i++;
        }

        return result;

    }
}
