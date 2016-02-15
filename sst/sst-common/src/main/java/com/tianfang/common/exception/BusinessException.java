package com.tianfang.common.exception;

import com.tianfang.common.constants.BusinessExceptionDiscription;

import lombok.Getter;
import lombok.Setter;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -6729001295655350041L;

	@Getter
	@Setter
	private BusinessExceptionDiscription discription;
	
	public BusinessException(BusinessExceptionDiscription discription){
		super(discription.getMessage());
		this.discription = discription;
	}
	
}
