package com.allen.androidalldemos.material_design.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Allen on 2015/12/14.
 */
public class DesignHome_Fragment_Adapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;


    public DesignHome_Fragment_Adapter(FragmentManager fm) {
        super(fm);
    }

    public DesignHome_Fragment_Adapter(FragmentManager fm, List<Fragment> mFragments, List<String> mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
