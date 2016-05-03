package foodie.project_training.com.foodie.User.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import foodie.project_training.com.foodie.IconTextAdapter;
import foodie.project_training.com.foodie.Momentum.controller.MomentumAdapter;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 13/04/2016.
 */
public class AccountFragment extends Fragment {

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.email)
    TextView email;


    @Bind(R.id.recyclerView)
    RecyclerView    recyclerView;

    @Bind(R.id.edit_btn)
    FloatingActionButton    editBtn;

    private static final String PREFS_NAME = "PrefsFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        final MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("Authenticating ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);
               // String jwt = settings.getString("JWT", "Nothing");
                String uid = settings.getString("UID", "Nothing");

                FoodieLink link = new FoodieLink(getContext(), dialog);
                link.getUser(uid,
                        new ServerCallBack() {

                            @Override
                            public void onSuccess(JSONObject result) {
                                System.out.println("Callback : " + result);
                                JSONArray userArray = null;
                                try {
                                    userArray = result.getJSONArray("User");
                                    for (int i = 0; i < userArray.length(); i++) {
                                        JSONObject userObj = userArray.getJSONObject(i);

                                        final User user = new User();

                                        user.setId(userObj.getString("id"));
                                        user.setEmail(userObj.getString("email"));
                                        user.setFirstName(userObj.getString("firstName"));
                                        user.setLastName(userObj.getString("lastName"));
                                        user.setBirthday(userObj.getString("birthday"));
                                        user.setAddress(userObj.getString("adress"));
                                        user.setCity(userObj.getString("city"));
                                        user.setZipcode(userObj.getDouble("zipcode"));
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
                                        recyclerView.setAdapter(adapter);
                                        editBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent  intent = new Intent(getContext(), EditAccountActivity.class);
                                                intent.putExtra("user", (Serializable)user);
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


        return view;
    }


}
