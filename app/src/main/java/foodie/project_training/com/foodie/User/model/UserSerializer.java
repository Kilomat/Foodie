package foodie.project_training.com.foodie.User.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by beau- on 24/04/2016.
 */
public class UserSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("email", user.getEmail());
        object.addProperty("password", user.getPassword());
        object.addProperty("lastName", user.getLastName());
        object.addProperty("firstName", user.getFirstName());
        object.addProperty("birthday", user.getBirthday());
        object.addProperty("address", user.getAddress());
        object.addProperty("city", user.getCity());
        object.addProperty("zipCode", user.getZipcode());
        object.addProperty("phone", user.getPhone());
        object.addProperty("gender", user.getGender());

        return object;
    }
}
