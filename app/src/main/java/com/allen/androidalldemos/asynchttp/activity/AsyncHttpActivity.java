package com.allen.androidalldemos.asynchttp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allen.androidalldemos.BaseActivity;
import com.allen.androidalldemos.R;
import com.allen.androidalldemos.applaction.MyApplaction;
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


    List<ChannelItem> userChannelList;
    List<ChannelItem> otherChannelList;

    GreenDaoUtils greenDaoUtils;
    MyApplaction myApplaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynchttp);
        myApplaction = (MyApplaction) getApplication();
        greenDaoUtils = myApplaction.getGreenDaoUtils();

        userChannelList = new ArrayList<ChannelItem>();
        otherChannelList = new ArrayList<ChannelItem>();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sendHttpBtn = (Button) findViewById(R.id.sendHttp_btn);
        showHttpResponseTv = (TextView) findViewById(R.id.show_httpresponse_TV);

        sendHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userChannelList.size() <= 0) {
                    sendHttp();
                }

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
        asyncHttpClient.get("http://c.3g.163.com/nc/topicset/android/subscribe/manage/listspecial.html", new AsyncHttpResponseHandler() {
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
                handlejson(responseBody);
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

    private void handlejson(byte[] bytes) {
        String jsonStr = new String(bytes);
        JSONObject jsonObject = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("tList");
            for (int b = 0; b < jsonArray.length(); b++) {
                JSONObject json = jsonArray.getJSONObject(b);

                stringBuilder.append("tname=" + json.getString("tname") + "tid=" + json.getString("tid") + "\n");

                int isSelect = 0;
                if (json.get("tname").equals("头条")
                        || json.get("tname").equals("娱乐")
                        || json.get("tname").equals("科技")
                        || json.get("tname").equals("手机")
                        || json.get("tname").equals("NBA")
                        || json.get("tname").equals("社会")
                        || json.get("tname").equals("体育")) {
                    isSelect = 1;
                    ChannelItem channelItem = new ChannelItem(
                            json.getString("tname"), json.getString("tid"), b,
                            isSelect);
                    userChannelList.add(channelItem);
                } else {
                    ChannelItem channelItem = new ChannelItem(
                            json.getString("tname"), json.getString("tid"), b + 100,
                            isSelect);
                    otherChannelList.add(channelItem);
                }

            }

            greenDaoUtils.saveUserChannel(userChannelList);
            greenDaoUtils.saveOtherChannel(otherChannelList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
