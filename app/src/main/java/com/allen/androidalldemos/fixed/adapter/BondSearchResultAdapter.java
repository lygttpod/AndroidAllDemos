package com.allen.androidalldemos.fixed.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.fixed.view.ObserverHScrollView;

import org.json.JSONObject;

/**
 * Created by allen on 2015/10/26.
 */
public class BondSearchResultAdapter extends com.allen.androidalldemos.fixed.adapter.BaseAdapter {
    /**列表表头容器**/
    private RelativeLayout mListviewHead;
    private Context mContext;

    public BondSearchResultAdapter(Activity mContext, int mPerPageSize) {
        super(mContext, mPerPageSize);
    }

    public BondSearchResultAdapter(Activity mContext, int mPerPageSize, RelativeLayout mListviewHead) {
        super(mContext, mPerPageSize);
        this.mContext = mContext;
        this.mListviewHead = mListviewHead;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 查找控件
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_hvscorll_listview_item, null);
            holder = new ViewHolder();

            holder.txt1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.txt2 = (TextView) convertView.findViewById(R.id.textView2);
            holder.txt3 = (TextView) convertView.findViewById(R.id.textView3);
            holder.txt4 = (TextView) convertView.findViewById(R.id.textView4);
            holder.txt5 = (TextView) convertView.findViewById(R.id.textView5);
            holder.txt6 = (TextView) convertView.findViewById(R.id.textView6);
            holder.txt7 = (TextView) convertView.findViewById(R.id.textView7);
            holder.txt8 = (TextView) convertView.findViewById(R.id.textView8);
            holder.txt9 = (TextView) convertView.findViewById(R.id.textView9);
            holder.txt10 = (TextView) convertView.findViewById(R.id.textView10);
            holder.txt11 = (TextView) convertView.findViewById(R.id.textView11);
            holder.txt12 = (TextView) convertView.findViewById(R.id.textView12);
            holder.txt13 = (TextView) convertView.findViewById(R.id.textView13);
            holder.txt14 = (TextView) convertView.findViewById(R.id.textView14);
            holder.txt15 = (TextView) convertView.findViewById(R.id.textView15);
            holder.txt16 = (TextView) convertView.findViewById(R.id.textView16);
            holder.txt17 = (TextView) convertView.findViewById(R.id.textView17);
            holder.txt18 = (TextView) convertView.findViewById(R.id.textView18);
            holder.txt19 = (TextView) convertView.findViewById(R.id.textView19);
            holder.txt20 = (TextView) convertView.findViewById(R.id.textView20);
            holder.txt21 = (TextView) convertView.findViewById(R.id.textView21);
            holder.txt22 = (TextView) convertView.findViewById(R.id.textView22);
            //列表水平滚动条
            ObserverHScrollView scrollView1 = (ObserverHScrollView) convertView.findViewById(R.id.horizontalScrollView1);
            holder.scrollView = (ObserverHScrollView) convertView.findViewById(R.id.horizontalScrollView1);
            //列表表头滚动条
            ObserverHScrollView headSrcrollView = (ObserverHScrollView) mListviewHead.findViewById(R.id.horizontalScrollView1);
            headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 装填数据
        JSONObject data = (JSONObject) getItem(position);
        try {
            holder.txt1.setText(data.optString("zqmc"));
            holder.txt2.setText(data.optString("zqjc"));
            holder.txt3.setText(data.optString("zqdm"));
            holder.txt4.setText(data.optString("fxjc"));
            holder.txt5.setText(data.optString("ksr"));
            holder.txt6.setText(data.optString("fxjg"));
            holder.txt7.setText(data.optString("sjfxze"));
            holder.txt8.setText(data.optString("jxfs"));
            holder.txt9.setText(data.optString("fxzq"));
            holder.txt10.setText(data.optString("pmll"));
            holder.txt11.setText(data.optString("lc"));
            holder.txt12.setText(data.optString("zjll"));
            holder.txt13.setText(data.optString("zqqx"));
            holder.txt14.setText(data.optString("zqpj"));
            holder.txt15.setText(data.optString("ztpj"));
            holder.txt16.setText(data.optString("zqpjjg"));
            holder.txt17.setText(data.optString("ztpjjg"));
            holder.txt18.setText(data.optString("zqzwdjr"));
            holder.txt19.setText(data.optString("ltr"));
            holder.txt20.setText(data.optString("jzghr"));
            holder.txt21.setText(data.optString("qxr"));
            holder.txt22.setText(data.optString("dqr"));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage().toString());
        }

        return convertView;
    }

    private class OnScrollChangedListenerImp implements ObserverHScrollView.OnScrollChangedListener {
        ObserverHScrollView mScrollViewArg;

        public OnScrollChangedListenerImp(ObserverHScrollView scrollViewar) {
            mScrollViewArg = scrollViewar;
        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollViewArg.smoothScrollTo(l, t);
        }
    };

    private class ViewHolder {
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        TextView txt6;
        TextView txt7;
        TextView txt8;
        TextView txt9;
        TextView txt10;
        TextView txt11;
        TextView txt12;
        TextView txt13;
        TextView txt14;
        TextView txt15;
        TextView txt16;
        TextView txt17;
        TextView txt18;
        TextView txt19;
        TextView txt20;
        TextView txt21;
        TextView txt22;
        HorizontalScrollView scrollView;
    }
}
