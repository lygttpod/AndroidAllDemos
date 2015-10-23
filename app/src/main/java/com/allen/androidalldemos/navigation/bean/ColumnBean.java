package com.allen.androidalldemos.navigation.bean;

/**
 * 导航数据bean文件
 * Created by allen on 2015/10/23.
 */
public class ColumnBean {

    private String tabName;
    private String tabId;

    public ColumnBean(String tabName, String tabId) {
        this.tabName = tabName;
        this.tabId = tabId;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    @Override
    public String toString() {
        return "ColumnBean{" +
                "tabName='" + tabName + '\'' +
                ", tabId='" + tabId + '\'' +
                '}';
    }
}
