package com.allen.androidalldemos.asynchttp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allen.androidalldemos.BaseActivity;
import com.allen.androidalldemos.R;
import com.allen.androidalldemos.applaction.MyApplaction;
import com.allen.androidalldemos.asynchttp.common.UrlString;
import com.allen.androidalldemos.utils.GreenDaoUtils;
import com.allen.androidalldemos.utils.ToastUtils;
import com.allen.androidalldemos.utils.db.greenrobot.gen.ChannelItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by allen on 2015/10/20.
 */
public class AsyncHttpActivity extends BaseActivity {
    private AsyncHttpClient asyncHttpClient;
    private Button sendHttpBtn;
    private TextView showHttpResponseTv;
    private ProgressBar progressBar;

    GreenDaoUtils greenDaoUtils;
    MyApplaction myApplaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynchttp);
        myApplaction = (MyApplaction) getApplication();
        greenDaoUtils = myApplaction.getGreenDaoUtils();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sendHttpBtn = (Button) findViewById(R.id.sendHttp_btn);
        showHttpResponseTv = (TextView) findViewById(R.id.show_httpresponse_TV);

        sendHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHttp();
            }
        });
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("异步网络数据请求");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 发送异步请求
     */
    private void sendHttp() {
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(UrlString.urlString, new AsyncHttpResponseHandler() {
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
//                String response = new String(responseBody);
                showHttpResponseTv.setText(error.getMessage());
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
