package com.allen.androidalldemos.asynchttp.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.androidalldemos.BaseActivity;
import com.allen.androidalldemos.R;
import com.allen.androidalldemos.applaction.MyApplaction;
import com.allen.androidalldemos.asynchttp.bean.NewsListBean;
import com.allen.androidalldemos.asynchttp.common.UrlString;
import com.allen.androidalldemos.asynchttp.utils.JsonUtils;
import com.allen.androidalldemos.loading.view.AVLoadingIndicatorView;
import com.allen.androidalldemos.utils.GreenDaoUtils;
import com.allen.androidalldemos.utils.LogUtil;
import com.allen.androidalldemos.utils.SmartWeatherUrlUtil;
import com.allen.androidalldemos.utils.ToastUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by allen on 2015/10/20.
 */
public class AsyncHttpActivity extends BaseActivity {
    private AsyncHttpClient asyncHttpClient;
    private Button sendHttpBtn;
    private TextView showHttpResponseTv;
    private AVLoadingIndicatorView progressBar;

    GreenDaoUtils greenDaoUtils;
    MyApplaction myApplaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynchttp);
        myApplaction = (MyApplaction) getApplication();
        greenDaoUtils = myApplaction.getGreenDaoUtils();

        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progressBar);
        sendHttpBtn = (Button) findViewById(R.id.sendHttp_btn);
        showHttpResponseTv = (TextView) findViewById(R.id.show_httpresponse_TV);

        sendHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog();
                sendHttp();


            }
        });
        initToolbar();


    }

    private void dialog() {
        final String[] items = {"极小", "小", "大", "极大"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(AsyncHttpActivity.this);
        builder.setTitle("字体大小");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AsyncHttpActivity.this, "选择了" + items[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

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

                StringBuffer buffer = new StringBuffer();
                NewsListBean newsListBean = JsonUtils.getObject(response, NewsListBean.class);
                buffer.append(newsListBean.getT1348648517839().get(4).toString() + "-------------------\n");

                showHttpResponseTv.setText(buffer.toString());
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
