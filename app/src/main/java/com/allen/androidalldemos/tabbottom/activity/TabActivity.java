package com.allen.androidalldemos.tabbottom.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.tabbottom.fragment.Fragment1;
import com.allen.androidalldemos.tabbottom.fragment.Fragment2;
import com.allen.androidalldemos.tabbottom.fragment.Fragment3;
import com.allen.androidalldemos.tabbottom.fragment.Fragment4;
import com.allen.androidalldemos.tabbottom.fragment.Fragment5;
import com.allen.androidalldemos.tabbottom.view.TabBottom;
import com.allen.androidalldemos.utils.ToastUtils;

/**
 * Created by Allen on 2015/12/22.
 */
public class TabActivity extends AppCompatActivity {

    private Context context;
    public TabBottom tabBottom;
    private FragmentManager fm;
    private FragmentTransaction ft;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    Fragment5 fragment5;

    Fragment fragments[] = {fragment1, fragment2, fragment3, fragment4, fragment5};
    Fragment fragment[] = {Fragment1.newInstance("界面一"),
            Fragment2.newInstance("界面二"),
            Fragment3.newInstance("界面三"),
            Fragment4.newInstance("界面四"),
            Fragment5.newInstance("界面五")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbottom);

        context = TabActivity.this;
        tabBottom = (TabBottom) findViewById(R.id.tab_bottom);

        initFragment(0);
        tabBottom.setOnClickListener(new TabBottom.TabOnClickListener() {
            @Override
            public void Tab_1_Listener() {
                showFragment(0);
                ToastUtils.showShort(context, "Tab_1_Listener");
            }

            @Override
            public void Tab_2_Listener() {
                showFragment(1);
                ToastUtils.showShort(context, "Tab_2_Listener");
            }

            @Override
            public void Tab_3_Listener() {
                showFragment(2);
                ToastUtils.showShort(context, "Tab_3_Listener");
            }

            @Override
            public void Tab_4_Listener() {
                showFragment(3);
                ToastUtils.showShort(context, "Tab_4_Listener");
            }

            @Override
            public void Tab_5_Listener() {
                showFragment(4);
                ToastUtils.showShort(context, "Tab_5_Listener");
            }
        });
    }

    public void showFragment(int index) {

        initFragment(index);
    }

    private void initFragment(int index) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        hideFragment();
        if (fragments[index] != null) {
            ft.show(fragments[index]);
        } else {
            fragments[index] = fragment[index];
            ft.add(R.id.main, fragments[index]);
        }
        ft.commit();
    }

    private void hideFragment() {
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null) {
                ft.hide(fragments[i]);
            }
        }
    }
}
