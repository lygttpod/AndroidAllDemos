package com.allen.androidalldemos.tabbottom.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.tabbottom.view.TabBottom;
import com.allen.androidalldemos.utils.ToastUtils;

/**
 * Created by Allen on 2015/12/22.
 */
public class TabActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbottom);
        TabBottom tabBottom = (TabBottom) findViewById(R.id.tab_bottom);
        tabBottom.setOnClickListener(new TabBottom.TabOnClickListener() {
            @Override
            public void Tab_1_Listener() {
                ToastUtils.showShort(TabActivity.this, "Tab_1_Listener");
            }

            @Override
            public void Tab_2_Listener() {
                ToastUtils.showShort(TabActivity.this, "Tab_2_Listener");
            }

            @Override
            public void Tab_3_Listener() {
                ToastUtils.showShort(TabActivity.this, "Tab_3_Listener");
            }

            @Override
            public void Tab_4_Listener() {
                ToastUtils.showShort(TabActivity.this, "Tab_4_Listener");
            }

            @Override
            public void Tab_5_Listener() {
                ToastUtils.showShort(TabActivity.this, "Tab_5_Listener");
            }
        });

    }
}
