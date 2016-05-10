package foodie.project_training.com.foodie.Restaurant.controller;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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
        @Bind(R.id.cardView) CardView cardView;
        @Bind(R.id.name) TextView name;
        @Bind(R.id.address) TextView address;
        @Bind(R.id.city) TextView city;
        @Bind(R.id.description) TextView description;
        @Bind(R.id.places) TextView places;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Restaurant>  restaurants;

    public RestaurantAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        System.out.println("rest ; " + restaurants.toString());
    }

    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_row, parent, false);
        RestaurantViewHolder  rvh = new RestaurantViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.RestaurantViewHolder holder, int position) {
        holder.name.setText(restaurants.get(position).getName());
        holder.address.setText(restaurants.get(position).getAddress());
        holder.city.setText(restaurants.get(position).getCity());
        holder.description.setText(restaurants.get(position).getDescription());
        holder.places.setText(String.valueOf(restaurants.get(position).getPlace()) + " seat(s)");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
