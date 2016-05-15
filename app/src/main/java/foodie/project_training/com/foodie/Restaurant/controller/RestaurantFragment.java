package foodie.project_training.com.foodie.Restaurant.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.Momentum.controller.MomentumAdapter;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;
import foodie.project_training.com.foodie.Utils.MyLocation;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 30/03/2016.
 */
public class RestaurantFragment extends Fragment {

    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.lvRestaurants) ListView lvRestaurants;
    @Bind(R.id.add_btn) FloatingActionButton addButton;

    private static final String PREFS_NAME = "PrefsFile";
    private FoodieLink  link;
    private String uid;
    private String jwt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_fragment, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        final MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("Please wait a moment ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();

        link = new FoodieLink(getContext(), dialog);

        refreshContent();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateRestaurantActivity.class));
            }
        });

        return view;
    }

    private void refreshContent() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                link.getRestaurants(jwt, new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        JSONArray array = null;

                        try {
                            final List<Restaurant>  restaurants = new ArrayList<>();
                            array = result.getJSONArray("Restaurants");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Restaurant restaurant = new Restaurant(
                                        object.get("id"),
                                        object.getString("userId"),
                                        object.getString("name"),
                                        object.getString("adress"),
                                        object.getString("city"),
                                        object.getString("description"),
                                        object.getInt("places"));
                                restaurants.add(restaurant);
                            }

                            RestaurantAdapter adapter = new RestaurantAdapter(getContext(), restaurants);

                            lvRestaurants.setAdapter(adapter);
                            lvRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getContext(), InfoRestaurantActivity.class);
                                    intent.putExtra("restoId", restaurants.get(position).getId().toString());
                                    startActivity(intent);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }
}
