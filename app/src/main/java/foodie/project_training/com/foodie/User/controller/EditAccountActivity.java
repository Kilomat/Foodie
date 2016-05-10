package foodie.project_training.com.foodie.User.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.Momentum.controller.MomentumAdapter;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.api.FoodieLink;

/**
 * Created by beau- on 13/04/2016.
 */
public class EditAccountActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar) Toolbar toolbar;
    @Bind(R.id.ll) LinearLayout ll;
    @Bind(R.id.btn_confirm) ImageButton btnConfirm;

    private static final String PREFS_NAME = "PrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        final User user = (User)intent.getSerializableExtra("user");

        final List<String> fields = new ArrayList<>();

  //      fields.add(new String("Old password"));
//        fields.add(new String("New password"));
        fields.add(new String("Confirm new password"));
        fields.add(new String("Lastname"));
        fields.add(new String("Firstname"));
        fields.add(new String("Address"));
        fields.add(new String("City"));
        fields.add(new String("Zip code"));
        fields.add(new String("Bio"));
        fields.add(new String("Gender"));
        fields.add(new String("Phone"));

        final List<String> listArg = new ArrayList<>();

        listArg.add(0, user.getPassword());
        listArg.add(1, user.getLastName());
        listArg.add(2, user.getFirstName());
        listArg.add(3, user.getAddress());
        listArg.add(4, user.getCity());
        listArg.add(5, String.valueOf(user.getZipcode()));
        listArg.add(6, user.getBio());
        listArg.add(7, user.getGender());
        listArg.add(8, user.getPhone());


        final List<EditText> listEd = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++){
            final EditText editText = new EditText(this);
            final int cnt = i;
            editText.setHint(fields.get(i));
            editText.setSingleLine(true);
            editText.setTextColor(Color.WHITE);
            editText.setHintTextColor(Color.WHITE);
            editText.setTypeface(Typeface.DEFAULT_BOLD);
            listEd.add(editText);
            ll.addView(editText);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                String uid = settings.getString("UID", "Nothing");
                final String jwt = settings.getString("JWT", "Nothing");

                final MaterialDialog dialog = new MaterialDialog.Builder(EditAccountActivity.this)
                        .title("Updating your informations ...")
                        .progress(true, 0)
                        .progressIndeterminateStyle(true)
                        .show();

                for (int i = 0; i < listEd.size(); i++) {
                    EditText editText = listEd.get(i);
                    if ( editText.getText().toString().trim().length() > 0)
                        listArg.set(i, editText.getText().toString());
                }
                for (int i = 0; i < listArg.size(); i++) {
                    System.out.println("listArg : " + listArg.get(i) + " Pos : " + i);
                }
                user.setPassword(listArg.get(0));
                user.setLastName(listArg.get(1));
                user.setFirstName(listArg.get(2));
                user.setAddress(listArg.get(3));
                user.setCity(listArg.get(4));
                user.setZipcode(Integer.valueOf(listArg.get(5)));
                user.setBio(listArg.get(6));
                user.setGender(listArg.get(7));
                user.setPhone(listArg.get(8));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FoodieLink link = new FoodieLink(getApplicationContext(), dialog);
                        link.updateUser(user, jwt);
                    }
                }, 3000);

            }
        });
    }

/*
    private String getContentFromEditText(int pos)  {
        return ((EditText)recyclerView.getChildAt(pos)).getText().toString();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean checkPassword(String password, String confirmPassword) {
        if (password.trim().length() >= 6) {
            System.out.println("password " + password.trim() + " " + confirmPassword.trim());
            if (password.trim().equals(confirmPassword.trim())) {
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
