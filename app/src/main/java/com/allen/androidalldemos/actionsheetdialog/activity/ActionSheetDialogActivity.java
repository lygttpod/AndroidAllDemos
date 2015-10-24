package com.allen.androidalldemos.actionsheetdialog.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.actionsheetdialog.tools.ImageTools;
import com.allen.androidalldemos.actionsheetdialog.view.ActionSheetDialog;
import com.allen.androidalldemos.qrcode.tools.EncodingHandler;
import com.allen.androidalldemos.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by allen on 2015/10/24.
 */
public class ActionSheetDialogActivity extends AppCompatActivity {

    private Button button1, button2;
    private ActionSheetDialog actionSheetDialog;
    private ImageView pictureImv;
    private Uri imageUri;
    private File imageFile;
    public static final int PHOTO_REQUEST_CAREMA = 1;
    public static final int CROP_PHOTO = 2;
    public static final int PHOTO_REQUEST_GALLERY = 3;
    public static final int PHOTO_REQUEST_CUT = 4;

    private String sdcardPathName = Environment.getExternalStorageDirectory()
            + "/allen/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_actionsheet_dialog);
        initToolbar();
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        pictureImv = (ImageView) findViewById(R.id.imageView);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_upload_image_Dialog();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMoreData();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("仿iOS选择框");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init_upload_image_Dialog() {
        actionSheetDialog = new ActionSheetDialog(this).builder();
        actionSheetDialog.setCancelable(true);
        actionSheetDialog.setCanceledOnTouchOutside(true);
        actionSheetDialog.addSheetItem("从相机", ActionSheetDialog.SheetItemColor.Blue,
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        camera();
                    }
                });
        actionSheetDialog.addSheetItem("从相册", ActionSheetDialog.SheetItemColor.Blue,
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        gallery();
                    }
                });
        actionSheetDialog.show();
    }

    private void initMoreData() {
        actionSheetDialog = new ActionSheetDialog(this).builder();
        actionSheetDialog.setCancelable(true);
        actionSheetDialog.setCanceledOnTouchOutside(true);
        actionSheetDialog.setTitle("这是设置标题");
        for (int i = 0; i < 5; i++) {
            actionSheetDialog.addSheetItem("内容" + i, ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    ToastUtils.showShort(ActionSheetDialogActivity.this, "选择了" + which);
                }
            });
        }
        actionSheetDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:

                if (resultCode == RESULT_OK) {
                    crop(Uri.fromFile(imageFile));
                }

                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory
                                .decodeStream(getContentResolver().openInputStream(
                                        imageUri));
                        pictureImv.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PHOTO_REQUEST_GALLERY:
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop(uri);
                }

                break;
            case PHOTO_REQUEST_CUT:
                // 从剪切图片返回的数据
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    ImageTools.saveImg(bitmap);
                    this.pictureImv.setImageBitmap(bitmap);
                }
                try {
                    // 将临时文件删除
                    // tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    /*
         * 从相机获取
         */
    public void camera() {
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        imageFile = new File(sdcardPathName, "tempImage.jpg");
        try {
            if (imageFile.exists()) {
                imageFile.delete();
            }
            imageFile.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 从文件中创建uri
        Uri uri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    /*
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片android.intent.action.GET_CONTENT
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}
