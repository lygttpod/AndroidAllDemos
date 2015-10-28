package com.allen.androidalldemos.gesturelockpsd.gesture.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.gesturelockpsd.activity.LoginActivity;
import com.allen.androidalldemos.gesturelockpsd.gesture.widget.GestureContentView;
import com.allen.androidalldemos.gesturelockpsd.gesture.widget.GestureDrawline;
import com.allen.androidalldemos.utils.SPUtils;
import com.allen.androidalldemos.utils.ToastUtils;


/**
 * 手势绘制/校验界面
 */
public class GestureVerifyActivity extends Activity implements
        View.OnClickListener {
    /**
     * 手机号码
     */
    public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
    /**
     * 意图
     */
    public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
    private RelativeLayout mTopLayout;
    private TextView mTextTitle;
    private TextView mTextCancel;
    private ImageView mImgUserLogo;
    private TextView mTextPhoneNumber;
    private TextView mTextTip;
    private FrameLayout mGestureContainer;
    private GestureContentView mGestureContentView;
    private TextView mTextForget;
    // private TextView mTextOther;
    private String mParamPhoneNumber;
    private long mExitTime = 0;
    private int mParamIntentCode;

    private String getGpsd = "";

    private String nameString, psdString;
    private boolean isbackground = false;
    private int psd_count = 5;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_verify);
        nameString = "Allen";
        context = this;

        getGpsd = SPUtils.get(context, "gesturePsd", "")
                .toString();
        ObtainExtraData();
        setUpViews();
        setUpListeners();
    }

    private void ObtainExtraData() {
        mParamPhoneNumber = getIntent().getStringExtra(PARAM_PHONE_NUMBER);
        mParamIntentCode = getIntent().getIntExtra(PARAM_INTENT_CODE, 0);
    }

    private void setUpViews() {
        mTopLayout = (RelativeLayout) findViewById(R.id.top_layout);
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mTextCancel = (TextView) findViewById(R.id.text_cancel);
        mImgUserLogo = (ImageView) findViewById(R.id.user_logo);
        mTextPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
        mTextTip = (TextView) findViewById(R.id.text_tip);
        mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
        mTextForget = (TextView) findViewById(R.id.text_forget_gesture);
        // mTextOther = (TextView) findViewById(R.id.text_other_account);

        mTextPhoneNumber.setText(nameString);
        mImgUserLogo.setImageResource(R.mipmap.user_logo);
        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, true, getGpsd,
                new GestureDrawline.GestureCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                    }

                    @Override
                    public void checkedSuccess() {
                        mGestureContentView.clearDrawlineState(0L);

                        ToastUtils.showLong(context, "验证成功，根据需求执行需要的操作即可！");
                        finish();

                        if (isbackground) {


                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void checkedFail() {
                        psd_count--;
                        if (psd_count <= 0) {
                            SPUtils.remove(context,
                                    "gesturePsd");
                            Intent intent = new Intent(
                                    GestureVerifyActivity.this,
                                    LoginActivity.class);
                            // 打开新的Activity
                            startActivity(intent);
                            finish();
                        }

                        mGestureContentView.clearDrawlineState(1300L);
                        mTextTip.setVisibility(View.VISIBLE);
                        mTextTip.setText(Html
                                .fromHtml("<font color='#c70c1e'>密码错误,还可再试</font>"
                                        + "<font color='#c70c1e'>"
                                        + psd_count
                                        + "</font>"
                                        + "<font color='#c70c1e'>次</font>"));
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils
                                .loadAnimation(GestureVerifyActivity.this,
                                        R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                    }
                });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
    }

    private void setUpListeners() {
        mTextCancel.setOnClickListener(this);
        mTextForget.setOnClickListener(this);
        // mTextOther.setOnClickListener(this);
    }

    private String getProtectedMobile(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0, 3));
        builder.append("****");
        builder.append(phoneNumber.subSequence(7, 11));
        return builder.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_cancel:
                this.finish();
                break;
            case R.id.text_forget_gesture:

                SPUtils.clear(context);
                Intent intent = new Intent(GestureVerifyActivity.this,
                        LoginActivity.class);
                // 打开新的Activity
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:

                return false;

        }
        return true;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//		ActivityCollector.removeActivity(this);
    }

}
