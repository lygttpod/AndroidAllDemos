package com.allen.androidalldemos.recycleview.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Canvas;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.recycleview.adapter.RecycleView_Drag_Adapter;
import com.allen.androidalldemos.utils.LogUtil;
import com.allen.androidalldemos.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Allen on 2015/12/17.
 */
public class DragRecycleViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecycleView_Drag_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycleview);
        recyclerView = ((RecyclerView) findViewById(R.id.recycler_view_drag));
        getData();

    }
    private void getData(){
        final List<String> list = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            list.add(""+(char)i);
        }
        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(list, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                //返回true表示执行拖动
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction ==200){
                    ToastUtils.showShort(DragRecycleViewActivity.this,direction+"");
                }
                LogUtil.e("allen",direction+"");
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }
        };

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecycleView_Drag_Adapter.OnStartDragListener onStartDragListener = new RecycleView_Drag_Adapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            }
        };
        adapter = new RecycleView_Drag_Adapter(list,onStartDragListener);
        //ListView_Adapter adapter1 = new ListView_Adapter(list,this);
        recyclerView.setAdapter(adapter);



    }
}
