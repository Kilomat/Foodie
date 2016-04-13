package foodie.project_training.com.foodie;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import foodie.project_training.com.foodie.Momentum.model.Momentum;

/**
 * Created by beau- on 02/04/2016.
 */
public class IconTextAdapter extends RecyclerView.Adapter<IconTextAdapter.ViewHolder> {

    private List<Integer> imgData;
    private List<String>  titleData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon) ImageView  icon;
        @Bind(R.id.title) TextView  title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public IconTextAdapter(List<Integer> imgData,
                           List<String>  titledata) {
        this.imgData = imgData;
        this.titleData = titledata;
    }

    @Override
    public IconTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ic_and_text_row, parent, false);
        ViewHolder  vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.icon.setImageResource(imgData.get(position));
        holder.title.setText(titleData.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView view) {
        super.onAttachedToRecyclerView(view);
    }

    @Override
    public int getItemCount() {
        return imgData.size();
    }
}
