package com.allen.androidalldemos.gesturelockpsd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.gesturelockpsd.gesture.activity.GestureEditActivity;
import com.allen.androidalldemos.utils.ToastUtils;

import org.w3c.dom.Text;

/**
 * Created by hardy on 2015/10/23.
 */
public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText usernameEt, psdEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();
        usernameEt = (EditText) findViewById(R.id.username_editText);
        psdEt = (EditText) findViewById(R.id.psd_editText);
        loginBtn = (Button) findViewById(R.id.login_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEt.getText().toString().equalsIgnoreCase("allen") && psdEt.getText().toString().equalsIgnoreCase("123456")) {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, GestureEditActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtils.showShort(LoginActivity.this, "帐号或密码错误！");
                }
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("测试手势密码");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
