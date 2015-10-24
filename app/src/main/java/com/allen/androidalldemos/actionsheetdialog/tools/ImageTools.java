package com.allen.androidalldemos.actionsheetdialog.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

public class ImageTools {

	public Uri imageUri;
	public File imageFile;

	public static String sdcardPathName = Environment.getExternalStorageDirectory()
			+ "/allen/";
	public static String fileName = "allen.jpg";

	public static void saveImg(Bitmap bitmap) {
		// ���ȱ���ͼƬ
		File appDir = new File(sdcardPathName);
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Bitmap getImg(String filePath) {
		Bitmap bitmap = null;
		File mFile = new File(filePath);
		// �����ļ�����
		if (mFile.exists()) {
			bitmap = BitmapFactory.decodeFile(filePath);
			return bitmap;
		}
		return bitmap;
	}

}
