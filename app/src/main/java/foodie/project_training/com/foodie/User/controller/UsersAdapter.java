package foodie.project_training.com.foodie.User.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.User.model.User;
import foodie.project_training.com.foodie.api.FoodieLink;

/**
 * Created by beau- on 02/04/2016.
 */
public class UsersAdapter extends BaseAdapter {

    private Context context;
    private List<User> users;

    private static final String PREFS_NAME = "PrefsFile";
    private String    uid;
    private String    jwt;

    private FoodieLink link;
    private MaterialDialog dialog;

    public static class ViewHolder  {
        @Bind(R.id.cardView) CardView  cardView;
        @Bind(R.id.firstName) TextView  firstName;
        @Bind(R.id.lastName) TextView lastName;
        @Bind(R.id.email) TextView email;
        @Bind(R.id.bio) TextView bio;
        @Bind(R.id.add_btn) ImageButton addBtn;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public UsersAdapter(Context context, List<User> users, MaterialDialog dialog) {
        this.context = context;
        this.users = users;
        this.dialog = dialog;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView  = inflater.inflate(R.layout.user_item_row, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.firstName.setText(users.get(position).getFirstName());
        holder.lastName.setText(users.get(position).getLastName());
        holder.email.setText(users.get(position).getEmail());
        holder.bio.setText(users.get(position).getBio());

        SharedPreferences settings = this.context.getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                        link = new FoodieLink(context, dialog);
                        link.monitorFriends(users.get(position).getId(), true, jwt);
                    }
                }, 3000);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
