package foodie.project_training.com.foodie.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.MainActivity;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.User.model.UserSerializer;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.FoodiePath;
import foodie.project_training.com.foodie.api.GsonRequest;

/**
 * Created by beau- on 12/04/2016.
 */
public class AuthenticationActivity extends AppCompatActivity {

    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.btn_authenticate)
    ImageButton btnAuthenticate;
    @Bind(R.id.create_account)
    TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);
        ButterKnife.bind(this);

        btnAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    final ProgressDialog dialog = new ProgressDialog(AuthenticationActivity.this, R.style.AppTheme_NoActionBar);
                    dialog.setIndeterminate(true);
                    dialog.setMessage("Authenticating ...");
                    dialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FoodieLink link = new FoodieLink(getApplicationContext(), dialog);
                            link.authUser(email.getText().toString(), password.getText().toString());
                        }
                    }, 3000);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your login/password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
    }


    private boolean checkInput() {
        if (!email.getText().toString().isEmpty() || !password.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
