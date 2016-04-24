package foodie.project_training.com.foodie.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.MainActivity;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.api.FoodieRequest;

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
                    if (checkUser())
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    else
                        Toast.makeText(getApplicationContext(), "Invalid email/password", Toast.LENGTH_LONG).show();
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


    private boolean checkUser() {
        FoodieRequest request = new FoodieRequest(this);
        List<User> users = request.getUsers("Users.json");
        if (users != null || users.size() > 0) {
            for (User user : users) {
                System.out.println("user" + user.toString());
                if (user.getEmail().equals(email.getText().toString()) && user.getPassword().equals(password.getText().toString()))
                    return true;
            }
            return false;
        }

        return false;
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
