package com.allen.androidalldemos.weather.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.allen.androidalldemos.R;


public class WeatherUtil {
	// 晴|多云|阴|阵雨|雷阵雨|雷阵雨伴有冰雹|雨夹雪|小雨|中雨|大雨|暴雨|大暴雨|特大暴雨|阵雪|小雪|中雪|大雪|暴雪|雾|冻雨|沙尘暴|小雨转中雨|
	// 中雨转大雨|大雨转暴雨|暴雨转大暴雨|大暴雨转特大暴雨|小雪转中雪|中雪转大雪|大雪转暴雪|浮尘|扬沙|强沙尘暴|霾
	public static Bitmap getWeatherImg(Context context, String weather) {

		Resources res = context.getResources();
		Bitmap bmp = null;
		if (weather.equals("晴")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d0);
		} else if (weather.equals("多云")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d1);
		} else if (weather.equals("阴")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d2);
		} else if (weather.equals("阵雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d3);
		} else if (weather.equals("雷阵雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d4);
		} else if (weather.equals("雷阵雨伴有冰雹")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d5);
		} else if (weather.equals("雨夹雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d6);
		} else if (weather.equals("小雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d7);
		} else if (weather.equals("中雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d8);
		} else if (weather.equals("大雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d9);
		} else if (weather.equals("暴雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d10);
		} else if (weather.equals("大暴雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d11);
		} else if (weather.equals("特大暴雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d12);
		} else if (weather.equals("阵雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d13);
		} else if (weather.equals("小雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d14);
		} else if (weather.equals("中雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d15);
		} else if (weather.equals("大雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d16);
		} else if (weather.equals("暴雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d17);
		} else if (weather.equals("雾")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d18);
		} else if (weather.equals("冻雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d19);
		} else if (weather.equals("沙尘暴")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d20);
		} else if (weather.equals("小雨转中雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d21);
		} else if (weather.equals("中雨转大雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d22);
		} else if (weather.equals("大雨转暴雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d23);
		} else if (weather.equals("暴雨转大暴雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d24);
		} else if (weather.equals("大暴雨转特大暴雨")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d25);
		} else if (weather.equals("小雪转中雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d26);
		} else if (weather.equals("中雪转大雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d27);
		} else if (weather.equals("大雪转暴雪")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d28);
		} else if (weather.equals("浮尘")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d29);
		} else if (weather.equals("扬沙")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d30);
		} else if (weather.equals("强沙尘暴")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d31);
		} else if (weather.equals("霾")) {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d53);
		} else {
			bmp = BitmapFactory.decodeResource(res, R.mipmap.d1);
		}
		return bmp;

	}

}
