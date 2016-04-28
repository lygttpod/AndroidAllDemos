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
 * Created by Allen on 2015/12/16.
 */
public class RecycleView_List_Adapter extends RecyclerView.Adapter<RecycleView_List_Adapter.ListViewHolder> {

    private Context mContext;
    private List<DataBean> dataBeans;

    public RecycleView_List_Adapter(Context mContext,List<DataBean> dataBeans) {
        this.mContext = mContext;
        this.dataBeans = dataBeans;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //当viewholder创建时回调
        View view = View.inflate(mContext, R.layout.activity_recycleview_list_item,null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        //当viewholder绑定数据时候回调
        DataBean dataBean = dataBeans.get(position);
        holder.setDatas(dataBean);
    }

    @Override
    public int getItemCount() {
        if (dataBeans != null){
            return dataBeans.size();
        }
        return 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        ImageView icon;
        TextView title;
        public ListViewHolder(final View itemView) {
            super(itemView);
            icon = ((ImageView) itemView.findViewById(R.id.item_iv_icon));
            title = ((TextView) itemView.findViewById(R.id.item_tv_title));
        }

        public void setDatas(DataBean dataBean) {
            //设置数据的方法 // TODO: 2015/12/16  
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
