package foodie.project_training.com.foodie.Authentication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
