package foodie.project_training.com.foodie.Authentication;

import android.os.Bundle;
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

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.User.model.User;
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
                    User    user = new User("", email.getText().toString().trim(), password.getText().toString().trim());

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("email", user.getEmail());
                    headers.put("password", user.getPassword());

                    GsonRequest<User>  userRequest = new GsonRequest<User>(Request.Method.POST,
                            FoodiePath._USERS_,
                            User.class,
                            headers,
                            new Response.Listener<User>() {
                                @Override
                                public void onResponse(User response) {
                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(userRequest);
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
