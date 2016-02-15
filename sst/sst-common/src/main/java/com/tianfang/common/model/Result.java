package com.tianfang.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Result<T> implements Serializable{
	
	private static final long serialVersionUID = 304933694754396673L;

	public Result(T t) {
		this.result = t;
	}

	@Getter
	@Setter
	private T result;
	
}
