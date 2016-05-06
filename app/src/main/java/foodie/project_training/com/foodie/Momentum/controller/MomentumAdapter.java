package foodie.project_training.com.foodie.Momentum.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Momentum.model.Momentum;

/**
 * Created by beau- on 02/04/2016.
 */
public class MomentumAdapter extends RecyclerView.Adapter<MomentumAdapter.MomentViewHolder> {

    public static class MomentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user) TextView user;
        @Bind(R.id.location) TextView location;
        @Bind(R.id.datePost) TextView datePost;
        @Bind(R.id.content) TextView content;

        public MomentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Momentum>  momentums;

    public MomentumAdapter(List<Momentum> momentums) {
        this.momentums = momentums;
    }

    @Override
    public MomentumAdapter.MomentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.momentum_item_row, parent, false);
        MomentViewHolder  mvh = new MomentViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MomentumAdapter.MomentViewHolder holder, int position) {
        holder.user.setText(momentums.get(position).getUserId());
        holder.location.setText(momentums.get(position).getLocation());
        holder.datePost.setText(momentums.get(position).getPostedAt());
        holder.content.setText(momentums.get(position).getContent());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView view) {
        super.onAttachedToRecyclerView(view);
    }

    @Override
    public int getItemCount() {
        return momentums.size();
    }
}
