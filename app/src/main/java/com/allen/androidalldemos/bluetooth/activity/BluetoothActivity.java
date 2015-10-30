package com.allen.androidalldemos.bluetooth.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by allen on 2015/10/29.
 */
public class BluetoothActivity extends AppCompatActivity {
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
                ToastUtils.showShort(BluetoothActivity.this, address);

            }
        });


        unBondAdapter = new BluetoothDvAdapter(this, unbond_bluetoothBeans);
        unbondList.setAdapter(unBondAdapter);
        unbondList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = unbond_bluetoothBeans.get(position).getDvAddress();
                ToastUtils.showShort(BluetoothActivity.this, address);


            }
        });
    }

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
                    ToastUtils.showShort(BluetoothActivity.this, "搜索完成");
                }
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
        ToastUtils.showShort(BluetoothActivity.this, "注销广播");
    }

}
