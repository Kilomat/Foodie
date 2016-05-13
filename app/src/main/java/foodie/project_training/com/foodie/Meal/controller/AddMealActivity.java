package foodie.project_training.com.foodie.Meal.controller;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.api.FoodieLink;

/**
 * Created by beau- on 13/05/2016.
 */
public class AddMealActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar) Toolbar toolbar;

    @Bind(R.id.name) EditText name;
    @Bind(R.id.city) EditText city;
    @Bind(R.id.description) EditText description;
    @Bind(R.id.places) EditText places;

    @Bind(R.id.)

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
