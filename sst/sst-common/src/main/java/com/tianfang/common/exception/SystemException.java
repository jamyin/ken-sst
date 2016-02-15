package com.tianfang.common.exception;

import lombok.Getter;
import lombok.Setter;

public class SystemException extends RuntimeException {
	
	private static final long serialVersionUID = 8248605426118786012L;

	@Getter
	@Setter
	private String message;
	
	public SystemException(String message){
		super(message);
		this.message = message;
	}
	
	public SystemException(Throwable cause) {  
        super(cause);  
      this.message = cause.getMessage();
    }
	
	public SystemException(String message, Throwable cause) {  
        super(message, cause);  
      this.message = message;
    } 
}
