package com.allen.androidalldemos.material_design.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.allen.androidalldemos.R;

import java.util.List;

/**
 * Created by Allen on 2015/12/14.
 */
public class DesignHome_Fragment_Adapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;
    private Context mContext;
    private int[] imageResId = {
            R.mipmap.ic_favorite_white_24dp,
            R.mipmap.ic_local_post_office_white_24dp,
            R.mipmap.ic_person_white_24dp
    };

    public DesignHome_Fragment_Adapter(FragmentManager fm) {
        super(fm);
    }

    public DesignHome_Fragment_Adapter(FragmentManager fm, List<Fragment> mFragments, List<String> mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    public DesignHome_Fragment_Adapter(FragmentManager fm, List<Fragment> mFragments, List<String> mTitles, Context mContext) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
        this.mContext = mContext;
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
        //return mTitles.get(position);//不加图标
        Drawable image = mContext.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" "+"\n"+mTitles.get(position));
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
