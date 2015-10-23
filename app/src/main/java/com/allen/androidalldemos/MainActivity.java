package com.allen.androidalldemos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.allen.androidalldemos.adapter.ListViewAdapter;
import com.allen.androidalldemos.asynchttp.activity.AsyncHttpActivity;
import com.allen.androidalldemos.bannerpager.activity.BannerPagerActivity;
import com.allen.androidalldemos.gesturelockpsd.activity.LoginActivity;
import com.allen.androidalldemos.gesturelockpsd.gesture.activity.GestureVerifyActivity;
import com.allen.androidalldemos.imageloader.ImageLoaderActivity;
import com.allen.androidalldemos.navigation.activity.NavigationActivity;
import com.allen.androidalldemos.sharesdk.ShareActivity;
import com.allen.androidalldemos.utils.SPUtils;
import com.allen.androidalldemos.utils.StringUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PullToRefreshListView pullToRefreshListView;
    private ListViewAdapter listViewAdapter;
    private List<String> list;
    private List<String> listurl;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //////////////////////////////////////////////////////////////
        initListView();
    }

    private void initlistDate() {
        list = new ArrayList<>();
        listurl = new ArrayList<>();

        list.add("异步网络请求(android-async-http)");
        list.add("图片异步加载(universal-image-loader)");
        list.add("分享功能(shareSDK)");
        list.add("手势密码解锁(Gesture_LockPsd)");
        list.add("广告页滑动(bannerpager)");
        list.add("仿新闻导航菜单(tab+viewpager)");

    }

    private void initListView() {
        initlistDate();
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
        listViewAdapter = new ListViewAdapter(this, list, listurl);
        pullToRefreshListView.setAdapter(listViewAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 1:
                        intent.setClass(MainActivity.this, AsyncHttpActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, ImageLoaderActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, ShareActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        String getGpsd = SPUtils.get(context, "gesturePsd", "").toString();
                        if (StringUtil.isNotEmpty(getGpsd)){
                            intent.setClass(context, GestureVerifyActivity.class);
                            startActivity(intent);
                        }else {
                            intent.setClass(context, LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 5:
                        intent.setClass(MainActivity.this, BannerPagerActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.setClass(MainActivity.this, NavigationActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
