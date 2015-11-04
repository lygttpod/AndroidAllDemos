package com.allen.androidalldemos.nanohttpd_and_acache.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.nanohttpd_and_acache.nanohttpd_utils.SimpleServer;
import com.allen.androidalldemos.nanohttpd_and_acache.webview.WebViewChromeListener;
import com.allen.androidalldemos.nanohttpd_and_acache.webview.WebViewClientListener;

import java.io.IOException;

public class NanoHttpdActivity extends Activity {
	private SimpleServer server;
	private WebView webView;
	private String urlString = "http://localhost:8088/login.html";
    private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nanohttpd);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
		initServer();
		initWebView();

	}

	/**
	 * 初始化本地服务器
	 */
	private void initServer() {
		server = new SimpleServer(this);
		server.asset_mgr = this.getAssets();
		try {
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化webview
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		webView = (WebView) findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebViewChromeListener(this, webView));
		webView.setWebViewClient(new WebViewClientListener(this,progressBar));

		webView.loadUrl(urlString);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		server.stop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){
			webView.goBack();   //goBack()表示返回webView的上一页面
			return true;
		}
		//继续执行父类的其他点击事件
		return super.onKeyDown(keyCode, event);

	}
}
