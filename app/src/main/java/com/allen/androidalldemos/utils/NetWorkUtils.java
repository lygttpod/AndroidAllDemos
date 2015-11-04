package com.allen.androidalldemos.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 判断是否有网络
 * Created by allen on 2015/8/26.
 */
public class NetWorkUtils {
    public static boolean isConnnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

            if (null != networkInfo) {
                for (NetworkInfo info : networkInfo) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        Log.e("NetWorkUtils", "the net is ok");
                        return true;
                    }
                }
            }
        }
        //Toast.makeText(context, "无网络链接", Toast.LENGTH_SHORT).show();
        return false;
    }
}
