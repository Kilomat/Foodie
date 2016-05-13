package foodie.project_training.com.foodie.User.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.GalleryAdapter;
import foodie.project_training.com.foodie.IconTextAdapter;
import foodie.project_training.com.foodie.Momentum.controller.MomentumAdapter;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Restaurant.controller.InfoRestaurantActivity;
import foodie.project_training.com.foodie.Restaurant.controller.RestaurantAdapter;
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.Utils.MyLocation;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 13/04/2016.
 */
public class AccountFragment extends Fragment {

    @Bind(R.id.name) TextView name;
    @Bind(R.id.email) TextView email;
    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView_user) RecyclerView recyclerViewUser;
    @Bind(R.id.gridView_restaurants) GridView gridViewRestaurants;
    @Bind(R.id.edit_moment) EditText myMoment;
    @Bind(R.id.btn_send) ImageButton sendBtn;
    @Bind(R.id.recyclerView_momentums) RecyclerView recyclerViewMomentums;
    @Bind(R.id.edit_btn) FloatingActionButton editBtn;

    private static final String PREFS_NAME = "PrefsFile";
    private FoodieLink link;
    private MaterialDialog dialog;

    private static String uid;
    private static String jwt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerViewUser.setHasFixedSize(true);
        recyclerViewMomentums.setHasFixedSize(true);
        LinearLayoutManager llmUser = new LinearLayoutManager(getContext());
        recyclerViewUser.setLayoutManager(llmUser);
        LinearLayoutManager llmMomentums = new LinearLayoutManager(getContext());
        recyclerViewMomentums.setLayoutManager(llmMomentums);

        dialog = new MaterialDialog.Builder(getContext())
                .title("Please wait a moment ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();

        link = new FoodieLink(getContext(), dialog);

        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        displayUserInfo();
        displayRestaurants();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                        .title("Please wait a moment ...")
                        .progress(true, 0)
                        .progressIndeterminateStyle(true)
                        .show();
                link = new FoodieLink(getContext(), dialog);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyLocation location = new MyLocation(getContext());
                        link.addMoment(myMoment.getText().toString(), location.getCurrentCity(), jwt);
                    }
                }, 3000);
            }
        });

        displayMomentums();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayUserInfo();
                displayRestaurants();
                displayMomentums();
            }
        });

        return view;
    }

    private void displayUserInfo() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                link.getUser(uid, jwt,
                        new ServerCallBack() {

                            @Override
                            public void onSuccess(JSONObject result) {
                                System.out.println("Callback : " + result);
                                JSONArray userArray = null;
                                try {
                                    userArray = result.getJSONArray("User");
                                    for (int i = 0; i < userArray.length(); i++) {
                                        final JSONObject userObj = userArray.getJSONObject(i);

                                        final User user = new User();

                                        user.setId(userObj.getString("id"));
                                        user.setEmail(userObj.getString("email"));
                                        user.setFirstName(userObj.getString("firstName"));
                                        user.setLastName(userObj.getString("lastName"));
                                        user.setBirthday(userObj.getString("birthday"));
                                        user.setAddress(userObj.getString("adress"));
                                        user.setCity(userObj.getString("city"));
                                        user.setZipcode(userObj.getInt("zipcode"));
                                        user.setBio(userObj.getString("bio"));
                                        user.setGender(userObj.getString("gender"));
                                        user.setPhone(userObj.getString("phone"));
                                        user.setNotification(userObj.getString("notification"));


                                        name.setText(user.getFirstName() + " " + user.getLastName());
                                        email.setText(user.getEmail());

                                        List<Integer> imgList = new ArrayList<>();
                                        imgList.add(R.drawable.account_birthday_icon);
                                        imgList.add(R.drawable.account_address_icon);
                                        imgList.add(R.drawable.account_bio_icon);
                                        imgList.add(R.drawable.account_gender_icon);
                                        imgList.add(R.drawable.account_phone_icon);

                                        List<String> titleList = new ArrayList<>();
                                        titleList.add(user.getBirthday());
                                        titleList.add(user.getAddress());
                                        titleList.add(user.getBio());
                                        titleList.add(user.getGender());
                                        titleList.add(user.getPhone());

                                        IconTextAdapter adapter = new IconTextAdapter(imgList, titleList);
                                        recyclerViewUser.setAdapter(adapter);
                                        editBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent  intent = new Intent(getContext(), EditAccountActivity.class);
                                                intent.putExtra("user", user);
                                                startActivity(intent);
                                            }
                                        });
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }
        }, 3000);
    }

    private void displayRestaurants() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                link.getRestaurantsByUser(uid, jwt, new ServerCallBack() {
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

                            List<Integer> imgList = new ArrayList<>();
                            List<String> titleList = new ArrayList<>();

                            for ( Restaurant restaurant : restaurants ) {
                                imgList.add(R.drawable.restaurant_icon);
                                titleList.add(restaurant.getName());
                                System.out.println("Resto " + restaurant.getName());
                            }

                            GalleryAdapter adapter = new GalleryAdapter(getContext(), imgList, titleList);
                            gridViewRestaurants.setAdapter(adapter);

                            gridViewRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void displayMomentums() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                link.getMomentByUser(uid, jwt, new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        JSONArray userArray = null;
                        List<Momentum>  momentumList = new ArrayList<>();

                        try {
                            userArray = result.getJSONArray("Moments");
                            for (int i = 0; i < userArray.length(); i++) {
                                JSONObject userObj = userArray.getJSONObject(i);
                                Momentum momentum = new Momentum(
                                        userObj.get("id"),
                                        userObj.getString("userId"),
                                        userObj.getString("content"),
                                        userObj.getString("location"),
                                        userObj.getString("postedAt"),
                                        userObj.getBoolean("deleted"));
                                momentumList.add(momentum);
                            }

                            MomentumAdapter adapter = new MomentumAdapter(getContext(), momentumList);
                            recyclerViewMomentums.setAdapter(adapter);
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
