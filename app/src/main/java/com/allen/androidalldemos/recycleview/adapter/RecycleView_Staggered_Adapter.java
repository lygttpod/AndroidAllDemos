package com.allen.androidalldemos.recycleview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.recycleview.bean.DataBean;
import com.allen.androidalldemos.recycleview.itemTouchHelper.ItemTouchHelperViewHolder;

import java.util.List;

/**
 * 瀑布流效果
 * Created by Allen on 2015/12/16.
 */
public class RecycleView_Staggered_Adapter extends RecyclerView.Adapter<RecycleView_Staggered_Adapter.StaggeredViewHolder> {

    private List<DataBean> dataBeans;
    private Context mContext;

    public RecycleView_Staggered_Adapter(Context mContext, List<DataBean> dataBeans) {
        this.mContext = mContext;
        this.dataBeans = dataBeans;
    }

    @Override
    public StaggeredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.activity_recycleview_staggered_item, null);
        return new StaggeredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaggeredViewHolder holder, int position) {
        DataBean dataBean = dataBeans.get(position);
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class StaggeredViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private ImageView icon;
        private TextView title;

        public StaggeredViewHolder(View itemView) {
            super(itemView);
            icon = ((ImageView) itemView.findViewById(R.id.item_iv_icon));
            title = ((TextView) itemView.findViewById(R.id.item_tv_title));
        }

        public void setData(DataBean dataBean) {
            icon.setImageResource(dataBean.getIcon());
            title.setText(dataBean.getTitle());
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.RED);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.TRANSPARENT);

        }
    }
}
