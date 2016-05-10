package foodie.project_training.com.foodie.Momentum.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.User.controller.EditAccountActivity;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.Utils.MyLocation;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 30/03/2016.
 */
public class MomentumFragment extends Fragment {

    @Bind(R.id.edit_moment) EditText myMoment;
    @Bind(R.id.btn_send) ImageButton sendBtn;
    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    private static final String PREFS_NAME = "PrefsFile";
    private FoodieLink  link;
    private String uid;
    private String jwt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.momentum_fragment, container, false);
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

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyLocation location = new MyLocation(getContext());
                        link.addMoment(myMoment.getText().toString(), location.getCurrentCity(), jwt);
                    }
                }, 3000);
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        refreshContent();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        return view;
    }

    private void refreshContent() {

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
                                        userObj.getString("postedAt"));
                                momentumList.add(momentum);
                            }

                            MomentumAdapter adapter = new MomentumAdapter(momentumList);
                            recyclerView.setAdapter(adapter);
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
