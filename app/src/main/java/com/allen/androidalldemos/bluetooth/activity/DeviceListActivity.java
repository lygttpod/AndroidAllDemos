package com.allen.androidalldemos.bluetooth.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.bluetooth.adapter.BluetoothDvAdapter;
import com.allen.androidalldemos.bluetooth.bean.BluetoothBean;


public class DeviceListActivity extends AppCompatActivity {
	/**
	 * Tag for Log
	 */
	private static final String TAG = "DeviceListActivity";

	/**
	 * Return Intent extra
	 */
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	private Button scanBt_Btn;
	private ListView bond_bt_lv;
	private ListView un_bond_bt_lv;

	private BluetoothAdapter mBtAdapter;

	private List<BluetoothBean> bluetoothBeans_bond;
	private List<BluetoothBean> bluetoothBeans_unbond;

	private BluetoothDvAdapter mDvAdapter_bond;
	private BluetoothDvAdapter mDvAdapter_unbond;

	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bluetoothBeans_bond = new ArrayList<BluetoothBean>();
		bluetoothBeans_unbond = new ArrayList<BluetoothBean>();

		setContentView(R.layout.acitivty_device_list);
		initToolbar();
		initView();
		getBondDv();
		registerBR();
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("蓝牙列表");
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	private void initView() {
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		bond_bt_lv = (ListView) findViewById(R.id.bond_bt_lv);
		un_bond_bt_lv = (ListView) findViewById(R.id.un_bond_bt_lv);
		scanBt_Btn = (Button) findViewById(R.id.scan_bt_btn);

		mDvAdapter_bond = new BluetoothDvAdapter(DeviceListActivity.this,
				bluetoothBeans_bond);
		mDvAdapter_unbond = new BluetoothDvAdapter(DeviceListActivity.this,
				bluetoothBeans_unbond);

		bond_bt_lv.setAdapter(mDvAdapter_bond);
		un_bond_bt_lv.setAdapter(mDvAdapter_unbond);

		scanBt_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.VISIBLE);
				doDiscovery();
			}
		});

		un_bond_bt_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mBtAdapter.cancelDiscovery();
				String address = bluetoothBeans_unbond.get(arg2).getDvAddress();
				// Create the result Intent and include the MAC address
				Intent intent = new Intent();
				intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
				// Set result and finish this Activity
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});

		bond_bt_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mBtAdapter.cancelDiscovery();
				String address = bluetoothBeans_bond.get(arg2).getDvAddress();
				// Create the result Intent and include the MAC address
				Intent intent = new Intent();
				intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
				// Set result and finish this Activity
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});

	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery() {
		Log.d(TAG, "doDiscovery()");



		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}

		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}

	private void getBondDv() {
		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		// If there are paired devices, add each one to the ArrayAdapter
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				BluetoothBean bean = new BluetoothBean(device.getName(),
						device.getAddress());
				bluetoothBeans_bond.add(bean);

			}
		} else {
			String noDevices = getResources().getText(R.string.none_paired)
					.toString();
			Toast.makeText(DeviceListActivity.this, noDevices, Toast.LENGTH_SHORT).show();
		}
	}

	private void registerBR() {
		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);
	}

	/**
	 * The BroadcastReceiver that listens for discovered devices and changes the
	 * title when discovery is finished
	 */
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed
				// already
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					BluetoothBean bean = new BluetoothBean(device.getName(),
							device.getAddress());
					bluetoothBeans_unbond.add(bean);
					mDvAdapter_unbond.notifyDataSetChanged();
				}
				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.select_device);
				if (bluetoothBeans_unbond.size() == 0) {
					String noDevices = getResources().getText(
							R.string.none_found).toString();
					Toast.makeText(DeviceListActivity.this, noDevices, Toast.LENGTH_SHORT)
							.show();
				}
				progressBar.setVisibility(View.GONE);
				mDvAdapter_unbond.notifyDataSetChanged();
			}
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	};
}
