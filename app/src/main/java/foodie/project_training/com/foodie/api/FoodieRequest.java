package foodie.project_training.com.foodie.api;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import foodie.project_training.com.foodie.User.model.User;

/**
 * Created by beau- on 17/04/2016.
 */
public class FoodieRequest extends Application {

    private Context context;

    public FoodieRequest(Context context) {
        this.context = context;
    }

    public String  loadJSONFromAsset(String path) {
        String  json = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public String  loadJSONFromLocal(String path) {
        String json = null;
        InputStream is = null;
        try {
            File file = new File(context.getFilesDir(), path);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            while ((json = br.readLine()) != null)
                sb.append(json);
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }

    public List<User> getUsers(String path) {
        JsonParser parser = new JsonParser();
 //       JsonObject mainObj = (JsonObject) parser.parse(loadJSONFromAsset(path));
        JsonObject mainObj = (JsonObject) parser.parse(loadJSONFromLocal(path));
        JsonArray usersArray = null;

        if (mainObj.has("Users")) {
            usersArray = mainObj.get("Users") != null ? mainObj.get("Users").getAsJsonArray() : new JsonArray();
        }

        Gson gson = new Gson();
        List<User> usersList = new ArrayList<>();
        if (usersArray.size() > 0) {
            for (JsonElement elmt : usersArray) {
                JsonObject  obj = elmt.getAsJsonObject();
                usersList.add(gson.fromJson(obj, User.class));
            }
        }

        return usersList;
    }

    public void addUser(String path, String email, String password) {
        JsonParser parser = new JsonParser();
 //       JsonObject mainObj = (JsonObject) parser.parse(loadJSONFromAsset(path));
        JsonObject mainObj = (JsonObject) parser.parse(loadJSONFromLocal(path));
        JsonArray usersArray = null;

        if (mainObj.has("Users")) {
            usersArray = mainObj.get("Users") != null ? mainObj.get("Users").getAsJsonArray() : new JsonArray();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<User>(){}.getType();
        User user = new User(String.valueOf(usersArray.size() + 1), email, password);

        try {
            System.out.println("gson : " + gson.toJson(user, type).toString() + " path " + context.getAssets().open(path).toString());

            FileWriter writer = new FileWriter(context.getFilesDir() + "/" + path);
            writer.write(gson.toJson(user, type));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
