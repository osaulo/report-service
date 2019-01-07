package com.report.dto;

public class City {

	private String cityName;
	
	private String state;
	
	public City() {
		// TODO Auto-generated constructor stub
	}

	public City(String cityName, String state) {
		this();
		this.cityName = cityName;
		this.state = state;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

}
