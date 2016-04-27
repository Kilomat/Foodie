package foodie.project_training.com.foodie.Authentication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.User.model.UserSerializer;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.FoodiePath;
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

                    final ProgressDialog dialog = new ProgressDialog(RegistrationActivity.this, R.style.AppTheme_NoActionBar);
                    dialog.setIndeterminate(true);
                    dialog.setMessage("Creating a new user ...");
                    dialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FoodieLink link = new FoodieLink(getApplicationContext(), dialog);
                            link.createUser(email.getText().toString(), password.getText().toString());
                        }
                    }, 3000);

                    /*
                    JsonObjectRequest userRequest = null;

                    userRequest = new JsonObjectRequest(Request.Method.POST,
                        FoodiePath._USERS_, new JSONObject(params),
                        new Response.Listener<JSONObject>() {

                             @Override
                             public void onResponse(JSONObject response) {
                                 System.out.println("ResponseJSON " + response);
                             }
                         },
                         new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 System.out.println("error " + error.toString());
                                 String statusCode = String.valueOf(error.networkResponse.statusCode);
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
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a login and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
