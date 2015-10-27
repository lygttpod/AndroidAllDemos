package com.allen.androidalldemos.fixed.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.fixed.adapter.BondSearchResultAdapter;
import com.allen.androidalldemos.fixed.tools.ToolFile;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by allen on 2015/10/26.
 */
public class HVScorllListviewActivity extends AppCompatActivity {

    /**
     * 列表表头容器
     **/
    private RelativeLayout mListviewHead;
    /**
     * 列表ListView
     **/
    private PullToRefreshListView mListView;
    /**
     * 列表ListView水平滚动条
     **/
    private HorizontalScrollView mHorizontalScrollView;
    /**
     * 列表适配器
     **/
    private BondSearchResultAdapter mListAdapter;
    /**
     * 总记录数
     **/
    private int totalRecordCount = 0;
    /**
     * 每一页请求条数
     **/
    private int mPerPageCount = 10;
    /***
     * 请求页码
     **/
    private int mPageNo = 1;
    /**
     * 是否加载完成
     **/
    private boolean isLoadFinished = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvscorll_listview);
        initToolbar();
        initView();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("水平/垂直滚动联动demo");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        {

            //初始化列表表头
            mListviewHead = (RelativeLayout) findViewById(R.id.head);
            mListviewHead.setFocusable(true);
            mListviewHead.setClickable(true);
            mListviewHead.setBackgroundColor(getResources().getColor(R.color.table_header));
            mListviewHead.setOnTouchListener(mHeadListTouchLinstener);
            mListviewHead.setVisibility(View.GONE);
            mHorizontalScrollView = (HorizontalScrollView) mListviewHead.findViewById(R.id.horizontalScrollView1);

            //实例化listview适配器
            mListAdapter = new BondSearchResultAdapter(this, mPerPageCount, mListviewHead);

            //初始化listview
            mListView = (PullToRefreshListView) findViewById(R.id.listView1);
            mListView.setMode(PullToRefreshBase.Mode.DISABLED);
            mListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载..");
            mListView.getLoadingLayoutProxy().setReleaseLabel("释放刷新");
            mListView.setOnScrollListener(mListViewScrollListener);
            mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    mListView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
                    //下拉刷新
                    if (isLoadFinished) {
                        requestData(1);
                        // mListView.getLoadingLayoutProxy().setLastUpdatedLabel("上次更新："+ToolDateTime.formatDateTime(new Date(), "yyyy-MM-dd HH:mm"));
                    }
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    mListView.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
                    //上拉加载更多
                    if (mListAdapter.getCount() < totalRecordCount && isLoadFinished) {
                        requestData(mListAdapter.getPageNo());
                    }
                }
            });

            //获取到Listview
            ListView listview = mListView.getRefreshableView();
            listview.setOnTouchListener(mHeadListTouchLinstener);
            listview.setAdapter(mListAdapter);

            //等待对话框
            requestData(1);
            // mListView.getLoadingLayoutProxy().setLastUpdatedLabel("上次更新："+ToolDateTime.formatDateTime(new Date(), "yyyy-MM-dd HH:mm"));


        }
    }


    /**
     * 列头/Listview触摸事件监听器<br>
     * 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
     */
    private View.OnTouchListener mHeadListTouchLinstener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mHorizontalScrollView.onTouchEvent(event);
            return false;
        }
    };

    /**
     * listView滚动监听器
     */
    private AbsListView.OnScrollListener mListViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        /**
         * firstVisibleItem 当前页第一条记录的索引
         * visibleItemCount 当前页可见条数
         * totalItemCount 已加载总记录数
         **/
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (visibleItemCount > 0
                    && firstVisibleItem + visibleItemCount == totalItemCount
                    && totalRecordCount > totalItemCount && isLoadFinished) {
                requestData(mListAdapter.getPageNo());
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    };

    /**
     * 请求数据
     */
    private void requestData(int pageNo) {
        mPageNo = pageNo;

        try {

            //正在加载
            isLoadFinished = false;
            //模拟数据
            String data = ToolFile.readAssetsValue(HVScorllListviewActivity.this, "hvc_data.json");
            parseData(new JSONObject(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析数据
     *
     * @param jsonObject
     */
    private void parseData(JSONObject jsonObject) {
        try {
            // 错误信息
            String errorMessage = jsonObject.getString("errorMsg");

            // 获取数据
            JSONArray dataList = jsonObject.getJSONArray("dataList");
            //下拉刷新情况
            if (mPageNo == 1) {
                mListAdapter.clear();
            }
            for (int i = 0; i < dataList.length(); i++) {
                mListAdapter.addItem(dataList.getJSONObject(i));
            }
            //列头可见
            mListviewHead.setVisibility(View.VISIBLE);
            //通知更新数据
            mListAdapter.notifyDataSetChanged();
            //结果集中记录数 TODO
            totalRecordCount = jsonObject.optInt("dataCount", dataList.length());
            //刷新完成
            isLoadFinished = true;
            mListView.onRefreshComplete();

        } catch (Exception e) {
            Log.e("allen", e.getMessage().toString());
            //刷新完成
            isLoadFinished = true;
            mListView.onRefreshComplete();
        }

    }
}
