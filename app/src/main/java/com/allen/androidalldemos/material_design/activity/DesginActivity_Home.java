package com.allen.androidalldemos.material_design.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.material_design.adapter.DesignHome_Fragment_Adapter;
import com.allen.androidalldemos.material_design.fragment.DesignFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2015/12/14.
 */
public class DesginActivity_Home extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DesignHome_Fragment_Adapter adapter;

    private List<String> tabTitle;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_home);
        initTabTitle();
        initFragment();
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.design_home_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Allen");
        mToolbar.setNavigationIcon(R.mipmap.ic_menu);

        mTabLayout = (TabLayout) findViewById(R.id.design_home_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.design_home_viewpager);
        adapter = new DesignHome_Fragment_Adapter(getSupportFragmentManager(), fragments, tabTitle);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void initTabTitle() {
        tabTitle = new ArrayList<>();
        tabTitle.add("Tab1");
        tabTitle.add("Tab2");
        tabTitle.add("Tab3");
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        for (int i = 0; i < tabTitle.size(); i++) {
            fragments.add(new DesignFragment().newInstance());
        }
    }

}
