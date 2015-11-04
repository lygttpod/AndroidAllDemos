package com.allen.androidalldemos.nanohttpd_and_acache.nanohttpd_utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.allen.androidalldemos.nanohttpd_and_acache.common.UrlPath;
import com.allen.androidalldemos.utils.ACache;
import com.allen.androidalldemos.utils.LogUtil;
import com.allen.androidalldemos.utils.NetWorkUtils;
import com.allen.androidalldemos.utils.SPUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class SimpleServer extends NanoHTTPD {
    public AssetManager asset_mgr;
    public Context mcontext;
    public ACache aCache;

    public SimpleServer(Context context) {
        // 端口是8088，也就是说要通过http://127.0.0.1:8088来访当问
        super(8088);
        mcontext = context;
        aCache = ACache.get(mcontext);
    }

    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        Map<String, String> parms = session.getParms();

        LogUtil.d("SimpleServer", "method----->" + method.toString());
        LogUtil.d("SimpleServer", "uri-------->" + uri.toString());
        LogUtil.d("SimpleServer", "parms------>" + parms.toString());

        // 默认传入的url是以“/”开头的，需要删除掉，否则就变成了绝对路径
        String file_name = uri.substring(1);

        switch (file_name) {

            case "reqsavemap":
                String username = parms.get("user").toString();
                String password = parms.get("psd").toString();

                SPUtils.put(mcontext, "username", username);
                SPUtils.put(mcontext, "password", password);

                String user = parms.get("user").toString() + "-----"
                        + parms.get("psd").toString();

                String result = "";
                if (user.equals("")) {
                    result = "结果为空";
                } else {
                    result = user;
                }

                return newFixedLengthResponse(result);
            case "reqreadmap":
                username = (String) SPUtils.get(mcontext, "username", "");
                password = (String) SPUtils.get(mcontext, "password", "");
                String resultString = "帐号：" + username + "  " + "密码：" + password;
                LogUtil.d("SimpleServer", "读取数据=" + resultString);

                return newFixedLengthResponse(resultString);
            case "reqxml":
                String response_Weather;
                response_Weather = getString(UrlPath.weatherUrl);

                return newFixedLengthResponse(response_Weather);

            default:
                LogUtil.d("file_name----->", file_name);
                if (file_name.endsWith("js")) {

                    response = getPage(file_name);

                    return newFixedLengthResponse_js(response);

                } else if (file_name.endsWith("css")) {

                    response = getPage(file_name);

                    return newFixedLengthResponse_css(response);
                } else if (file_name.endsWith("png")) {

                    response = getPage(file_name);

                    return newFixedLengthResponse_png(response);
                } else {

                    response = getPage(file_name);

                    return newFixedLengthResponse(response);
                }


        }

    }

    private byte[] getPage(String file_name) {
        byte[] result = null;
        result = getCache(file_name);
        if (result == null) {
            if (NetWorkUtils.isConnnected(mcontext)) {
                result = getData(UrlPath.rooturl + file_name);
                aCache.put(file_name, result, ACache.TIME_HOUR);//设置过期时间
            } else {
                if (file_name.equalsIgnoreCase("img/opps.jpg")) {
                    result = get404Page("img/opps.jpg");
                } else {
                    result = get404Page("error.html");
                }


            }
        }
        return result;
    }

    private byte[] get404Page(String filename) {
        String error_file_name = filename;
        int len = 0;
        byte[] buffer = null;
        try {
            // 通过AssetManager直接打开文件进行读取操作
            InputStream in = asset_mgr.open(error_file_name,
                    AssetManager.ACCESS_BUFFER);
            // 假设单个网页文件大小的上限是1MB
            buffer = new byte[1024 * 1024];
            int temp = 0;
            while ((temp = in.read()) != -1) {
                buffer[len] = (byte) temp;
                len++;
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 将读取到的文件内容返回给浏览器
        return buffer;
    }

    /*
    同步网络请求
     */
    private String getString(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        com.squareup.okhttp.Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    byte[] response;

    /*
    同步网络请求
    以二进制形式返回
     */
    private byte[] getData(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        com.squareup.okhttp.Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] getCache(String key) {
        return aCache.getAsBinary(key);
    }


}