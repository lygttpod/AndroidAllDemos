package com.allen.androidalldemos.bluetooth.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.bluetooth.adapter.BluetoothDvAdapter;
import com.allen.androidalldemos.bluetooth.bean.BluetoothBean;
import com.allen.androidalldemos.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by allen on 2015/10/29.
 */
public class BluetoothActivity1 extends AppCompatActivity {
    private Button openBluBtn;
    private BluetoothAdapter bluetoothAdapter;

    private LinearLayout bond_linearLayout;
    private BluetoothBean bluetoothBean;
    private List<BluetoothBean> bond_bluetoothBeans;
    private List<BluetoothBean> unbond_bluetoothBeans;

    private ListView bondList;
    private ListView unbondList;

    private BluetoothDvAdapter unBondAdapter;
    private BluetoothDvAdapter BondAdapter;


    //////////////////////////////////
    private BluetoothDevice device;
    private BluetoothSocket clientSocket;

    private OutputStream os;
    private final UUID MY_UUID = UUID.fromString("00001104-0000-1000-8000-00805F9B34FB");


    private AcceptThread acceptThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        bond_bluetoothBeans = new ArrayList<>();
        unbond_bluetoothBeans = new ArrayList<>();

        initToolbar();

        initView();

        initBroadcast();
        initListview();

        openBluBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bond_bluetoothBeans.clear();
                unbond_bluetoothBeans.clear();

                BondAdapter.notifyDataSetChanged();
                unBondAdapter.notifyDataSetChanged();

                initBluetooth();
            }
        });
        initBluetooth();
        acceptThread = new AcceptThread();
        acceptThread.start();
    }

    private void initView() {
        bond_linearLayout = (LinearLayout) findViewById(R.id.bond_linarlayout);
        openBluBtn = (Button) findViewById(R.id.openBluBtn);

        bondList = (ListView) findViewById(R.id.bluetooth_bond_listview);
        unbondList = (ListView) findViewById(R.id.bluetooth_unbond_listview);

    }

    private void initListview() {
        BondAdapter = new BluetoothDvAdapter(this, bond_bluetoothBeans);
        bondList.setAdapter(BondAdapter);
        bondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = bond_bluetoothBeans.get(position).getDvAddress();
                ToastUtils.showShort(BluetoothActivity1.this, address);
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }

                if (device == null) {
                    device = bluetoothAdapter.getRemoteDevice(address);
                }
                if (clientSocket == null) {

                    try {
                        clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                        clientSocket.connect();

                        os = clientSocket.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.write("发送信息到蓝牙设备".getBytes("utf-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        unBondAdapter = new BluetoothDvAdapter(this, unbond_bluetoothBeans);
        unbondList.setAdapter(unBondAdapter);
        unbondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = unbond_bluetoothBeans.get(position).getDvAddress();
                ToastUtils.showShort(BluetoothActivity1.this, address);
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }

                if (device == null) {
                    device = bluetoothAdapter.getRemoteDevice(address);
                }
                if (clientSocket == null) {

                    try {
                        clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                        clientSocket.connect();

                        os = clientSocket.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.write("发送信息到蓝牙设备".getBytes("utf-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ToastUtils.showShort(BluetoothActivity1.this, String.valueOf(msg.obj));
            super.handleMessage(msg);
        }
    };

    private void initBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
        search();
        getBondBluetooth();
    }

    private void getBondBluetooth() {
        final Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (BluetoothDevice device : devices) {
                bond_linearLayout.setVisibility(View.VISIBLE);
                bluetoothBean = new BluetoothBean(device.getName(), device.getAddress(), true);
                bond_bluetoothBeans.add(bluetoothBean);
                BondAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initBroadcast() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(receiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(receiver, filter);
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("蓝牙通信");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void search() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
    }

    public final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {

                    bluetoothBean = new BluetoothBean(device.getName(), device.getAddress(), false);
                    unbond_bluetoothBeans.add(bluetoothBean);

                    unBondAdapter.notifyDataSetChanged();
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    ToastUtils.showShort(BluetoothActivity1.this, "搜索完成");
                }
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
        ToastUtils.showShort(BluetoothActivity1.this, "注销广播");
    }

    private class AcceptThread extends Thread {
        private BluetoothServerSocket serverSocket;
        private BluetoothSocket socket;
        private InputStream is;
        private OutputStream os;

        public AcceptThread() {
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("name", MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                socket = serverSocket.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();
                while (true) {
                    byte[] buffer = new byte[1024];
                    int count = is.read(buffer);

                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    handler.sendMessage(msg);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
