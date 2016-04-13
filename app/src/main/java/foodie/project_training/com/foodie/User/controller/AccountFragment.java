package foodie.project_training.com.foodie.User.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.IconTextAdapter;
import foodie.project_training.com.foodie.Momentum.controller.MomentumAdapter;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.R;

/**
 * Created by beau- on 13/04/2016.
 */
public class AccountFragment extends Fragment {

    @Bind(R.id.recyclerView)
    RecyclerView    recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        List<Integer> imgList = new ArrayList<>();
        imgList.add(R.drawable.account_birthday_icon);
        imgList.add(R.drawable.account_address_icon);
        imgList.add(R.drawable.account_bio_icon);
        imgList.add(R.drawable.account_gender_icon);
        imgList.add(R.drawable.account_phone_icon);

        List<String> titleList = new ArrayList<>();
        titleList.add("04/07/1994");
        titleList.add("Beijing Jiatong DaXue");
        titleList.add("Blabalabalablablba");
        titleList.add("Male");
        titleList.add("182-0161-8329");

        IconTextAdapter adapter = new IconTextAdapter(imgList, titleList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
