package foodie.project_training.com.foodie.Restaurant.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class RestaurantAdapter extends BaseAdapter {

    public static class ViewHolder {
        @Bind(R.id.cardView)
        CardView cardView;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.city)
        TextView city;
        @Bind(R.id.description)
        TextView description;
        @Bind(R.id.places)
        TextView places;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;
    private List<Restaurant> restaurants;

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.restaurant_item_row, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(restaurants.get(position).getName());
        holder.address.setText(restaurants.get(position).getAddress());
        holder.city.setText(restaurants.get(position).getCity());
        holder.description.setText(restaurants.get(position).getDescription());
        holder.places.setText(String.valueOf(restaurants.get(position).getPlace()) + " seat(s)");

        return convertView;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
