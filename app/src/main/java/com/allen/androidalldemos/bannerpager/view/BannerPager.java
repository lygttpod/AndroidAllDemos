package com.allen.androidalldemos.bannerpager.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.allen.androidalldemos.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class BannerPager extends ViewPager implements ImageLoadingListener {

    private List<String> mUrls;
    private MyAdapter mAdapter;
    private int mCount;
    private final int AUTO_SCROLL = 1;
    private final int DELAY_TIME = 5000;// 滚动间隔时间
    private boolean mOnTouch;// 是否触摸
    private Map<String, Bitmap> mBitMaps = new HashMap<String, Bitmap>();
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;
    private ImageClickListener mClickListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mCount <= 1) {
                mHandler.removeMessages(AUTO_SCROLL);
                return;
            }

            if (mUrls == null) {
                mHandler.removeMessages(AUTO_SCROLL);
                return;
            }

            if (msg.what == AUTO_SCROLL && !mOnTouch) {
                if (BannerPager.this.isShown()) {
                    setCurrentItem(getCurrentItem() + 1);
                    mHandler.sendEmptyMessageDelayed(AUTO_SCROLL, DELAY_TIME);
                } else {
                    mHandler.removeMessages(AUTO_SCROLL);
                }
            }
        }
    };

    public BannerPager(Context context) {
        super(context);
        init();
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.default_banner)
                .showImageOnFail(R.mipmap.default_banner).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCount <= 1) {
            return true;
        }
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mOnTouch = true;
                mHandler.removeMessages(AUTO_SCROLL);
                break;

            case MotionEvent.ACTION_UP:
                mOnTouch = false;
                mHandler.removeMessages(AUTO_SCROLL);
                mHandler.sendEmptyMessageDelayed(AUTO_SCROLL, DELAY_TIME);
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * �?启自动滚�?
     */
    public void startScroll() {
        if (1 < mCount) {
            mHandler.removeMessages(AUTO_SCROLL);
            mHandler.sendEmptyMessageDelayed(AUTO_SCROLL, DELAY_TIME);
        }
    }

    /**
     * 停止自动滚动
     */
    public void stopScroll() {
        mHandler.removeMessages(AUTO_SCROLL);
    }

    /**
     * 设置图片地址
     */
    public void setUrls(List<String> urls) {
        if (this.mUrls != null) {
            this.mUrls.clear();
            this.mCount = 0;
        }
        this.mUrls = urls;
        this.mCount = mUrls.size();
        if (this.mAdapter != null) {
            this.mAdapter = null;
        }
        this.mAdapter = new MyAdapter();
        setAdapter(mAdapter);
        loadBitmap();
        if (1 < mCount) {
            setCurrentItem(mCount * 100);
        } else {
            setCurrentItem(0);
        }
        startScroll();
    }

    /**
     * 设置图片点击事件
     */
    public void setClickListener(ImageClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    /**
     * 释放图片资源
     */
    public void relaseResoure() {
        mHandler.removeMessages(AUTO_SCROLL);
        if (mBitMaps != null) {
            mBitMaps.clear();
            mBitMaps = null;
        }

        if (mUrls != null) {
            mUrls.clear();
            mUrls = null;
            mAdapter = null;
            mHandler = null;
        }
    }

    /**
     * 读取图片资源
     */
    private void loadBitmap() {
        if (mUrls != null && 0 < mUrls.size()) {
            if (mBitMaps != null) {
                mBitMaps.clear();
            }

            for (String photourl : mUrls) {
                ImageLoader.getInstance().loadImage(photourl, this);
            }
        }
    }

    @Override
    public void onLoadingCancelled(String arg0, View arg1) {

    }

    @Override
    public void onLoadingComplete(String url, View arg1, Bitmap bitmap) {
        if (mBitMaps != null) {
            mBitMaps.put(url, bitmap);
        }
    }

    @Override
    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

    }

    @Override
    public void onLoadingStarted(String arg0, View arg1) {

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 1 < mCount ? Integer.MAX_VALUE : 1;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((BannerPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ScaleType.FIT_XY);
            String imageurl = mUrls.get(position % mCount);
            if (mBitMaps != null && mBitMaps.get(imageurl) != null) {
                imageView.setImageBitmap(mBitMaps.get(imageurl));
            } else {
                if (mUrls != null) {
                    mImageLoader.displayImage(imageurl, imageView, mOptions);
                }
            }
            ((BannerPager) container).addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.OnImageClick(position % mCount);
                    }
                }
            });
            return imageView;
        }
    }

    public interface ImageClickListener {
        public void OnImageClick(int index);
    }

}
