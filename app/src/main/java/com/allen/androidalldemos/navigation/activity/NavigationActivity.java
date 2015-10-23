package com.allen.androidalldemos.navigation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allen.androidalldemos.BaseActivity;
import com.allen.androidalldemos.R;
import com.allen.androidalldemos.applaction.MyApplaction;
import com.allen.androidalldemos.navigation.adapter.MyFragmentAdapter;
import com.allen.androidalldemos.navigation.fragment.TestFragment;
import com.allen.androidalldemos.navigation.utils.NavigationUtils;
import com.allen.androidalldemos.utils.GreenDaoUtils;
import com.allen.androidalldemos.utils.LogUtil;
import com.allen.androidalldemos.utils.db.greenrobot.gen.ChannelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2015/10/23.
 */
public class NavigationActivity extends BaseActivity {

    private LinearLayout mNavigation;
    private HorizontalScrollView mNaviga_scroll;
    private ImageView addIV;
    private Context context;
    private NavigationUtils navigationUtils;

    private ViewPager viewPager;
    private MyFragmentAdapter myFragmentAdapter;
    private List<Fragment> fragmentList = null;

    private TestFragment fragment;
    List<ChannelItem> userChannelList;
    private GreenDaoUtils greenDaoUtils;

    private MyApplaction myApplaction;
    /**
     * 请求CODE
     */
    public final static int CHANNELREQUEST = 1;
    /**
     * 调整返回的RESULTCODE
     */
    public final static int CHANNELRESULT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        myApplaction = (MyApplaction) getApplication();
        context = this;
        userChannelList = new ArrayList<>();
        greenDaoUtils = myApplaction.getGreenDaoUtils();
        initToolbar();
        addIV = (ImageView) findViewById(R.id.add_naviga_itme_bt);

        addIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ChannelActivity.class);
                startActivityForResult(intent, CHANNELREQUEST);
            }
        });
        mNaviga_scroll = (HorizontalScrollView) findViewById(R.id.naviga_scroll);
        mNavigation = (LinearLayout) findViewById(R.id.naviga_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        initData();

        initViewPager();

    }

    private void initData() {
        userChannelList = greenDaoUtils.getChannelItems(1);
        navigationUtils = new NavigationUtils(viewPager, context, mNaviga_scroll, mNavigation, userChannelList);
        navigationUtils.initNavigation();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("仿新闻导航菜单");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initFragmentData() {
        fragmentList = new ArrayList<>();
        fragmentList.clear();
        for (int i = 0; i < userChannelList.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("title", userChannelList.get(i).getName());
            fragment = new TestFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
    }

    private void initViewPager() {
        initFragmentData();
        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                navigationUtils.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHANNELREQUEST:
                if (resultCode == CHANNELRESULT) {
                    initData();
                    navigationUtils.initNavigation();
                    initViewPager();
                    //Toast.makeText(HomeActivity.this, "onActivityResult", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
