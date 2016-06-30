package Classes.Game;

import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * Created by Edward on 30-Jun-16.
 */

public class GsonRequest {
    private final String url = "https://api.random.org/json-rpc/1/invoke";
    private final String jsonrpc = "2.0";
    private String method;
    private int numbersAmmount;
    private final String apiKey = "b9a2029e-e515-4e93-bef6-c76e5c34bc92";
    private int min = 0;
    private int max = 99;
    private final boolean replacement = false;


    public GsonRequest(String method,int numbersAmount, int min, int max)
    {
        this.method=method;
        this.numbersAmmount = numbersAmount;
        this.min = min;
        this.max = max;

    }

    public JsonObject RequestNumbers() throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity input = new StringEntity("{\n" +
                "    \"jsonrpc\": \"" + jsonrpc + "\",\n" +
                "    \"method\": \"" + method +"\",\n" +
                "    \"params\": { \n" +
                "        \"apiKey\": \"" + apiKey +"\",\n" +
                "        \"n\": "+ numbersAmmount + ",\n" +
                "        \"min\": "+ min + ",\n" +
                "        \"max\": " + max +",\n" +
                "        \"replacement\":" + replacement +"\n" +
                "    },\n" +
                "    \"id\": 42\n" +
                "}");
        input.setContentType("application/json");
        httpPost.setEntity(input);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String body = "";
        try {

            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            body = EntityUtils.toString(entity2);
            EntityUtils.consume(entity2);
        } finally {
            response.close();
        }

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject)jsonParser.parse(body);
        return jsonObject;
    }

    public JsonArray getNumbers(JsonObject jsonObject)
    {
        return jsonObject.get("result").getAsJsonObject().get("random").getAsJsonObject().get("data").getAsJsonArray();


    }

}
