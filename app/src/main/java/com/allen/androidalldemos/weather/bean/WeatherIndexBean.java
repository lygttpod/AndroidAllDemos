package com.allen.androidalldemos.weather.bean;

public class WeatherIndexBean {

	String title;
	String zs;
	String tipt;
	String des;

	public WeatherIndexBean(String title, String zs, String tipt, String des) {
		super();
		this.title = title;
		this.zs = zs;
		this.tipt = tipt;
		this.des = des;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs;
	}

	public String getTipt() {
		return tipt;
	}

	public void setTipt(String tipt) {
		this.tipt = tipt;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
