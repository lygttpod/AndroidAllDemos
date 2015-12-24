package com.allen.androidalldemos.tabbottom.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allen.androidalldemos.R;

/**
 * Created by Allen on 2015/12/22.
 */
public class TabBottom extends LinearLayout implements View.OnClickListener {

    private static int mSelectedTabIndex = 0;
    private static int mTabCount = 5;//设置tab的数量

    private LinearLayout mLinearLayout;

    private int img_drawable_Id[] = {
                    R.drawable.home_btn_selector,
                    R.drawable.faxian_btn_selector,
                    R.drawable.cart_btn_selector,
                    R.drawable.category_btn_selector,
                    R.drawable.mine_btn_selector};

    public TabBottom(Context context) {
        super(context);
        initImageView(context);
    }

    public TabBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageView(context);
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            int selectId = (int) v.getTag();
            switch (selectId) {
                case 0:
                    setCurrentItem(0);
                    mOnClickListener.Tab_1_Listener();
                    break;
                case 1:
                    setCurrentItem(1);
                    mOnClickListener.Tab_2_Listener();
                    break;
                case 2:
                    setCurrentItem(2);
                    mOnClickListener.Tab_3_Listener();
                    break;
                case 3:
                    setCurrentItem(3);
                    mOnClickListener.Tab_4_Listener();
                    break;
                case 4:
                    setCurrentItem(4);
                    mOnClickListener.Tab_5_Listener();
                    break;
            }
        }

    }


    private TabOnClickListener mOnClickListener;

    //接口回调方法
    public void setOnClickListener(TabOnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }


    /**
     * 提供对外接口
     */
    public interface TabOnClickListener {

        public void Tab_1_Listener();

        public void Tab_2_Listener();

        public void Tab_3_Listener();

        public void Tab_4_Listener();

        public void Tab_5_Listener();

    }

    /**
     * 初始化导航菜单视图
     *
     * @param context
     */
    private void initImageView(Context context) {
        setOrientation(HORIZONTAL);
        //添加一个水平布局的Linerlauyout
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(HORIZONTAL);
        mLinearLayout.setPadding(5, 10, 5, 10);
        mLinearLayout.setBackgroundColor(Color.GRAY);
        mLinearLayout.setGravity(Gravity.CENTER);
        addView(mLinearLayout, layoutParams);
        addTab(mTabCount);

        setCurrentItem(mSelectedTabIndex);//默认选择第一项
    }

    /**
     * 添加子栏目
     *
     * @param count
     */
    private void addTab(int count) {
        //向横向布局LinerarLayout添加ImagerView
        LayoutParams img_lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(img_drawable_Id[i]);
            imageView.setTag(i);
            imageView.setOnClickListener(this);
            mLinearLayout.addView(imageView, img_lp);
        }
    }

    /**
     * 设置当前被选中Tab的状态，为了selector切换状态准备
     *
     * @param item
     */
    public void setCurrentItem(int item) {
        final int tabCount = mLinearLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mLinearLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
        }
    }
}
