package com.allen.androidalldemos.recycleview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.androidalldemos.R;

import java.util.List;

/**
 * Created by Allen on 2015/12/17.
 */
public class RecycleView_Drag_Adapter extends RecyclerView.Adapter<RecycleView_Drag_Adapter.ViewHolder> {
    private List<String> list;

    public RecycleView_Drag_Adapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.item_main_name_TV));
        }

        public void setData(String name) {
            textView.setText(name);
        }
    }
}
