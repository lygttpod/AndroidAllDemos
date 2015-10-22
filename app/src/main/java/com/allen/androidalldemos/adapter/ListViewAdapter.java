package com.allen.androidalldemos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by allen on 2015/10/20.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> listStrings;
    private List<String> imageurlStrings;

    public ListViewAdapter(Context mContext, List<String> listStrings) {
        this.mContext = mContext;
        this.listStrings = listStrings;
    }

    public ListViewAdapter(Context mContext, List<String> listStrings, List<String> imageurlStrings) {
        this.mContext = mContext;
        this.listStrings = listStrings;
        this.imageurlStrings = imageurlStrings;
    }

    @Override
    public int getCount() {
        if (listStrings.size() > 0) {
            return listStrings.size();
        }

        return imageurlStrings.size();

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
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_image_IV);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (listStrings.size() > 0) {
            viewHolder.textView.setText(listStrings.get(position));
        }
        if (imageurlStrings.size() > 0) {
            // 显示图片的配置
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true)
                    .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
            ImageLoader.getInstance()
                    .displayImage(
                            imageurlStrings.get(position),
                            viewHolder.imageView, options);
        }

        return convertView;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
