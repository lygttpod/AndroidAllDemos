package com.allen.androidalldemos.tabbottom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allen.androidalldemos.R;

public class BottomMenu extends LinearLayout implements OnClickListener {

    private OnClickListener listener;
    private ImageView home, customer, job, news, more;

    public static final int FLAG_HOME = 1;

    public static final int FLAG_CUSTOMER = 2;

    public static final int FLAG_JOB = 3;

    public static final int FLAG_NEWS = 4;

    public static final int FLAG_MORE = 5;

    public static int sFlag = 1;

    public BottomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        if (!isInEditMode()) {
            setBackgroundResource(R.drawable.blue_button_background);
            initImageView(context);
            return;
        }

        initFlagState();
    }

    /**
     * 导航菜单点击事件对外接口
     *
     * @author Allen
     */
    public interface OnClickListener {
        public void homeListener();

        public void customerListener();

        public void jobListener();

        public void newsListener();

        public void moreListener();

    }

    public OnClickListener getListener() {
        return listener;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * 初始化导航菜单视图
     *
     * @param context
     */
    private void initImageView(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 1.0f);
        home = new ImageView(context);
        customer = new ImageView(context);
        job = new ImageView(context);
        news = new ImageView(context);
        more = new ImageView(context);

        home.setImageResource(R.mipmap.d0);
        customer.setImageResource(R.mipmap.d0);
        job.setImageResource(R.mipmap.d0);
        news.setImageResource(R.mipmap.d0);
        more.setImageResource(R.mipmap.d0);

        home.setLayoutParams(params);
        customer.setLayoutParams(params);
        job.setLayoutParams(params);
        news.setLayoutParams(params);
        more.setLayoutParams(params);

        home.setOnClickListener(this);
        customer.setOnClickListener(this);
        job.setOnClickListener(this);
        news.setOnClickListener(this);
        more.setOnClickListener(this);

        addView(home);
        addView(customer);
        addView(job);
        addView(news);
        addView(more);

    }

    /**
     * 初始化状态
     */
    public void initFlagState() {
        restoreImageState();
        if (sFlag == FLAG_HOME) {
            setHomeState();
        } else if (sFlag == FLAG_CUSTOMER) {
            setCustomerState();
        } else if (sFlag == FLAG_JOB) {
            setJobState();
        } else if (sFlag == FLAG_NEWS) {
            setNewsState();
        } else if (sFlag == FLAG_MORE) {
            setMoreState();
        }
    }

    /**
     * 恢复所有按钮图片初始化未点击状态
     */
    public void restoreImageState() {
        home.setImageResource(R.mipmap.d0);
        customer.setImageResource(R.mipmap.d0);
        job.setImageResource(R.mipmap.d0);
        news.setImageResource(R.mipmap.d0);
        more.setImageResource(R.mipmap.d0);

    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        restoreImageState();
        if (view == home) {
            goHome();
        } else if (view == customer) {
            goCustomer();
        } else if (view == job) {
            goJob();
        } else if (view == news) {
            goNews();
        } else if (view == more) {
            goMore();
        }
    }

    public void goHome() {
        setHomeState();

    }

    public void goCustomer() {
        setCustomerState();

    }

    public void goJob() {
        setJobState();

    }

    public void goNews() {
        setNewsState();

    }

    public void goMore() {
        setMoreState();

    }

    public void setHomeState() {

        home.setImageResource(R.mipmap.d0);
        if (listener != null && sFlag != FLAG_HOME) {
            listener.homeListener();
        }
        sFlag = FLAG_HOME;
    }

    public void setCustomerState() {

        customer.setImageResource(R.mipmap.d0);
        if (listener != null && sFlag != FLAG_CUSTOMER) {
            listener.customerListener();
        }
        sFlag = FLAG_CUSTOMER;
    }

    public void setJobState() {

        job.setImageResource(R.mipmap.d0);

        if (listener != null && sFlag != FLAG_JOB) {
            listener.jobListener();
        }
        sFlag = FLAG_JOB;
    }

    public void setNewsState() {

        news.setImageResource(R.mipmap.d0);
        if (listener != null && sFlag != FLAG_NEWS) {
            listener.newsListener();
        }
        sFlag = FLAG_NEWS;
    }

    public void setMoreState() {

        more.setImageResource(R.mipmap.d0);
        if (listener != null && sFlag != FLAG_MORE) {
            listener.moreListener();
        }
        sFlag = FLAG_MORE;
    }

}
