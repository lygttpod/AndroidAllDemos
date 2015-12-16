package com.allen.androidalldemos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.androidalldemos.R;

import java.util.List;

/**
 * Created by Allen on 2015/12/16.
 */
public class RecycleviewAdapter extends RecyclerView.Adapter<RecycleviewAdapter.ListViewHolder> {
    private Context context;
    private List<String> list;

    public RecycleviewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    /**
     * 自定义item点击事件接口
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.main_listview_item, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String name = list.get(position);
        holder.setData(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;

        public ListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = ((TextView) itemView.findViewById(R.id.item_name_TV));
        }

        public void setData(String name) {
            textView.setText(name);
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerViewItemClickListener != null) {
                onRecyclerViewItemClickListener.onItemClick(v, getPosition());
            }
        }
    }
}
