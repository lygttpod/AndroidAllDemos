package com.allen.androidalldemos.recycleview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.recycleview.adapter.ListView_Adapter;
import com.allen.androidalldemos.recycleview.adapter.RecycleView_Drag_Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2015/12/17.
 */
public class DragRecycleViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycleview);
        recyclerView = ((RecyclerView) findViewById(R.id.recycler_view_drag));
        getData();
    }
    private void getData(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("内容"+i);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecycleView_Drag_Adapter adapter = new RecycleView_Drag_Adapter(list);
        ListView_Adapter adapter1 = new ListView_Adapter(list,this);
        recyclerView.setAdapter(adapter);
    }
}
