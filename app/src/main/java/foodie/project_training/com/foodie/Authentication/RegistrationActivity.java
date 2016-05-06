package foodie.project_training.com.foodie.Authentication;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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

        password.setTypeface(Typeface.DEFAULT_BOLD);
        password.setTransformationMethod(new PasswordTransformationMethod());
        confirmPassword.setTypeface(Typeface.DEFAULT_BOLD);
        confirmPassword.setTransformationMethod(new PasswordTransformationMethod());

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPassword() == true) {

                    final MaterialDialog dialog = new MaterialDialog.Builder(RegistrationActivity.this)
                            .title("Creating a new user ...")
                            .progress(true, 0)
                            .progressIndeterminateStyle(true)
                            .show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FoodieLink link = new FoodieLink(getApplicationContext(), dialog);
                            link.createUser(email.getText().toString(), password.getText().toString());
                        }
                    }, 3000);

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
