package com.tianfang.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Future implements Serializable{
	//{"weatherinfo":{"city":"上海","cityid":"101020100","temp1":"22℃","temp2":"11℃","weather":"小雨",
	//"img1":"d7.gif","img2":"n7.gif","ptime":"08:00"}}
	
	@Getter
	@Setter
	public String temperature;
	
	@Getter
	@Setter
	private String weather;
	
	
	@Getter
	@Setter
	private weather_id weather_id;
	
	@Getter
	@Setter
	private String wind;
	
	@Getter
	@Setter
	private String week;
	
	@Getter
	@Setter
	private String date;
}
