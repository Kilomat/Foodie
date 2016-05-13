package foodie.project_training.com.foodie.Restaurant.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.Meal.controller.AddMealActivity;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 06/05/2016.
 */
public class InfoRestaurantActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar) Toolbar toolbar;
    @Bind(R.id.name) TextView name;
    @Bind(R.id.address) TextView address;
    @Bind(R.id.city) TextView city;
    @Bind(R.id.description) TextView description;
    @Bind(R.id.places) TextView places;

    @Bind(R.id.add_btn) ImageButton addMealBtn;
    @Bind(R.id.recyclerView_meals) RecyclerView recyclerViewMeals;
    @Bind(R.id.edit_btn) FloatingActionButton editBtn;

    private static final String PREFS_NAME = "PrefsFile";
    private FoodieLink  link;
    private String uid;
    private String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_restaurant_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        final MaterialDialog dialog = new MaterialDialog.Builder(InfoRestaurantActivity.this)
                .title("Please wait a moment ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = getIntent();

                final String restoId = intent.getStringExtra("restoId");

                link = new FoodieLink(getApplicationContext(), dialog);
                link.getInfoRestaurant(restoId, jwt, new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            final JSONObject obj = result.getJSONObject("Restaurant");
                            name.setText(obj.getString("name"));
                            address.setText(obj.getString("adress"));
                            city.setText(obj.getString("city"));
                            description.setText(obj.getString("description"));
                            places.setText(String.valueOf(obj.getInt("places")));

                            if ( uid.equals(obj.getString("userId")) ) {
                                editBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            Restaurant restaurant = new Restaurant(obj.get("id"),
                                                    obj.getString("userId"),
                                                    obj.getString("name"),
                                                    obj.getString("adress"),
                                                    obj.getString("city"),
                                                    obj.getString("description"),
                                                    obj.getInt("places"));
                                            Intent intent = new Intent(InfoRestaurantActivity.this, CreateRestaurantActivity.class);
                                            intent.putExtra("restaurant", restaurant);
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 3000);

        addMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoRestaurantActivity.this, AddMealActivity.class);
                startActivity(intent);
            }
        });
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
