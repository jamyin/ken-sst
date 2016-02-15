package com.tianfang.common.dto;

import java.io.Serializable;
import java.util.HashMap;

public class WeatherMap implements Serializable{

	public static HashMap<String,Future> weatherMap = new HashMap<String,Future>();
	
	public HashMap<String, Future> getWeatherMap() {
		return weatherMap;
	}

	public void setLgMap(HashMap<String, Future> weatherMap) {
		this.weatherMap = weatherMap;
	}
}
