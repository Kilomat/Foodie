package foodie.project_training.com.foodie.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import foodie.project_training.com.foodie.MainActivity;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.User.model.UserSerializer;

/**
 * Created by beau- on 10/04/2016.
 */
public class FoodieLink {

    private Context context;
    private ProgressDialog dialog;

    public FoodieLink(Context context, ProgressDialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    public void createUser(String email, String password) {

        User user = new User(email, password);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserSerializer());
        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        System.out.println("gson" + gson.toJson(params));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("password", user.getPassword());

        System.out.println("jsonObject " + jsonObject.toString());
        GsonRequest<JSONObject>  userRequest = new GsonRequest<>(Request.Method.POST,
                FoodiePath._USERS_,
                jsonObject.toString(),
                new TypeToken<JSONObject>() {}.getType(),
                gson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        Toast.makeText(context, "You can now login !", Toast.LENGTH_LONG).show();
                        System.out.println("responses : " + response.toString());
//                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        System.out.println("statusCode : " + statusCode);
                        if ( error.networkResponse.data != null) {
                            try {
                                Toast.makeText(context, new String(error.networkResponse.data, "UTF-8"), Toast.LENGTH_LONG).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(userRequest);
    }

    public void authUser(String email, String password) {
        User user = new User(email, password);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserSerializer());
        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("password", user.getPassword());

        GsonRequest<JSONObject> authRequest = new GsonRequest<>(Request.Method.POST,
                FoodiePath._USERS_AUTH_,
                jsonObject.toString(),
                new TypeToken<JSONObject>() {}.getType(),
                gson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        System.out.println("statusCode : " + statusCode);
                        if ( error.networkResponse.data != null) {
                            try {
                                Toast.makeText(context, new String(error.networkResponse.data, "UTF-8"), Toast.LENGTH_LONG).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(authRequest);
    }
}
