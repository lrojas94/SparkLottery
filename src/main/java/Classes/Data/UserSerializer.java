package Classes.Data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by MEUrena on 7/20/16.
 * All rights reserved.
 */
public class UserSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
        JsonObject root = new JsonObject();
        root.addProperty("id", user.getId());
        root.addProperty("first_name", user.getFirstName());
        root.addProperty("last_name", user.getLastName());
        root.addProperty("email", user.getEmail());
        root.addProperty("username", user.getUsername());
        root.addProperty("balance", user.getAccount().getBalance());

        return root;
    }
}
