package com.tianfang.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class DateDto implements Serializable{
	@Getter
	@Setter
	private String day;
	
	@Getter
	@Setter
	private String week;
	
	@Getter
	@Setter
	private String date;
	
	@Getter
	@Setter
	private String nowDay;
	
	@Getter
	@Setter
	private Short dayForWeek;
}
