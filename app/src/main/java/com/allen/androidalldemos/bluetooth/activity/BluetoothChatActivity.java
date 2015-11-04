package com.allen.androidalldemos.bluetooth.activity;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allen.androidalldemos.MainActivity;
import com.allen.androidalldemos.R;
import com.allen.androidalldemos.bluetooth.adapter.BluetoothMsgAdapter;
import com.allen.androidalldemos.bluetooth.bean.MessageBean;
import com.allen.androidalldemos.bluetooth.bluetoothtools.BluetoothChatService;
import com.allen.androidalldemos.bluetooth.bluetoothtools.Constants;
import com.allen.androidalldemos.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class BluetoothChatActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "BluetoothChatFragment";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button sendButton;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    //private ArrayAdapter<String> mConversationArrayAdapter;

    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    private BluetoothChatService mChatService = null;

    private boolean isshowdialog;
    private ProgressBar progressBar;

    private List<MessageBean> messageBeans;
    private BluetoothMsgAdapter msgAdapter;
    Toolbar toolbar;
    private LinearLayout msg_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_chat);
        isshowdialog = (boolean) SPUtils.get(BluetoothChatActivity.this, "isshowdialog", true);
        messageBeans = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        msg_ll = (LinearLayout) findViewById(R.id.msg_ll);
        initToolbar();

        mConversationView = (ListView) findViewById(R.id.in);
        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {

            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();

        }

        ensureDiscoverable();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("蓝牙通讯");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChatService != null) {
                    mChatService.stop();
                }
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        isshowdialog = false;
                        Intent serverIntent = new Intent(BluetoothChatActivity.this,
                                DeviceListActivity.class);
                        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
        if (isshowdialog) {
            dialog();
        }

    }

    private void dialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BluetoothChatActivity.this);
        builder.setTitle("使用说明");
        builder.setCancelable(false);
        builder.setMessage(R.string.bt_tip);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isshowdialog = false;
                SPUtils.put(BluetoothChatActivity.this, "isshowdialog", isshowdialog);
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity
        // returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't
            // started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
//		mConversationArrayAdapter = new ArrayAdapter<String>(this,
//				R.layout.message);
        msgAdapter = new BluetoothMsgAdapter(BluetoothChatActivity.this, messageBeans);
        mConversationView.setAdapter(msgAdapter);
        //mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        // mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        // if (mBluetoothAdapter.getScanMode() !=
        // BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent discoverableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(
                    BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
            startActivity(discoverableIntent);
        }

        // }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            // setStatus(getString(R.string.title_connected_to,
                            // mConnectedDeviceName));
                            //mConversationArrayAdapter.clear();
                            //messageBeans.clear();
                            msgAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            // setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            // setStatus(R.string.title_not_connected);
                            progressBar.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    MessageBean messageBean = new MessageBean("我", writeMessage, true);
                    messageBeans.add(messageBean);
                    msgAdapter.notifyDataSetChanged();
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
//				mConversationArrayAdapter.add(mConnectedDeviceName + ":  "
//						+ readMessage);
                    MessageBean messageBean1 = new MessageBean(mConnectedDeviceName, readMessage, false);
                    messageBeans.add(messageBean1);
                    msgAdapter.notifyDataSetChanged();
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(
                            Constants.DEVICE_NAME);
                    toolbar.setTitle(mConnectedDeviceName);
                    toolbar.setSubtitle("正在聊天");
                    Toast.makeText(BluetoothChatActivity.this,
                            "连接到 " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    msg_ll.setVisibility(View.VISIBLE);

                    break;
                case Constants.MESSAGE_TOAST:

                    Toast.makeText(BluetoothChatActivity.this,
                            msg.getData().getString(Constants.TOAST),
                            Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    progressBar.setVisibility(View.VISIBLE);
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    /**
     * Establish connection with other divice
     *
     * @param data   An {@link Intent} with
     *               {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras().getString(
                DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.button_send:
                String message = mOutEditText.getText().toString().trim();
                sendMessage(message);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bluetooth_menu, menu);
        return true;
    }
}
