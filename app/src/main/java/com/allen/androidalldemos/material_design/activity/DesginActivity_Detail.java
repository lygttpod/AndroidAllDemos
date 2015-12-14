package com.allen.androidalldemos.material_design.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.utils.ToastUtils;

/**
 * Created by Allen on 2015/12/10.
 */
public class DesginActivity_Detail extends AppCompatActivity {
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    Toolbar mToolbar;
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_datail);
        initToolbar();

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_Layout);
        mCollapsingToolbarLayout.setTitle("我的课程");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.actionBtn);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "课程学习完毕", Snackbar.LENGTH_SHORT).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort(DesginActivity_Detail.this, "这是Toast");
                    }
                }).show();
            }
        });

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
