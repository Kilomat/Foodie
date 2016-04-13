package foodie.project_training.com.foodie.Momentum.controller;

import android.os.Bundle;
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
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Momentum.model.Momentum;

/**
 * Created by beau- on 30/03/2016.
 */
public class MomentumFragment extends Fragment {

    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.momentum_fragment, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        List<Momentum>  momentumList = new ArrayList<Momentum>();

        momentumList.add(new Momentum(0, 1,"Title 1", "LaDurée", R.drawable.header_profil, R.drawable.header_background,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget leo at sapien laoreet molestie. Aenean malesuada, ante vel sagittis sodales, libero ante sodales neque"
                , "2016/03/04", "Paris", false, 36));

        momentumList.add(new Momentum(0, 1,"Title 1", "LaDurée", R.drawable.header_profil, R.drawable.header_background,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget leo at sapien laoreet molestie. Aenean malesuada, ante vel sagittis sodales, libero ante sodales neque"
                , "2016/03/04", "Paris", false, 36));

        MomentumAdapter adapter = new MomentumAdapter(momentumList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
