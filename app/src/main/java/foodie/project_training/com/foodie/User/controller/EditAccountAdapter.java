package foodie.project_training.com.foodie.User.controller;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.R;

/**
 * Created by beau- on 05/05/2016.
 */
public class EditAccountAdapter extends RecyclerView.Adapter<EditAccountAdapter.EditAccountViewHolder> {

    private List<String> fields;

    public EditAccountAdapter(List<String> fields) {
        this.fields = fields;
    }

    private static OnItemClickListener itemClickListener;

    public static class EditAccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.field) EditText field;

        public EditAccountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            field.setTag(this);
            field.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(v, getLayoutPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int pos);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public EditAccountAdapter.EditAccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_account_item_row, parent, false);
        EditAccountViewHolder  mvh = new EditAccountViewHolder(v);
        return mvh;
    }

    private int lastFocussedPosition = -1;
    private Handler handler = new Handler();

    @Override
    public void onBindViewHolder(final EditAccountAdapter.EditAccountViewHolder holder, final int position) {
        holder.field.setHint(fields.get(position));
        holder.field.setTag(this);
        holder.field.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (lastFocussedPosition == -1 || lastFocussedPosition == position) {
                                lastFocussedPosition = position;
                                holder.field.requestFocus();
                            }
                        }
                    }, 200);

                } else {
                    lastFocussedPosition = -1;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView view) {
        super.onAttachedToRecyclerView(view);
    }

}
