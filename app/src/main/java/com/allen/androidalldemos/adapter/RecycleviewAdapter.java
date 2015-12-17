package com.allen.androidalldemos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.androidalldemos.MainDataBean;
import com.allen.androidalldemos.R;

import java.util.List;

/**
 * Created by Allen on 2015/12/16.
 */
public class RecycleviewAdapter extends RecyclerView.Adapter<RecycleviewAdapter.ListViewHolder> {
    private Context context;
    private List<MainDataBean> dataBeans;
    public RecycleviewAdapter(Context context, List<MainDataBean> list) {
        this.context = context;
        this.dataBeans = list;
    }

    public OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
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
        //View view = View.inflate(context, R.layout.main_list_item, null);
        View view = LayoutInflater.from(context).inflate(R.layout.main_list_item,null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String name = dataBeans.get(position).getName();
        int id = dataBeans.get(position).getId();
        holder.setData(name,id);
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
         int id;

        public ListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = ((TextView) itemView.findViewById(R.id.item_main_name_TV));
        }

        public void setData(String name,int id) {
            textView.setText(name);
           this.id=id;
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerViewItemClickListener != null) {
                onRecyclerViewItemClickListener.onItemClick(v, id);
            }
        }
    }
}
