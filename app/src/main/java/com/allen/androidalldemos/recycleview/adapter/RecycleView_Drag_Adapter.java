package com.allen.androidalldemos.recycleview.adapter;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.recycleview.itemTouchHelper.ItemTouchHelperViewHolder;

import java.util.List;

/**
 * Created by Allen on 2015/12/17.
 */
public class RecycleView_Drag_Adapter extends RecyclerView.Adapter<RecycleView_Drag_Adapter.ViewHolder> {
    private List<String> list;

    public RecycleView_Drag_Adapter(List<String> list, OnStartDragListener onStartDragListener) {
        this.list = list;
        this.onStartDragListener = onStartDragListener;
    }

    public final OnStartDragListener onStartDragListener;

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_list_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    onStartDragListener.onStartDrag(holder);

                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.item_drag_name_TV));
            imageView = (ImageView) itemView.findViewById(R.id.drag_item_IV);

        }

        public void setData(String name) {
            textView.setText(name);
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
