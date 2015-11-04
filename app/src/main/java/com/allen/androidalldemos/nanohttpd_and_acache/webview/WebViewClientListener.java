package com.allen.androidalldemos.nanohttpd_and_acache.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by allen on 2015/8/31.
 */
public class WebViewClientListener extends WebViewClient {

    private WebViewDealUrlData webViewDealUrlData;
    private Context context;
    private ProgressBar progressBar;

    public WebViewClientListener(Context context, ProgressBar progressBar) {
        super();
        this.context = context;
        this.progressBar = progressBar;
        webViewDealUrlData = new WebViewDealUrlData(context);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (view != null) {
            if (url.startsWith("http://") && !url.startsWith("http://action") && !url.startsWith("http://tel:") && !url.startsWith("http://stock:")) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            WebViewDealUrlData.onWebClientUrlLis(url);
        }
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (progressBar.getVisibility() == ProgressBar.GONE) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        if (WebViewDealUrlData.IsActionCall(url)) {
            return;
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (progressBar.getVisibility() == ProgressBar.VISIBLE) {
            progressBar.setVisibility(ProgressBar.GONE);
        }
        if (view != null) {
            view.scrollTo(0, 0);
        }
        super.onPageFinished(view, url);

    }


}
