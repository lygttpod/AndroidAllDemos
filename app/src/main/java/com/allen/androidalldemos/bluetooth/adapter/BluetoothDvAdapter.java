package com.allen.androidalldemos.bluetooth.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.bluetooth.bean.BluetoothBean;


/**
 * Created by allen on 2015/10/30.
 */
public class BluetoothDvAdapter extends BaseAdapter {
    private Context mContext;
    private List<BluetoothBean> bluetoothBeans;

    public BluetoothDvAdapter(Context mContext, List<BluetoothBean> bluetoothBeans) {
        this.mContext = mContext;
        this.bluetoothBeans = bluetoothBeans;
    }

    @Override
    public int getCount() {
        return bluetoothBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_bluetooth_item, null);
            viewHolder.dvName = (TextView) convertView.findViewById(R.id.dvName_TV);
            viewHolder.dvAddress = (TextView) convertView.findViewById(R.id.dvAddress_TV);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (bluetoothBeans.size() > 0) {
            viewHolder.dvName.setText(bluetoothBeans.get(position).getDvName());
            viewHolder.dvAddress.setText(bluetoothBeans.get(position).getDvAddress());
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView dvName;
        private TextView dvAddress;
    }
}
