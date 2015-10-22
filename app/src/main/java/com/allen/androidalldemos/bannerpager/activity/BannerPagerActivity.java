package com.allen.androidalldemos.bannerpager.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.bannerpager.view.BannerPager;
import com.allen.androidalldemos.utils.DisplayUtil;
import com.allen.androidalldemos.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2015/10/21.
 */
public class BannerPagerActivity extends AppCompatActivity implements BannerPager.ImageClickListener{
    private BannerPager mBannerPager;
    private LinearLayout mAdTipLayout;

    private String[] imgurls = {
            "http://www.baidu.com/img/bdlogo.png",
            "http://b.hiphotos.baidu.com/news/q%3D100/sign=32bf684cf203918fd1d139ca613c264b/3b87e950352ac65c41a059dffef2b21192138af0.jpg",
            "http://www.baidu.com/img/bdlogo.png"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannerpager);
        mBannerPager = (BannerPager)findViewById(R.id.bannerpager);
        mAdTipLayout = (LinearLayout)findViewById(R.id.bannerpager_ll_adtip);
        initToolbar();
        showAdvertise(imgurls);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("广告滑动");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void showAdvertise(String[] imgurls) {
        if (imgurls == null || imgurls.length == 0) {
            return;
        }
        // 设置mGallery的大小
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBannerPager
                .getLayoutParams();

        params.width = DisplayUtil.getDisplayWidth(this);
        params.height = params.width * 9 / 16;
        mBannerPager.setLayoutParams(params);
        mAdTipLayout.removeAllViews();
        List<String> urls = new ArrayList<String>();
        for (int i = 0; i < imgurls.length && i < 6; i++) {

            ImageView view = new ImageView(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            view.setClickable(false);
            view.setFocusableInTouchMode(false);
            view.setFocusable(false);
            view.setImageResource(R.mipmap.ad_tip_normal);
            view.setPadding(8, 0, 8, 0);
            mAdTipLayout.addView(view);
            urls.add(imgurls[i]);
        }

        if (mAdTipLayout.getChildAt(0) != null) {
            ((ImageView) mAdTipLayout.getChildAt(0))
                    .setImageResource(R.mipmap.ad_tip_selected);
        }
        mBannerPager.setUrls(urls);

        mBannerPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int position) {
                int length = mAdTipLayout.getChildCount();
                for (int i = 0; i < length; i++) {
                    ((ImageView) mAdTipLayout.getChildAt(i))
                            .setImageResource(R.mipmap.ad_tip_normal);
                }
                position = position % length;
                ((ImageView) mAdTipLayout.getChildAt(position))
                        .setImageResource(R.mipmap.ad_tip_selected);
            }

        });
    }

    @Override
    public void OnImageClick(int index) {
        ToastUtils.showShort(this,"索引="+index);
    }
}
