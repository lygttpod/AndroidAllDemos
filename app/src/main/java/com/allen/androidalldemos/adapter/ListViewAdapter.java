package com.allen.androidalldemos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.androidalldemos.R;

import java.util.List;

/**
 * Created by allen on 2015/10/20.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> listStrings;

    public ListViewAdapter(Context mContext, List<String> listStrings) {
        this.mContext = mContext;
        this.listStrings = listStrings;
    }

    @Override
    public int getCount() {
        return listStrings.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_listview_item, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item_name_TV);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(listStrings.get(position));
        return convertView;
    }

    public class ViewHolder {
        TextView textView;
    }
}
