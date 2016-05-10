package foodie.project_training.com.foodie;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by beau- on 02/04/2016.
 */
public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> imgData;
    private List<String>  titleData;


    public static class ViewHolder  {
        @Bind(R.id.icon) ImageView  icon;
        @Bind(R.id.title) TextView  title;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public GalleryAdapter(Context context,
                          List<Integer> imgData,
                          List<String>  titledata) {
        this.context = context;
        this.imgData = imgData;
        this.titleData = titledata;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView  = inflater.inflate(R.layout.gallery_item_row, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageResource(imgData.get(position));
        holder.title.setText(titleData.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return titleData.size();
    }

    @Override
    public Object getItem(int position) {
        return titleData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
