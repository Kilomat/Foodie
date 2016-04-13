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
        @Bind(R.id.title) TextView    title;
        @Bind(R.id.username) TextView    username;
        @Bind(R.id.profile_image) ImageView   profile;
        @Bind(R.id.picture) ImageView   picture;
        @Bind(R.id.comment) TextView    comment;
        @Bind(R.id.date) TextView     date;
        @Bind(R.id.place) TextView    place;

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
        holder.title.setText(momentums.get(position).getTitle());
        holder.username.setText(momentums.get(position).getUsername());
        holder.profile.setImageResource(momentums.get(position).getProfile());
        holder.picture.setImageResource(momentums.get(position).getPicture());
        holder.comment.setText(momentums.get(position).getComment());
        holder.date.setText(momentums.get(position).getDate());
        holder.place.setText(momentums.get(position).getPlace());
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
