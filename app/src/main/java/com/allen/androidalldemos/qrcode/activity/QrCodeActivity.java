package com.allen.androidalldemos.qrcode.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.qrcode.tools.EncodingHandler;
import com.allen.androidalldemos.utils.ToastUtils;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.Decoder;

/**
 * Created by allen on 2015/10/24.
 */
public class QrCodeActivity extends AppCompatActivity {

    EncodingHandler encodingHandler;
    Button createQrBtn, saomiaoQrBtn;
    EditText qrString_ET;
    ImageView qr_IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initToolbar();

        createQrBtn = (Button) findViewById(R.id.create_qr_button);
        saomiaoQrBtn = (Button) findViewById(R.id.saomiao_qr_button);
        qrString_ET = (EditText) findViewById(R.id.qrString_ET);
        qr_IV = (ImageView) findViewById(R.id.qr_IV);


        createQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qrString = qrString_ET.getText().toString();
                if (!qrString.equals("")) {
                    CreateQR(qrString);
                } else {
                    ToastUtils.showShort(QrCodeActivity.this, "请先输入字符串");
                }

            }
        });

        saomiaoQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void CreateQR(String qrString) {
        encodingHandler = new EncodingHandler();
        try {
            Bitmap bitmap = encodingHandler.createQRCode(qrString, 600);

            //Bitmap bitmap = encodingHandler.createCode(qrString, zzkh_icon);//中间带图片的二维码
            qr_IV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("二维码");
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
