package smallville7123.liboverview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.inflate;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Overview_Adapter_Samsung_GoodLock_TaskChanger_Grid extends RecyclerView.Adapter<Overview_Adapter_Samsung_GoodLock_TaskChanger_Grid.ViewHolder> {
    private static final String TAG = "Overview_Adapter_Samsun";
    public GridLayoutManager manager;
    Overview overview;
    int global_padding = 75;

    public Overview_Adapter_Samsung_GoodLock_TaskChanger_Grid(Overview overview) {
        this.overview = overview;
    }

    public void setManager(Overview overview) {
        manager = new GridLayoutManager(overview.mContext, 1);
        overview.setLayoutManager(manager);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout application;
        ImageView applicationIcon;
        TextView applicationLabel;
        FrameLayout applicationContentBackground;
        ImageView applicationContent;
        Bitmap applicationContentBitmap;

        public ViewHolder(FrameLayout itemView) {
            super(itemView);
            application = itemView;
            applicationIcon = itemView.findViewById(R.id.applicationIcon);
            applicationLabel = itemView.findViewById(R.id.applicationLabel);
            applicationContentBackground = itemView.findViewById(R.id.applicationContentBackground);
            applicationContent = itemView.findViewById(R.id.applicationContent);

            applicationIcon.setImageResource(android.R.drawable.sym_def_app_icon);
            applicationContent.setBackgroundColor(Color.BLACK);
        }

        public void adjustHeightByRowCount() {
            float containerHeight = overview.getHeight();
            ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            p.height = Math.round(containerHeight/overview.rowCount);
            application.setLayoutParams(p);
        }

        public void adjustForPadding() {
            application.setPadding(global_padding, global_padding, global_padding, global_padding);
        }

        public void setItem(int position) {
            // add item if we can
            if (position < overview.data.size()) {
                applicationIcon.setVisibility(View.VISIBLE);
                applicationLabel.setVisibility(View.VISIBLE);
                applicationContent.setVisibility(View.VISIBLE);
                Overview.DataSet dataSet = overview.data.get(position);
                applicationIcon.setImageDrawable(dataSet.icon);
                applicationLabel.setText(dataSet.title);
                applicationContentBitmap = dataSet.content;
                applicationContent.setImageBitmap(applicationContentBitmap);
            } else {
                applicationIcon.setVisibility(View.INVISIBLE);
                applicationLabel.setVisibility(View.INVISIBLE);
                applicationContent.setVisibility(View.INVISIBLE);
            }
        }

        public void setOnClickListener() {
            if (overview.onClickListener != null) {
                application.setOnClickListener(overview.onClickListener);
            } else {
                application.setOnClickListener(null);
                application.setClickable(false);
            }
        }
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        if (holder.applicationContentBitmap != null) holder.applicationContentBitmap.recycle();
        holder.applicationContent.setImageBitmap(null);
        super.onViewRecycled(holder);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((FrameLayout) inflate(overview.mContext, R.layout.overview_layout_samsung_goodlock_taskchanger_grid, null));
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.adjustHeightByRowCount();
        holder.adjustForPadding();
        holder.setItem(position);
        holder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        // ensure there is at least rowCount items
        int itemSize = overview.data.size();
        return itemSize + (itemSize % overview.columnCount);
    }
}
