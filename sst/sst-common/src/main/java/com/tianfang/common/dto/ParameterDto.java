package com.tianfang.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ParameterDto implements Serializable{
	
	@Getter
	@Setter
	private String spaceId;
	
	@Getter
	@Setter	
	private String date;
	
	@Getter
	@Setter
	private String orderTime;
	
	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private Integer orderStatus;
	
	@Getter
	@Setter
	private Integer week;
}
