package foodie.project_training.com.foodie.Meal.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.Meal.model.Meal;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;
import foodie.project_training.com.foodie.api.FoodieLink;

/**
 * Created by beau- on 02/04/2016.
 */
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image) ImageView image;
        @Bind(R.id.title) TextView title;
        @Bind(R.id.city) TextView city;
        @Bind(R.id.datePost) TextView datePost;
        @Bind(R.id.description) TextView description;
        @Bind(R.id.places) TextView places;
        @Bind(R.id.participate) ImageButton participateBtn;

        @Bind(R.id.btn_layout) LinearLayout btnLayout;
        @Bind(R.id.edit_btn) ImageButton editBtn;
        @Bind(R.id.delete_btn) ImageButton deleteBtn;

        public MealViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context     context;
    private String      restaurantId;
    private List<Meal>  meals;

    private static final String PREFS_NAME = "PrefsFile";
    private String    uid;
    private String    jwt;

    public MealAdapter(Context context, String restaurantId, List<Meal> meals) {
        this.context = context;
        this.restaurantId = restaurantId;
        this.meals = meals;
        System.out.println("meals ; " + meals.toString());
    }

    @Override
    public MealAdapter.MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_row, parent, false);
        MealViewHolder  vh = new MealViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MealAdapter.MealViewHolder holder, int position) {

        SharedPreferences settings = this.context.getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        holder.title.setText(meals.get(position).getTitle());
        holder.city.setText(meals.get(position).getCity());
        holder.datePost.setText(meals.get(position).getPostedAt());
        holder.description.setText(meals.get(position).getDescription());
        holder.places.setText(String.valueOf(meals.get(position).getPlaces()));

        holder.participateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog dialog = new MaterialDialog.Builder(context)
                        .title("Please wait a moment ...")
                        .progress(true, 0)
                        .progressIndeterminateStyle(true)
                        .show();

                FoodieLink link = new FoodieLink(context, dialog);
            }
        });

        if (meals.get(position).getRestaurantId().equals(restaurantId)) {
            holder.btnLayout.setVisibility(LinearLayout.VISIBLE);
        }

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
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
        return meals.size();
    }
}
