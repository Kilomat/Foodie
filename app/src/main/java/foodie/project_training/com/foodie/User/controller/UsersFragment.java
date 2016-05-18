package foodie.project_training.com.foodie.User.controller;

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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.Utils.MyLocation;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 13/04/2016.
 */
public class UsersFragment extends Fragment {

    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.lvUsers) ListView lvUsers;

    private static final String PREFS_NAME = "PrefsFile";
    private FoodieLink link;
    private MaterialDialog dialog;

    private static String uid;
    private static String jwt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_fragment, container, false);
        ButterKnife.bind(this, view);

        dialog = new MaterialDialog.Builder(getContext())
                .title("Please wait a moment ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();

        link = new FoodieLink(getContext(), dialog);

        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        displayUser();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayUser();
            }
        });

        return view;
    }

    private void displayUser() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                link.getAllUsers(jwt,
                        new ServerCallBack() {

                            @Override
                            public void onSuccess(JSONObject result) {
                                System.out.println("Callback : " + result);
                                JSONArray userArray = null;
                                try {
                                    userArray = result.getJSONArray("User");
                                    List<User> users = new ArrayList<User>();

                                    for (int i = 0; i < userArray.length(); i++) {
                                        JSONObject userObj = userArray.getJSONObject(i);
                                        User user = new User();

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

                                        users.add(user);
                                    }

                                    dialog = new MaterialDialog.Builder(getContext())
                                            .title("Please wait a moment ...")
                                            .progress(true, 0)
                                            .progressIndeterminateStyle(true)
                                            .build();

                                    UsersAdapter adapter = new UsersAdapter(getContext(), users, dialog);
                                    lvUsers.setAdapter(adapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                );
            }
        }, 3000);
    }
}
