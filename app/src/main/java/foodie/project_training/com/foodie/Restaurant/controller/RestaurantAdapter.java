package foodie.project_training.com.foodie.Restaurant.controller;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;

/**
 * Created by beau- on 02/04/2016.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @Nullable @Bind(R.id.user) TextView user;
        @Nullable @Bind(R.id.name) TextView name;
        @Nullable @Bind(R.id.address) TextView address;
        @Nullable @Bind(R.id.city) TextView city;
        @Nullable @Bind(R.id.description) TextView description;
        @Nullable @Bind(R.id.places) TextView places;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Restaurant>  restaurants;

    public RestaurantAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_row, parent, false);
        RestaurantViewHolder  rvh = new RestaurantViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.RestaurantViewHolder holder, int position) {
        holder.user.setText(restaurants.get(position).getUserId());
        holder.name.setText(restaurants.get(position).getName());
        holder.address.setText(restaurants.get(position).getAddress());
        holder.city.setText(restaurants.get(position).getCity());
        holder.description.setText(restaurants.get(position).getDescription());
        holder.places.setText(restaurants.get(position).getPlace());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView view) {
        super.onAttachedToRecyclerView(view);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
