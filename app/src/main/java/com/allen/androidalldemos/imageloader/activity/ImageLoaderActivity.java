package com.allen.androidalldemos.imageloader.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.adapter.ListViewAdapter;
import com.allen.androidalldemos.imageloader.adapter.ListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2015/10/21.
 */
public class ImageLoaderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ListViewAdapter listViewAdapter;
    private List<String> list;
    private List<String> listurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader);
        initListView();
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("异步网络图片请求");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initlistDate() {
        listurl = new ArrayList<>();
        list = new ArrayList<>();
        listurl.add("http://img4.cache.netease.com/3g/2015/10/21/20151021075539ba3a3.png");
        listurl.add("http://img3.cache.netease.com/3g/2015/10/21/2015102107553775c2f.jpg");
        listurl.add("http://img6.cache.netease.com/3g/2015/10/21/20151021081938555df.jpg");
        listurl.add("http://img6.cache.netease.com/3g/2015/10/21/201510210836463e2ea.jpg");
        listurl.add("http://img2.cache.netease.com/3g/2015/10/21/20151021023108e6296.jpg");
        listurl.add("http://img1.cache.netease.com/3g/2015/10/21/20151021084655af100.jpg");
        listurl.add("http://img6.cache.netease.com/3g/2015/10/21/2015102108075835fdb.jpg");
        listurl.add("http://img6.cache.netease.com/3g/2015/10/21/201510210836463e2ea.jpg");
        listurl.add("http://img6.cache.netease.com/3g/2015/10/21/20151021080756377e5.jpg");
        listurl.add("http://img6.cache.netease.com/3g/2015/10/21/201510210815251350e.jpg");

    }

    private void initListView() {
        initlistDate();
        mRecyclerView = (RecyclerView) findViewById(R.id.listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new ListAdapter(this, listurl));

    }

}
