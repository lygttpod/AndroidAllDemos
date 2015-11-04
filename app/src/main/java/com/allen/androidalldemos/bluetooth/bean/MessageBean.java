package com.allen.androidalldemos.bluetooth.bean;

/**
 * Created by allen on 2015/11/3.
 */
public class MessageBean {

    private String username;
    private String msg;
    private  boolean issendmsg;


    public MessageBean(String username, String msg, boolean issendmsg) {
        this.username = username;
        this.msg = msg;
        this.issendmsg = issendmsg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean issendmsg() {
        return issendmsg;
    }

    public void setIssendmsg(boolean issendmsg) {
        this.issendmsg = issendmsg;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "username='" + username + '\'' +
                ", msg='" + msg + '\'' +
                ", issendmsg=" + issendmsg +
                '}';
    }
}
