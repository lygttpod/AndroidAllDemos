package com.allen.androidalldemos.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by allen on 2015/10/20.
 */
public class ToastUtils {
    static Toast mToast;

    /**
     * 短时间显示
     *
     * @param context
     * @param msg
     */
    public static void showShort(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 长时间显示
     * @param context
     * @param msg
     */
    public static void showLong(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
}
