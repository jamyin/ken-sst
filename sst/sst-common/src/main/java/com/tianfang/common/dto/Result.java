package com.tianfang.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Result implements Serializable{
	@Getter
	@Setter
	private List<Future> future;
}
