package foodie.project_training.com.foodie.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
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
    private static final String PREFS_NAME = "PrefsFile";

    public FoodieLink(Context context, ProgressDialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    public void createUser(String email, String password) {

        User user = new User(email, password);

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("password", user.getPassword());

        CustomStringRequest userRequest = new CustomStringRequest(Request.Method.POST,
                FoodiePath._USERS_,
                jsonObject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  objectResp = new JSONObject(response);
                            Toast.makeText(context, objectResp.getString("ok"), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if ( error.networkResponse.data != null) {
                            try {
                                String statusCode = String.valueOf(error.networkResponse.statusCode);
                                JSONObject  errorObject = new JSONObject(new String(error.networkResponse.data));
                                String errorString = errorObject.getString("error");
                                Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(userRequest);
    }

    public void authUser(final String email, final String password) {
        User user = new User(email, password);

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("password", user.getPassword());

        CustomStringRequest authRequest = new CustomStringRequest(Request.Method.POST,
                    FoodiePath._USERS_AUTH_,
                    jsonObject,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject  obj = new JSONObject(response);
                                SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("JWT", obj.getString("JWT"));
                                editor.putString("UID", obj.getString("uid"));
                                editor.commit();

                                dialog.dismiss();
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            if ( error.networkResponse.data != null) {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject  errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(authRequest);
    }

    public void getUser(String _id, final ServerCallBack callBack) {

        final User user = new User();

        CustomStringRequest authRequest = new CustomStringRequest(Request.Method.GET,
                FoodiePath._USERS_ + "/" + _id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
//                        System.out.println("response : " + response);
                        try {
                            callBack.onSuccess(new JSONObject(response));
                            /*JSONObject  respObj = new JSONObject(response);
                            JSONArray   userArray = new JSONArray(respObj.getJSONArray("User"));
                            for (int i = 0; i < userArray.length(); i++) {
                                JSONObject userObj = userArray.getJSONObject(i);
                                user.setEmail(userObj.getString("email"));
                                user.setFirstName(userObj.getString("firstName"));
                                user.setLastName(userObj.getString("lastName"));
                                user.setBirthday(userObj.getString("birthday"));
                                user.setAddress(userObj.getString("address"));
                                user.setCity(userObj.getString("city"));
                                user.setZipcode(userObj.getDouble("zipcode"));
                                user.setBio(userObj.getString("bio"));
                                user.setGender(userObj.getString("gender"));
                                user.setPhone(userObj.getString("phone"));
                                user.setNotification(userObj.getString("notification"));

                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if ( error.networkResponse.data != null) {
                            try {
                                String statusCode = String.valueOf(error.networkResponse.statusCode);
                                JSONObject  errorObject = new JSONObject(new String(error.networkResponse.data));
                                String errorString = errorObject.getString("error");
                                Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(authRequest);
    }


    public void updateUser(User user) {

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("password", user.getPassword());

        CustomStringRequest userRequest = new CustomStringRequest(Request.Method.PUT,
                FoodiePath._USERS_ + "/" + user.getId(),
                jsonObject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  objectResp = new JSONObject(response);
                            Toast.makeText(context, objectResp.getString("ok"), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if ( error.networkResponse.data != null) {
                            try {
                                String statusCode = String.valueOf(error.networkResponse.statusCode);
                                JSONObject  errorObject = new JSONObject(new String(error.networkResponse.data));
                                String errorString = errorObject.getString("error");
                                Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(userRequest);
    }
}
