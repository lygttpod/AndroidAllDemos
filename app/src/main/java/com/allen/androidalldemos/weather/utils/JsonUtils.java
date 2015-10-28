package com.allen.androidalldemos.weather.utils;

import com.allen.androidalldemos.weather.bean.WeatherIndexBean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;



public class JsonUtils {
	ArrayList<WeatherIndexBean> weatherIndexBeans;
	public static JSONObject parseJsonObj(String jsonStr) throws Exception {

		JSONObject weatherJson = new JSONObject(jsonStr);
		String status = weatherJson.getString("status");

		JSONArray results = weatherJson.getJSONArray("results");
		JSONObject obj = results.getJSONObject(0);
		String currentCity = obj.getString("currentCity");
		String pm25 = obj.getString("pm25");

		System.out.println(currentCity + pm25);
		JSONArray indexArr = obj.getJSONArray("index");
		for (int i = 0; i < indexArr.length(); i++) {
			JSONObject index = indexArr.getJSONObject(i);
			
			System.out.println(index.getString("title"));
			System.out.println(index.getString("zs"));
			System.out.println(index.getString("tipt"));
			System.out.println(index.getString("des"));
		}
		JSONArray weather_dataArr = obj.getJSONArray("weather_data");
		for (int i = 0; i < weather_dataArr.length(); i++) {
			JSONObject weather_data = weather_dataArr.getJSONObject(i);
			
			System.out.println(weather_data.getString("date"));
			System.out.println(weather_data.getString("dayPictureUrl"));
			System.out.println(weather_data.getString("nightPictureUrl"));
			System.out.println(weather_data.getString("weather"));
			System.out.println(weather_data.getString("wind"));
			System.out.println(weather_data.getString("temperature"));
		}
		System.out.println(status);
		results.getString(0);
		

		return weatherJson;
	}

	public static JSONArray parseJsonArr(String jsonStr) throws Exception {

		JSONObject newsJson = new JSONObject(jsonStr);
		String success = newsJson.getString("success");
		JSONArray arr = newsJson.getJSONArray("yi18");

		for (int i = 0; i < arr.length(); i++) {

			JSONObject news = arr.getJSONObject(i);

			int id = news.getInt("id");
			String title = news.getString("title");

		}
		return arr;
	}

}
