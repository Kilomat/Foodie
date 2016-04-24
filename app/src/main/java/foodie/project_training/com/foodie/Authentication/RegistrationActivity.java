package foodie.project_training.com.foodie.Authentication;

import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.User.model.UserSerializer;
import foodie.project_training.com.foodie.api.FoodiePath;
import foodie.project_training.com.foodie.api.FoodieRequest;
import foodie.project_training.com.foodie.api.GsonRequest;

/**
 * Created by beau- on 16/04/2016.
 */
public class RegistrationActivity extends AppCompatActivity {

    @Bind(R.id.email) EditText email;
    @Bind(R.id.password) EditText password;
    @Bind(R.id.confirm_password) EditText confirmPassword;
    @Bind(R.id.btn_confirm) ImageButton  btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        ButterKnife.bind(this);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPassword() == true) {

                    /*if (checkUser()) {
                        FoodieRequest request = new FoodieRequest(getApplicationContext());
                        request.addUser("Users.json", email.getText().toString(), password.getText().toString());
                    }*/

                    User user = new User(email.getText().toString(), password.getText().toString());

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(User.class, new UserSerializer());
                    gsonBuilder.setPrettyPrinting();

                    Gson gson = gsonBuilder.create();
                    String userJson = gson.toJson(user);
/*
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("email", user.getEmail());
                    params.put("password", user.getPassword());
                    System.out.println("gson" + gson.toJson(params));*/

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("email", user.getEmail());
                    jsonObject.addProperty("password", user.getPassword());

                    System.out.println("jsonObject " + jsonObject.toString());
                    GsonRequest<User>  userRequest = new GsonRequest<>(Request.Method.POST,
                            FoodiePath._USERS_,
                            jsonObject.toString(),
                            new TypeToken<User>() {}.getType(),
                            gson,
                            new Response.Listener<User>() {
                                @Override
                                public void onResponse(User response) {
                                    System.out.println("responses : " + response.toString());
//                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("ERROR : "  +  error.toString());
                                }
                            });
/*
                    JsonObjectRequest userRequest = null;

                    userRequest = new JsonObjectRequest(Request.Method.POST,
                        FoodiePath._USERS_, new JSONObject(params),
                        new Response.Listener<JSONObject>() {

                             @Override
                             public void onResponse(JSONObject response) {
                                 System.out.println("ResponseJSON ");
                             }
                         },
                         new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 System.out.println("error " + error.toString());
                                 /*String statusCode = String.valueOf(error.networkResponse.statusCode);
                                 System.out.println("statusCode : " + statusCode);
                                 if ( error.networkResponse.data != null) {
                                     try {
                                         System.out.println("error : " + new String(error.networkResponse.data, "UTF-8"));
                                     } catch (UnsupportedEncodingException e) {
                                         e.printStackTrace();
                                     }
                                 } else {
                                     System.out.println("error null");
                                 }
                             }
                         }) {

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }
                    };

*/

/*                    StringRequest userRequest = new StringRequest(Request.Method.POST, FoodiePath._USERS_,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println("MARCHE");
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    String responseBody = null;
                                    try {
                                        responseBody = new String( error.networkResponse.data, "utf-8" );
                                        System.out.println("error : " + responseBody);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }) {
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("emailol", email.getText().toString());
                            params.put("passwordod", password.getText().toString());
                            return params;
                        }

                        @Override
                        public HashMap<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String,String> params = new HashMap<String, String>();
                            params.put("Content-Type","application/json");
                            return params;
                        }
                    };
                    */
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(userRequest);
                }
            }
        });
    }


    private boolean checkUser() {
        FoodieRequest request = new FoodieRequest(this);
        List<User> users = request.getUsers("Users.json");
        if (users != null || users.size() > 0) {
            for (User user : users) {
                System.out.println("user" + user.toString());
                if (user.getEmail().equals(email.getText().toString())) {
                    Toast.makeText(this, "This email is already registered", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean checkPassword() {
        if (password.getText().toString().trim().length() >= 6) {
            System.out.println("password " + password.getText().toString().trim() + " " + confirmPassword.getText().toString().trim());
            if (password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                return true;
            } else {
                Toast.makeText(this, "Your password doesn't match", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Your password must have at least 6 characters !", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
