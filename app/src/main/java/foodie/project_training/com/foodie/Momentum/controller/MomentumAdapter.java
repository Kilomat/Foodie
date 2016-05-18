package foodie.project_training.com.foodie.Momentum.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import foodie.project_training.com.foodie.R;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.User.controller.AccountFragment;
import foodie.project_training.com.foodie.api.FoodieLink;
import foodie.project_training.com.foodie.api.ServerCallBack;

/**
 * Created by beau- on 02/04/2016.
 */
public class MomentumAdapter extends RecyclerView.Adapter<MomentumAdapter.MomentViewHolder> {

    public static class MomentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user) TextView user;
        @Bind(R.id.friend_btn) ImageButton friendBtn;
        @Bind(R.id.delete_btn) ImageButton deleteBtn;
        @Bind(R.id.location) TextView location;
        @Bind(R.id.datePost) TextView datePost;
        @Bind(R.id.content) TextView content;

        public MomentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;
    private List<Momentum>  momentums;

    private static final String PREFS_NAME = "PrefsFile";
    private String    uid;
    private String    jwt;

    private FoodieLink link;
    private MaterialDialog dialog;

    public MomentumAdapter(Context context, List<Momentum> momentums) {
        this.context = context;
        this.momentums = momentums;
    }

    @Override
    public MomentumAdapter.MomentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.momentum_item_row, parent, false);
        MomentViewHolder  mvh = new MomentViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MomentumAdapter.MomentViewHolder holder, final int position) {

        SharedPreferences settings = this.context.getSharedPreferences(PREFS_NAME, 0);
        uid = settings.getString("UID", "Nothing");
        jwt = settings.getString("JWT", "Nothing");

        holder.user.setText(momentums.get(position).getUserId());

        dialog = new MaterialDialog.Builder(context)
                .title("Please wait a moment ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();
        link = new FoodieLink(context, dialog);

        holder.friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        link.monitorFriends(momentums.get(position).getUserId(), true, jwt);
                    }
                }, 3000);
            }
        });

        if (uid.equals(momentums.get(position).getUserId()))
            holder.deleteBtn.setVisibility(LinearLayout.VISIBLE);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        link.deleteMoment(momentums.get(position), jwt, new ServerCallBack() {
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
