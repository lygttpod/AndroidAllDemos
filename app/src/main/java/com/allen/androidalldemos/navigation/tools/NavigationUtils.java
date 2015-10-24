package com.allen.androidalldemos.navigation.tools;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.utils.ScreenUtils;
import com.allen.androidalldemos.utils.db.greenrobot.gen.ChannelItem;

import java.util.List;

/**
 * Created by allen on 2015/10/23.
 */
public class NavigationUtils {

    //    private ColumnBean columBean;
//    private List<ColumnBean> columnBeans;
    private LinearLayout mNavigation;
    private HorizontalScrollView mNaviga_scroll;
    private int columnSelectIndex = 0;

    private int mScreenWidth = 0;
    private Context context;

    private ViewPager viewPager;

    List<ChannelItem> userChannelList;

    public NavigationUtils(Context context) {
        this.context = context;
    }

    public NavigationUtils(Context context, LinearLayout mNavigation, HorizontalScrollView mNaviga_scroll) {
        this.context = context;
        this.mNavigation = mNavigation;
        this.mNaviga_scroll = mNaviga_scroll;
    }

    public NavigationUtils(ViewPager viewPager, Context context, HorizontalScrollView mNaviga_scroll, LinearLayout mNavigation, List<ChannelItem> userChannelList) {
        this.viewPager = viewPager;
        this.context = context;
        this.mNaviga_scroll = mNaviga_scroll;
        this.mNavigation = mNavigation;
        this.userChannelList = userChannelList;
    }

    public void initNavigation() {
        initColumnData();
        initTabColumn();
    }

    /**
     * 获取Column栏目 数据
     */
    public void initColumnData() {
//        columnBeans = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            columBean = new ColumnBean("菜单" + i, "tabid" + i);
//            columnBeans.add(columBean);
//        }


    }


    /**
     * 初始化Column栏目项
     */
    public void initTabColumn() {
        mNavigation.removeAllViews();
        int count = userChannelList.size();
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 20;
            params.rightMargin = 20;
            params.gravity = Gravity.CENTER;
            TextView columnTextView = new TextView(context);
            columnTextView.setTextAppearance(context, R.style.top_category_scroll_view_item_text);
            columnTextView.setBackgroundResource(R.drawable.navigation_button_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setText(userChannelList.get(i).getName());
            columnTextView.setTextColor(context.getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mNavigation.getChildCount(); i++) {
                        View localView = mNavigation.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            selectTab(i);
                            //Toast.makeText(HomeActivity.this, "i=" + i, Toast.LENGTH_SHORT).show();
                            viewPager.setCurrentItem(i);

                        }
                    }
                    Toast.makeText(context, userChannelList.get(v.getId()).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            mNavigation.addView(columnTextView, i, params);
        }
    }

    /**
     * 选择的Column里面的Tab
     */
    public void selectTab(int tab_postion) {
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mNavigation.getChildCount(); i++) {
            View checkView = mNavigation.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mNaviga_scroll.smoothScrollTo(i2, 0);
        }
        //判断是否选中
        for (int j = 0; j < mNavigation.getChildCount(); j++) {
            View checkView = mNavigation.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }
}
