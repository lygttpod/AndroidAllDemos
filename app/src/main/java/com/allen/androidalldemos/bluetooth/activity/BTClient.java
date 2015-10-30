package com.allen.androidalldemos.bluetooth.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by allen on 2015/10/30.
 */
public class BTClient {


    final String Tag = getClass().getSimpleName();
    private BluetoothSocket btsocket = null;
    private BluetoothDevice btdevice = null;
    private BufferedInputStream bis = null;
    private BufferedOutputStream bos = null;
    private BluetoothAdapter mBtAdapter = null;

    private Handler detectedHandler = null;

    public BTClient(BluetoothAdapter mBtAdapter, Handler detectedHandler) {
        this.mBtAdapter = mBtAdapter;
        this.detectedHandler = detectedHandler;
    }

    public void connectBTServer(String address) {
        //check address is correct
        if (BluetoothAdapter.checkBluetoothAddress(address)) {
            btdevice = mBtAdapter.getRemoteDevice(address);
            ThreadPool.getInstance().excuteTask(new Runnable() {
                public void run() {
                    try {
                        btsocket = btdevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

                        Message msg2 = new Message();
                        msg2.obj = "请稍候，正在连接服务器:" + BluetoothMsg.BlueToothAddress;
                        msg2.what = 0;
                        detectedHandler.sendMessage(msg2);

                        btsocket.connect();
                        Message msg = new Message();
                        msg.obj = "已经连接上服务端！可以发送信息。";
                        msg.what = 0;
                        detectedHandler.sendMessage(msg);
                        receiverMessageTask();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(Tag, e.getMessage());

                        Message msg = new Message();
                        msg.obj = "连接服务端异常！请检查服务器是否正常，断开连接重新试一试。";
                        msg.what = 0;
                        detectedHandler.sendMessage(msg);
                    }

                }
            });
        }
    }

    private void receiverMessageTask() {
        ThreadPool.getInstance().excuteTask(new Runnable() {
            public void run() {
                byte[] buffer = new byte[2048];
                int totalRead;
                /*InputStream input = null;
	            OutputStream output=null;*/
                try {
                    bis = new BufferedInputStream(btsocket.getInputStream());
                    bos = new BufferedOutputStream(btsocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    //	ByteArrayOutputStream arrayOutput=null;
                    while ((totalRead = bis.read(buffer)) > 0) {
                        //		 arrayOutput=new ByteArrayOutputStream();
                        String txt = new String(buffer, 0, totalRead, "UTF-8");
                        Message msg = new Message();
                        msg.obj = "Receiver: " + txt;
                        msg.what = 1;
                        detectedHandler.sendMessage(msg);
                    }
                } catch (EOFException e) {
                    Message msg = new Message();
                    msg.obj = "server has close!";
                    msg.what = 1;
                    detectedHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.obj = "receiver message error! make sure server is ok,and try again connect!";
                    msg.what = 1;
                    detectedHandler.sendMessage(msg);
                }
            }
        });
    }

    public boolean sendmsg(String msg) {
        boolean result = false;
        if (null == btsocket || bos == null)
            return false;
        try {
            bos.write(msg.getBytes());
            bos.flush();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void closeBTClient() {
        try {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
            if (btsocket != null)
                btsocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
