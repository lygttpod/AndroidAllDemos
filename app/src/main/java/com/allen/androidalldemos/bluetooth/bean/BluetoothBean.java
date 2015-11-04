package com.allen.androidalldemos.bluetooth.bean;

/**
 * Created by allen on 2015/10/30.
 * 蓝牙设备信息
 */
public class BluetoothBean {

    private String dvName;
    private String dvAddress;
    private boolean isBond;

    public BluetoothBean(String dvName, String dvAddress) {
        this.dvAddress = dvAddress;
        this.dvName = dvName;
    }

    public String getDvName() {
        return dvName;
    }

    public void setDvName(String dvName) {
        this.dvName = dvName;
    }

    public String getDvAddress() {
        return dvAddress;
    }

    public void setDvAddress(String dvAddress) {
        this.dvAddress = dvAddress;
    }

    public boolean isBond() {
        return isBond;
    }

    public void setIsBond(boolean isBond) {
        this.isBond = isBond;
    }

    @Override
    public String toString() {
        return "BluetoothBean{" +
                "dvName='" + dvName + '\'' +
                ", dvAddress='" + dvAddress + '\'' +
                ", isBond=" + isBond +
                '}';
    }
}
