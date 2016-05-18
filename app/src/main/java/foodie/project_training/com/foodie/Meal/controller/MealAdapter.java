package foodie.project_training.com.foodie.Meal.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.Meal.model.Meal;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Restaurant.controller.InfoRestaurantActivity;
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 02/04/2016.
 */
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title) TextView title;
        @Bind(R.id.city) TextView city;
        @Bind(R.id.datePost) TextView datePost;
        @Bind(R.id.description) TextView description;
        @Bind(R.id.places) TextView places;
        @Bind(R.id.participate) ImageButton participateBtn;
        @Bind(R.id.price) TextView price;

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
    private MaterialDialog dialog;

    private static final String PREFS_NAME = "PrefsFile";
    private String    uid;
    private String    jwt;

    public MealAdapter(Context context, String restaurantId, List<Meal> meals, MaterialDialog dialog) {
        this.context = context;
        this.restaurantId = restaurantId;
        this.meals = meals;
        this.dialog = dialog;
    }

    @Override
    public MealAdapter.MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_row, parent, false);
        MealViewHolder  vh = new MealViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MealAdapter.MealViewHolder holder, final int position) {

        SharedPreferences settings = this.context.getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        holder.title.setText(meals.get(position).getTitle());
        holder.city.setText(meals.get(position).getCity());
        holder.datePost.setText(meals.get(position).getPostedAt());
        holder.description.setText(meals.get(position).getDescription());
        holder.places.setText(meals.get(position).getParticipants().length() + " user(s) registered");

        final FoodieLink link = new FoodieLink(context, dialog);

        holder.participateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        link.participateToMeal(meals.get(position).getId().toString(), true, jwt, new ServerCallBack() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                try {
                                    Toast.makeText(context, result.getString("ok"), Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }, 3000);
            }
        });

        holder.price.setText(meals.get(position).getPrice() + " Yuan(s)");

        if (meals.get(position).getRestaurantId().equals(restaurantId))
            holder.btnLayout.setVisibility(LinearLayout.VISIBLE);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        meals.get(position).setActive(false);
                        link.monitorMeal(meals.get(position), jwt, new ServerCallBack() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                try {
                                    Toast.makeText(context, result.getString("ok"), Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }, 3000);
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
