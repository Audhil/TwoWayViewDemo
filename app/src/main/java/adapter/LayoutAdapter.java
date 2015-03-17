package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.smdaudhilbe.mohammed_2284.ztwowayviewdemo.R;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;
import org.lucasr.twowayview.widget.StaggeredGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammed-2284 on 17/03/15.
 */
public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.MyViewHolder> {

    private static final int COUNT = 100;
    private final TwoWayView recyclerView;
    private final Context mContext;
    private final int mLayoutId;
    private int mCurrentItemId = 0;
    private List<Integer> mItems;

    public LayoutAdapter(Context contxt, int layoutId, TwoWayView recyclerView) {
        this.mContext = contxt;
        this.mLayoutId = layoutId;
        this.recyclerView = recyclerView;

        mItems = new ArrayList<Integer>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            addItem(i);
        }
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.txTView.setText(mItems.get(position).toString());

        boolean isVertical = (recyclerView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);

        View itemViewIs = myViewHolder.itemView;
        Integer itemId = mItems.get(position);

        if (mLayoutId == R.layout.layout_staggered_grid) {
            final int dimenId;
            if (itemId % 3 == 0) {
                dimenId = R.dimen.staggered_child_medium;
            } else if (itemId % 5 == 0) {
                dimenId = R.dimen.staggered_child_large;
            } else if (itemId % 7 == 0) {
                dimenId = R.dimen.staggered_child_xlarge;
            } else {
                dimenId = R.dimen.staggered_child_small;
            }

            final int span;
            if (itemId == 2) {
                span = 2;
            } else {
                span = 1;
            }

            final int size = mContext.getResources().getDimensionPixelSize(dimenId);

            final StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) itemViewIs.getLayoutParams();

            if (!isVertical) {
                lp.span = span;
                lp.width = size;
                itemViewIs.setLayoutParams(lp);
            } else {
                lp.span = span;
                lp.height = size;
                itemViewIs.setLayoutParams(lp);
            }
        } else if (mLayoutId == R.layout.layout_spannable_grid) {
            final SpannableGridLayoutManager.LayoutParams lp = (SpannableGridLayoutManager.LayoutParams) itemViewIs.getLayoutParams();

            final int span1 = (itemId == 0 || itemId == 3 ? 2 : 1);
            final int span2 = (itemId == 0 ? 2 : (itemId == 3 ? 3 : 1));

            final int colSpan = (isVertical ? span2 : span1);
            final int rowSpan = (isVertical ? span1 : span2);

            if (lp.rowSpan != rowSpan || lp.colSpan != colSpan) {
                lp.rowSpan = rowSpan;
                lp.colSpan = colSpan;
                itemViewIs.setLayoutParams(lp);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //  ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txTView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txTView = (TextView) itemView.findViewById(R.id.titleIs);
        }
    }
}