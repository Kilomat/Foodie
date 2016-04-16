package foodie.project_training.com.foodie.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.MainActivity;
import foodie.project_training.com.foodie.R;

/**
 * Created by beau- on 12/04/2016.
 */
public class AuthenticationActivity extends AppCompatActivity {

    @Bind(R.id.input_layout_email)
    MaterialTextField inputEmail;
    @Bind(R.id.input_layout_password)
    MaterialTextField inputPassword;
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
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
