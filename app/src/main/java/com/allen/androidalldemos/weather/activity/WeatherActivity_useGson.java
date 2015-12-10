package com.allen.androidalldemos.weather.activity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.asynchttp.utils.JsonUtils;
import com.allen.androidalldemos.utils.LogUtil;
import com.allen.androidalldemos.utils.SPUtils;
import com.allen.androidalldemos.utils.StringUtil;
import com.allen.androidalldemos.utils.ToastUtils;
import com.allen.androidalldemos.weather.bean.WeatherBean;
import com.allen.androidalldemos.weather.bean.WeatherDataBean;
import com.allen.androidalldemos.weather.bean.WeatherIndexBean;
import com.allen.androidalldemos.weather.utils.NetUtils;
import com.allen.androidalldemos.weather.utils.WeatherUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hardy on 2015/11/6.
 */
public class WeatherActivity_useGson extends AppCompatActivity{


    private String provider;
    private String locationString;
    // private TextView showLocaltionTV;

    private AsyncHttpClient asyncHttpClient;

    private PullToRefreshScrollView pullToRefreshScrollView;

//    private ArrayList<WeatherIndexBean> weatherIndexBeans;
//    private ArrayList<WeatherDataBean> weatherDataBeans;
    private static int UPDATA = 1;

    private LinearLayout layout_body, layout_today;
    private TextView date, weather, wind, temperature;

    private TextView date_one, weather_one, wind_one, temperature_one;

    private TextView date_two, weather_two, wind_two, temperature_two;

    private TextView date_three, weather_three, wind_three, temperature_three;
    private ImageView[] pic = new ImageView[3];
    private String[] params;
    private TextView textView_city_show;
    private String currentCity;

    private LocationClient locationClient = null;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Battery_Saving;
    private static final int UPDATE_TIME = 1000 * 60 * 60 * 8;
    private static int LOCATION_COUTNS = 0;

    private Context context;
    private WeatherBean weatherBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);
        context = this;
        initToolbar();
//        weatherDataBeans = new ArrayList<WeatherDataBean>();
//        weatherIndexBeans = new ArrayList<WeatherIndexBean>();
        asyncHttpClient = new AsyncHttpClient();
        layout_today = (LinearLayout) findViewById(R.id.linearlayout_today);
        layout_body = (LinearLayout) findViewById(R.id.linearLayout_body);

        initWeatherBody();
        initPullToRefreshScrollView();
        if (NetUtils.isConnected(context)) {
            initLocation();
        } else {
            ToastUtils.showShort(context, "检查网络是否通畅！");
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("天气预报");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_leftarrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWeatherBody() {
        textView_city_show = (TextView) findViewById(R.id.textView_city_show);

        date = (TextView) findViewById(R.id.today_date);
        weather = (TextView) findViewById(R.id.today_weather);
        wind = (TextView) findViewById(R.id.today_wind);
        temperature = (TextView) findViewById(R.id.today_temperature);

        date_one = (TextView) findViewById(R.id.textView_date_one);
        weather_one = (TextView) findViewById(R.id.textView_weather_one);
        wind_one = (TextView) findViewById(R.id.textView_wind_one);
        temperature_one = (TextView) findViewById(R.id.textView_temperature_one);
        pic[0] = (ImageView) findViewById(R.id.imageView_pic_one);

        date_two = (TextView) findViewById(R.id.textView_date_two);
        weather_two = (TextView) findViewById(R.id.textView_weather_two);
        wind_two = (TextView) findViewById(R.id.textView_wind_two);
        temperature_two = (TextView) findViewById(R.id.textView_temperature_two);
        pic[1] = (ImageView) findViewById(R.id.imageView_pic_two);

        date_three = (TextView) findViewById(R.id.textView_date_three);
        weather_three = (TextView) findViewById(R.id.textView_weather_three);
        wind_three = (TextView) findViewById(R.id.textView_wind_three);
        temperature_three = (TextView) findViewById(R.id.textView_temperature_three);
        pic[2] = (ImageView) findViewById(R.id.imageView_pic_three);

       // updataWeather();

    }

    private void updataWeather() {
        String currentCity = (String) SPUtils.get(context,
                "currentCity", "");
        //weatherBean = (WeatherBean) SPUtils.get(context,"weatherBean","");
        textView_city_show.setText(currentCity);
        if (!weatherBean.getResults().get(0).getWeather_data().get(0).getDate().equals("")) {
            if (weatherBean.getResults().get(0).getWeather_data().get(0).getDate().length() > 9) {
                String dateString = StringUtil.Substring(weatherBean.getResults().get(0).getWeather_data()
                        .get(0).getDate());
                date.setText(dateString);

            } else {
                String dataString1 = StringUtil.Substring1(weatherBean.getResults().get(0).getWeather_data()
                        .get(0).getTemperature());

                date.setText(dataString1);
            }
        }

        weather.setText(weatherBean.getResults().get(0).getWeather_data().get(0).getWeather());
        wind.setText(weatherBean.getResults().get(0).getWeather_data().get(0).getWind());
        temperature.setText(weatherBean.getResults().get(0).getWeather_data().get(0).getTemperature());

        date_one.setText(weatherBean.getResults().get(0).getWeather_data().get(1).getDate());
        weather_one.setText(weatherBean.getResults().get(0).getWeather_data().get(1).getWeather());
        wind_one.setText(weatherBean.getResults().get(0).getWeather_data().get(1).getWind());
        temperature_one.setText(weatherBean.getResults().get(0).getWeather_data().get(1).getTemperature());

        date_two.setText(weatherBean.getResults().get(0).getWeather_data().get(2).getDate());
        weather_two.setText(weatherBean.getResults().get(0).getWeather_data().get(2).getWeather());
        wind_two.setText(weatherBean.getResults().get(0).getWeather_data().get(2).getWind());
        temperature_two.setText(weatherBean.getResults().get(0).getWeather_data().get(2).getTemperature());

        date_three.setText(weatherBean.getResults().get(0).getWeather_data().get(3).getDate());
        weather_three.setText(weatherBean.getResults().get(0).getWeather_data().get(3).getWeather());
        wind_three.setText(weatherBean.getResults().get(0).getWeather_data().get(3).getWind());
        temperature_three.setText(weatherBean.getResults().get(0).getWeather_data().get(3).getTemperature());
        for (int i = 0; i < weatherBean.getResults().get(0).getWeather_data().size(); i++) {
            SPUtils.put(context, "date" + i, weatherBean.getResults().get(0).getWeather_data().get(i)
                    .getDate());
            SPUtils.put(context, "weather" + i,
                    weatherBean.getResults().get(0).getWeather_data().get(i).getWeather());
            SPUtils.put(context, "wind" + i, weatherBean.getResults().get(0).getWeather_data().get(i)
                    .getWind());
            SPUtils.put(context, "temperature" + i, weatherBean.getResults().get(0).getWeather_data()
                    .get(i).getTemperature());
        }
        if (!weatherBean.getResults().get(0).getWeather_data().get(0).getDate().equalsIgnoreCase("")) {
            for (int i = 0; i < 3; i++) {
                Log.d("allen",
                        "i=" + i + "getWeather="
                                + weatherBean.getResults().get(0).getWeather_data().get(i + 1).getWeather());
                pic[i].setImageBitmap(WeatherUtil.getWeatherImg(context,
                        weatherBean.getResults().get(0).getWeather_data().get(i + 1).getWeather()));
            }
        }

        //showBannerAD();
    }

    private void initLocation() {

        locationClient = new LocationClient(this);
        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);// 设置定位模式
        option.setOpenGps(false); // 是否打开GPS
        option.setCoorType("gcj02"); // 设置返回值的坐标类型。

        option.setProdName("LocationWeather"); // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(UPDATE_TIME); // 设置定时定位的时间间隔。单位毫秒
        locationClient.setLocOption(option);

        // 注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                // Receive Location
                StringBuffer sb = new StringBuffer(256);
                sb.append(location.getLongitude());
                sb.append(",");
                sb.append(location.getLatitude());

                Log.d("allen", sb.toString());
                if (!sb.toString().equals("")) {
                    sendWeatherRequest(sb.toString());
                    locationClient.stop();
                } else {
                    Toast.makeText(context, "位置获取失败，稍后再试！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        locationClient.start();
    }

    private void showLocation(Location location) {
        String currentPosition = "latitude is " + location.getLatitude() + "\n"
                + "longitude is " + location.getLongitude();
        android.util.Log.d("allen", currentPosition);
        // showLocaltionTV.setText(currentPosition);
    }

    private void sendWeatherRequest(String location) {
        if (location != null) {
            // 显示当前设备的位置信息

            String path = "http://api.map.baidu.com/telematics/v3/weather?location="
                    + location + "&output=json&ak=RUD7mk38fQdG0ZjcLCyigc2u";
            LogUtil.d("allen", path);
            // 发送天气请求
            asyncHttpClient.get(path, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                    // TODO Auto-generated method stub
                    String jsonStr = new String(arg2);

                    //weatherDataBeans.clear();
                    weatherBean = JsonUtils.getObject(jsonStr,WeatherBean.class);
                    if (weatherBean.getStatus().equals("success")){
                        currentCity = weatherBean.getResults().get(0).getCurrentCity();
                        SPUtils.put(context, "currentCity",
                                currentCity);

                    }

                    handler.sendEmptyMessage(UPDATA);

                    // showLocaltionTV.setText(jsonStr);

                }

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                      Throwable arg3) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "天气更新失败，稍后请重试！",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub
                    super.onFinish();

                    pullToRefreshScrollView.onRefreshComplete();

                }
            });

        }
    }

    private void initPullToRefreshScrollView() {
        String label = DateUtils.formatDateTime(getApplicationContext(),
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);

        // 这几个刷新Label的设置
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        // s.smoothScrollBy(0, 0);

        pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次更新：" + label);
        pullToRefreshScrollView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullToRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
                "正在加载");
        pullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
        ScrollView scrollView = pullToRefreshScrollView.getRefreshableView();
        // scrollView.scrollTo(0, 0);
        scrollView.smoothScrollTo(0, 0);
        // pullToRefreshScrollView.scrollTo(0, 0);
        // 上拉、下拉设定
        // pullToRefreshScrollView.setMode(Mode.PULL_FROM_START);

        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullToRefreshScrollView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ScrollView> refreshView) {
                        // TODO Auto-generated method stub
                        String label = DateUtils.formatDateTime(
                                getApplicationContext(),
                                System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME
                                        | DateUtils.FORMAT_SHOW_DATE
                                        | DateUtils.FORMAT_ABBREV_ALL);
                        initLocation();
                    }
                });

    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updataWeather();
                    break;
                case 2:
                    Log.d("allen",
                            "weatherDataBeans.size()=" + weatherBean.getResults().size());
                    for (int i = 0; i < 3; i++) {
                        Log.d("allen", "i=" + i + "getWeather="
                                + weatherBean.getResults().get(i + 1).getWeather_data());
                        pic[i].setImageBitmap(WeatherUtil.getWeatherImg(
                                context, weatherBean.getResults().get(i + 1).getWeather_data().get(i + 1).getWeather()
                        ));
                    }

                    break;

                default:
                    break;
            }
        }

        ;
    };


}
