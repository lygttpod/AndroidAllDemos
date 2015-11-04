package com.allen.androidalldemos.bluetooth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.bluetooth.bean.MessageBean;

import java.util.List;

/**
 * Created by allen on 2015/11/3.
 */
public class BluetoothMsgAdapter extends BaseAdapter {

    private Context mContext;
    private List<MessageBean> messageBeans;


    public BluetoothMsgAdapter(Context mContext, List<MessageBean> messageBeans) {
        this.mContext = mContext;
        this.messageBeans = messageBeans;

    }


    @Override
    public int getCount() {
        return messageBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return messageBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 遗留问题   使用viewholder时候就不行  不使用可以  但是这样效率不高  等待高手解惑。。。
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        boolean msgtype = messageBeans.get(position).issendmsg();
        //if (convertView == null) {

        if (!msgtype) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text_left, null);
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text_right, null);
        }
        viewHolder = new ViewHolder();
        viewHolder.username = (TextView) convertView.findViewById(R.id.username_TV);
        viewHolder.msg = (TextView) convertView.findViewById(R.id.msg_TV);
        convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        viewHolder.username.setText(messageBeans.get(position).getUsername());
        viewHolder.msg.setText(messageBeans.get(position).getMsg());
        return convertView;
    }

    private class ViewHolder {
        private TextView username;
        private TextView msg;
    }
}
