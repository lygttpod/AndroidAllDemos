package com.allen.androidalldemos.asynchttp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.utils.ToastUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by allen on 2015/10/20.
 */
public class AsyncHttpActivity extends Activity {
    private AsyncHttpClient asyncHttpClient;
    private Button sendHttpBtn;
    private TextView showHttpResponseTv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynchttp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sendHttpBtn = (Button) findViewById(R.id.sendHttp_btn);
        showHttpResponseTv = (TextView) findViewById(R.id.show_httpresponse_TV);

        sendHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHttp();
            }
        });
    }


    /**
     * 发送异步请求
     */
    private void sendHttp() {
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get("http://c.3g.163.com/nc/article/list/T1348649580692/0-1.html", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                showHttpResponseTv.setText(response);
                ToastUtils.showShort(AsyncHttpActivity.this, "请求成功");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = new String(responseBody);
                showHttpResponseTv.setText(response);
                ToastUtils.showShort(AsyncHttpActivity.this, "请求失败");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
