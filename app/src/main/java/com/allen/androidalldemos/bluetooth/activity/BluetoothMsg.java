package com.allen.androidalldemos.bluetooth.activity;

/**
 * Created by allen on 2015/10/30.
 */
public class BluetoothMsg {


    public enum ServerOrCilent{
        NONE,
        SERVICE,
        CILENT
    };
    //蓝牙连接方式
    public static ServerOrCilent serviceOrCilent = ServerOrCilent.NONE;
    //连接蓝牙地址
    public static String BlueToothAddress = null,lastblueToothAddress=null;
    //通信线程是否开启
    public static boolean isOpen = false;

}
