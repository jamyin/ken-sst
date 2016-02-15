package com.tianfang.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
public class WeatherDto implements Serializable{
	@Getter
	@Setter
	private String resultcode;
	@Getter
	@Setter
	private String reason;
	
	@Getter
	@Setter
	private Result result;
	

}
