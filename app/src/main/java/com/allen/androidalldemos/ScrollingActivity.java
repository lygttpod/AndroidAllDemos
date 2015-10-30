package com.allen.androidalldemos;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by hardy on 2015/10/29.
 */

public class ScrollingActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        initToolbar();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(getTitle());

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("测试");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
