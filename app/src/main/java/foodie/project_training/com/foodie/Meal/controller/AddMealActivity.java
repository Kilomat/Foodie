package foodie.project_training.com.foodie.Meal.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.Meal.model.Meal;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 13/05/2016.
 */
public class AddMealActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar) Toolbar toolbar;

    @Bind(R.id.title) EditText title;
    @Bind(R.id.city) EditText city;
    @Bind(R.id.description) EditText description;
    @Bind(R.id.places) EditText places;
    @Bind(R.id.price) EditText price;

    @Bind(R.id.btn_confirm) ImageButton btnConfirm;

    private static final String PREFS_NAME = "PrefsFile";
    private FoodieLink link;
    private String uid;
    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        final Intent intent = getIntent();

        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (validate(new EditText[] {title, city, description, places, price})) {

                    final MaterialDialog dialog = new MaterialDialog.Builder(AddMealActivity.this)
                            .title("Please wait a moment ...")
                            .progress(true, 0)
                            .progressIndeterminateStyle(true)
                            .show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            link = new FoodieLink(getApplicationContext(), dialog);

                            Meal meal = new Meal();

                            meal.setRestaurantId(intent.getStringExtra("restoId"));
                            meal.setTitle(title.getText().toString());
                            meal.setCity(city.getText().toString());
                            meal.setDescription(description.getText().toString());
                            meal.setPlaces(Integer.valueOf(places.getText().toString()));
                            meal.setPrice(Integer.valueOf(price.getText().toString()));

                            link.addMeal(meal, jwt, new ServerCallBack() {
                                @Override
                                public void onSuccess(JSONObject result) {
                                    try {
                                        Toast.makeText(getApplicationContext(), result.getString("ok"), Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }, 3000);
                } else {
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validate(EditText[] fields){
        for(int i=0; i<fields.length; i++){
            EditText currentField=fields[i];
            if(currentField.getText().toString().length()<=0){
                return false;
            }
        }
        return true;
    }

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

}
