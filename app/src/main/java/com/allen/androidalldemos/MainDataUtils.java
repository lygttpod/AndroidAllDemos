package com.allen.androidalldemos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2015/12/17.
 */
public class MainDataUtils {

    static String name[] = {"异步网络请求(android-async-http)",
            "图片异步加载(universal-image-loader)",
            "分享功能(shareSDK)",
            "手势密码解锁(Gesture_LockPsd)",
            "广告页滑动(bannerpager)",
            "仿新闻导航菜单(tab+viewpager)",
            "弹出框(dialog)",
            "仿iOS选择框(actionsheetdialog)",
            "二维码扫码生成(zxing)",
            "水平/垂直联动demo",
            "天气预报_普通解析",
            "天气预报_gson解析",
            "蓝牙通讯",
            "本地监听网络请求(nanohttpd+acache)",
            "自定义loading对话框",
            "Android Material Design",
            "RecycleView",
            "DragRecycleView",
            "底部导航菜单"
    };

    public static List<MainDataBean> getListDate() {
        List<MainDataBean> dataBeans = new ArrayList<>();

        for (int i = 0; i < name.length; i++) {
            MainDataBean dataBean = new MainDataBean();
            dataBean.setId(i);
            dataBean.setName(name[i]);
            dataBeans.add(dataBean);
        }
        return dataBeans;
    }

    public static List<String> getListDate1() {
        List<String> dataBeans = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            dataBeans.add(name[i]);
        }
        return dataBeans;
    }
}
