package com.allen.androidalldemos.sharesdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.allen.androidalldemos.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by hardy on 2015/10/22.
 */
public class ShareActivity extends AppCompatActivity {

    private Button shareSDKBtn, shareAndroidBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ShareSDK.initSDK(this);
        initToolbar();
        shareSDKBtn = (Button) findViewById(R.id.shareSDKBtn);
        shareAndroidBtn = (Button) findViewById(R.id.shareAndroidBtn);

        shareSDKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        shareAndroidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                // intent.setType("image/*");//intent.setType("text/plain");
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Android原生分享功能");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("分享功能");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setDialogMode();
        // 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
        // oks.setNotification(R.drawable.ic_launcher,
        // getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setImageUrl("http://lygttpod.com");
        oks.setTitle("分享标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://lygttpod.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("分享内容");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://lygttpod.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("分享内容");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://lygttpod.com");

        // 启动分享GUI
        oks.show(this);
    }
}
