package com.allen.androidalldemos.fixed.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/*
 * 
 * 一个视图容器控件
 * 阻止 拦截 ontouch事件传递给其子控件
 * */
public class ObserverHScrollViewIntercept extends LinearLayout {

	public ObserverHScrollViewIntercept(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ObserverHScrollViewIntercept(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i("pdwy","ScrollContainer onInterceptTouchEvent");
		return true;
	}
}
