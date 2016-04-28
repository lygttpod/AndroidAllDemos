package com.allen.androidalldemos.recycleview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.adapter.RecycleviewAdapter;
import com.allen.androidalldemos.recycleview.adapter.RecycleView_Grid_Adapter;
import com.allen.androidalldemos.recycleview.adapter.RecycleView_List_Adapter;
import com.allen.androidalldemos.recycleview.adapter.RecycleView_Staggered_Adapter;
import com.allen.androidalldemos.recycleview.bean.DataBean;
import com.allen.androidalldemos.recycleview.data.Data;
import com.allen.androidalldemos.recycleview.itemTouchHelper.ItemTouchHelperAdapter;
import com.allen.androidalldemos.recycleview.itemTouchHelper.SimpleGridItemTouchHelperCallback;
import com.allen.androidalldemos.recycleview.itemTouchHelper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Allen on 2015/12/16.
 */
public class RecycleViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        recyclerView = ((RecyclerView) findViewById(R.id.recycler_view));

        loadListData(false, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycleview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //list显示的几种方式
        if (id == R.id.action_list_normal) {
            loadListData(false, true);
            return true;
        } else if (id == R.id.action_list_vertical_reverse) {
            loadListData(true, true);
            return true;
        } else if (id == R.id.action_list_horizontal) {
            loadListData(false, false);
            return true;
        } else if (id == R.id.action_list_horizontal_reverse) {
            loadListData(true, false);
            return true;
        }
        //grid显示的几种方式

        if (id == R.id.action_grid_normal) {
            loadGridData(false, true);
            return true;
        } else if (id == R.id.action_grid_vertical_reverse) {
            loadGridData(true, true);
            return true;
        } else if (id == R.id.action_grid_horizontal) {
            loadGridData(false, false);
            return true;
        } else if (id == R.id.action_grid_horizontal_reverse) {
            loadGridData(true, false);
            return true;
        }

        //瀑布流显示的几种方式
        if (id == R.id.action_staggered_normal) {
            loadStaggeredData(false, true);
            return true;
        } else if (id == R.id.action_staggered_vertical_reverse) {
            loadStaggeredData(true, true);
            return true;
        } else if (id == R.id.action_staggered_horizontal) {
            loadStaggeredData(false, false);
            return true;
        } else if (id == R.id.action_staggered_horizontal_reverse) {
            loadStaggeredData(true, false);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    //获取数据
    private void getData(List<DataBean> dataBeans) {
        for (int i = 0; i < Data.icon.length; i++) {
            DataBean dataBean = new DataBean();
            dataBean.setIcon(Data.icon[i]);
            dataBean.setTitle("图片描述" + i);
            dataBeans.add(dataBean);
        }
    }

    //获取瀑布流数据
    private void getStaggeredData(List<DataBean> dataBeans) {
        for (int i = 0; i < Data.pic.length; i++) {
            DataBean dataBean = new DataBean();
            dataBean.setIcon(Data.pic[i]);
            dataBean.setTitle("图片描述" + i);
            dataBeans.add(dataBean);
        }
    }


    /**
     * 加载数据
     *
     * @param isReverse
     * @param isVertical
     */
    private void loadListData(boolean isReverse, boolean isVertical) {
        final List<DataBean> dataBeans = new ArrayList<>();
        getData(dataBeans);
        //1、设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置是否反向布局
        layoutManager.setReverseLayout(isReverse);
        //设置是垂直布局还是水平布局
        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        final RecycleView_List_Adapter list_adapter = new RecycleView_List_Adapter(this,dataBeans);
        //2.设置适配器
        recyclerView.setAdapter(list_adapter);

        //3.滑动删除及拖拽效果
        list_Drag_Delete(dataBeans,list_adapter);
    }


    /**
     * 加载grid数据
     *
     * @param isReverse
     * @param isVertical
     */
    private void loadGridData(boolean isReverse, boolean isVertical) {
        List<DataBean> dataBeans = new ArrayList<>();
        getData(dataBeans);
        //1.设置布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //2.设置是否反向布局
        layoutManager.setReverseLayout(isReverse);
        //3.设置垂直布局还是水平布局
        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        final RecycleView_Grid_Adapter grid_adapter = new RecycleView_Grid_Adapter(this, dataBeans);
        recyclerView.setAdapter(grid_adapter);

    }

    /**
     * 加载瀑布流数据
     *
     * @param isReverse
     * @param isVertical
     */
    private void loadStaggeredData(boolean isReverse, boolean isVertical) {
        List<DataBean> dataBeans = new ArrayList<>();
        getStaggeredData(dataBeans);
        //设置瀑布流布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);
        recyclerView.setLayoutManager(layoutManager);
        final RecycleView_Staggered_Adapter staggered_adapter =new RecycleView_Staggered_Adapter(this, dataBeans);
        recyclerView.setAdapter(staggered_adapter);

    }


    private void list_Drag_Delete(final List<DataBean> dataBean, final RecyclerView.Adapter adapter){
        ItemTouchHelperAdapter touchHelperAdapter = new ItemTouchHelperAdapter() {
            @Override
            public void onItemMove(int fromPosition, int toPosition) {
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(dataBean, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(dataBean, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemDismiss(int position) {
                dataBean.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
        ItemTouchHelper.Callback mCallback = new SimpleItemTouchHelperCallback(touchHelperAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(mCallback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

}
