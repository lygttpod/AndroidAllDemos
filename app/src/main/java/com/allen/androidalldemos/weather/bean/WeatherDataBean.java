package com.allen.androidalldemos.weather.bean;

public class WeatherDataBean {
	String date;
	String dayPictureUrl;
	String nightPictureUrl;
	String weather;
	String wind;
	String temperature;

	public WeatherDataBean(String date, String dayPictureUrl,
			String nightPictureUrl, String weather, String wind,
			String temperature) {
		super();
		this.date = date;
		this.dayPictureUrl = dayPictureUrl;
		this.nightPictureUrl = nightPictureUrl;
		this.weather = weather;
		this.wind = wind;
		this.temperature = temperature;
	}
	

	public WeatherDataBean(String date, String weather, String wind,
			String temperature) {
		super();
		this.date = date;
		this.weather = weather;
		this.wind = wind;
		this.temperature = temperature;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDayPictureUrl() {
		return dayPictureUrl;
	}

	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}

	public String getNightPictureUrl() {
		return nightPictureUrl;
	}

	public void setNightPictureUrl(String nightPictureUrl) {
		this.nightPictureUrl = nightPictureUrl;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

}
