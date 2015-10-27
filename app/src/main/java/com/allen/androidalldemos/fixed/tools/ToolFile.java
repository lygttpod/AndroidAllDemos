package com.allen.androidalldemos.fixed.tools;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author 曾繁添
 * @version 1.0
 */
public class ToolFile {

	private static final String TAG = ToolFile.class.getSimpleName();

    /**
     * 读取assets目录的文件内容
     *
     * @param context
     *            内容上下文
     * @param fileName
     *            文件名称，包含扩展名
     * @return
     */
    public static String readAssetsValue(Context context, String fileName) {
        String result = "";
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context
     *            内容上下文
     * @param fileName
     *            文件名称，包含扩展名
     * @return
     */
    public static List<String> readAssetsListValue(Context context, String fileName) {
        List<String> list = new ArrayList<String>();
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
