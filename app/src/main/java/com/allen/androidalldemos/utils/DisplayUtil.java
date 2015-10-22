package com.allen.androidalldemos.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * UI像素计算工具�?
 * 
 * @author tangqiang
 * 
 */

public class DisplayUtil {

	/** 获取屏幕宽度 */
	public static int getDisplayWidth(Context context) {
		if (context != null) {
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			int w_screen = dm.widthPixels;
			// int h_screen = dm.heightPixels;
			return w_screen;
		}
		return 720;
	}

	/** 获取屏幕高度 */
	public static int getDisplayHight(Context context) {
		if (context != null) {
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			// int w_screen = dm.widthPixels;
			int h_screen = dm.heightPixels;
			return h_screen;
		}
		return 1280;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
