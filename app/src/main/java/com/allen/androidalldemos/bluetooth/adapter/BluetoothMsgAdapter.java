package com.allen.androidalldemos.bluetooth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
     * 解决办法 ：暂时使用一个布局使用gone和visable判断显示
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
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text, null);
            viewHolder.msg_left = (TextView) convertView.findViewById(R.id.msg_left_TV);
            viewHolder.msg_right = (TextView) convertView.findViewById(R.id.msg_right_TV);

            viewHolder.left_ll = (RelativeLayout) convertView.findViewById(R.id.left_msg_ll);
            viewHolder.right_ll = (RelativeLayout) convertView.findViewById(R.id.right_msg_ll);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!msgtype) {
            viewHolder.left_ll.setVisibility(View.VISIBLE);
            viewHolder.right_ll.setVisibility(View.GONE);
            viewHolder.msg_left.setText(messageBeans.get(position).getMsg());
        } else {
            viewHolder.left_ll.setVisibility(View.GONE);
            viewHolder.right_ll.setVisibility(View.VISIBLE);
            viewHolder.msg_right.setText(messageBeans.get(position).getMsg());
        }

        return convertView;
    }

    private class ViewHolder {
        private RelativeLayout left_ll;
        private RelativeLayout right_ll;
        private TextView msg_left;
        private TextView msg_right;
    }
}
