package com.allen.androidalldemos.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.androidalldemos.R;

/**
 * Created by Allen on 2016/4/8.
 */
public class TopBar extends RelativeLayout {

    private String title;
    private float titleTextSize;
    private int titleTextColor;

    private String leftText;
    private int leftTextColor;
    private Drawable leftBackground;

    private String rightText;
    private int rightTextColor;
    private Drawable rightBackground;

    private TextView mTitle;
    private Button mLeftBtn;
    private Button mRightBtn;

    private LayoutParams leftParams,titleParams,rightParams;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取arrt配置属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TopBar);

        title = typedArray.getString(R.styleable.TopBar_titleText);
        titleTextColor = typedArray.getColor(R.styleable.TopBar_titleTextColors,0);
        titleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize,0);

        leftText = typedArray.getString(R.styleable.TopBar_leftText);
        leftTextColor = typedArray.getColor(R.styleable.TopBar_leftTextColor,0);
        leftBackground = typedArray.getDrawable(R.styleable.TopBar_titleTextSize);

        rightText = typedArray.getString(R.styleable.TopBar_rightText);
        rightTextColor = typedArray.getColor(R.styleable.TopBar_rightTextColor,0);
        rightBackground = typedArray.getDrawable(R.styleable.TopBar_rightBackground);

        typedArray.recycle();

        //创建需要的控件
        mTitle = new TextView(context);
        mLeftBtn = new Button(context);
        mRightBtn = new Button(context);

        //设置获取的属性值
        mTitle.setText(title);
        mTitle.setTextSize(titleTextSize);
        mTitle.setTextColor(titleTextColor);
        mTitle.setGravity(Gravity.CENTER);

        mLeftBtn.setText(leftText);
        mLeftBtn.setTextColor(leftTextColor);
        mLeftBtn.setBackground(leftBackground);

        mRightBtn.setText(rightText);
        mRightBtn.setTextColor(rightTextColor);
        mRightBtn.setBackground(rightBackground);

        setBackgroundColor(0xFFF59563);

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(mLeftBtn,leftParams);

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(mRightBtn,rightParams);

        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(mTitle,titleParams);


    }
}
