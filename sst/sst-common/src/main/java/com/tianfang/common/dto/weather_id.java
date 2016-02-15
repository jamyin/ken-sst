package com.tianfang.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class weather_id implements Serializable{
	@Setter
	@Getter
	private Integer fa;

	@Setter
	@Getter
	private Integer fb;
}
