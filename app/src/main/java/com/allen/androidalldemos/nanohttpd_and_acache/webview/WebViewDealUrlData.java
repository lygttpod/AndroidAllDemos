package com.allen.androidalldemos.nanohttpd_and_acache.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by allen on 2015/8/31.
 */
public class WebViewDealUrlData {
    private static Context context;


    public WebViewDealUrlData(Context context) {
        setContext(context);

    }

    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static void onWebClientUrlLis(String strUrl) {
        System.out.println("onWebClientUrlLis:" + strUrl);
        if (strUrl == null || strUrl.length() <= 0) {
            return;
        }

        if (IsActionCall(strUrl)) {
            return;
        }

    }


    public static boolean IsActionCall(String strUrl) {


        String strUrlStart = "http://tel:";
        String strUrlStartTel = "tel:";
        if (strUrl.startsWith(strUrlStartTel)) {
            strUrl = "http://" + strUrl;
        }

        Pattern pattern = Pattern.compile("\\d{2,4}-\\d{7,8}");
        Matcher matcher = pattern.matcher(strUrl);
        boolean bPhoneNum = matcher.matches();
        String strTelPhone = "";
        if (bPhoneNum) {
            strTelPhone = strUrl;
        } else if (strUrl.startsWith(strUrlStart)) {
            strTelPhone = strUrl.substring(strUrlStart.length(), strUrl.length() - (strUrl.endsWith("/") ? 1 : 0));
        }

        final String strMobile = strTelPhone;

        if (strMobile != null && strMobile.length() > 0) {
            new AlertDialog.Builder(getContext())
                    .setTitle("温馨提示")
                    .setMessage("确定拨打电话:" + strMobile + "?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + strMobile));
                            getContext().startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();

            return true;
        }

        return false;
    }
}
