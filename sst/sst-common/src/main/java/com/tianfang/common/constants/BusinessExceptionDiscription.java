package com.tianfang.common.constants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public enum BusinessExceptionDiscription {

	USER_ERROR_001("001", "用户测试异常");
	
	@Getter
	@Setter
	private String code;
	
	@Getter
	@Setter
	private String message;
	
	BusinessExceptionDiscription(String code, String message){
		this.code = code;
		this.message = message;
	}
}
