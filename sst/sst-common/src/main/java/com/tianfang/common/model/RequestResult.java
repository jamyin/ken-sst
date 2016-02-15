package com.tianfang.common.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RequestResult implements Serializable {

	private static final long serialVersionUID = 5862723191563657862L;

	public RequestResult(){
		
	}
	
	public RequestResult(boolean success, String message){
		this.success = success;
		this.message = message;
	}
	
	@Getter
	@Setter
	public boolean success;
	
	@Getter
	@Setter
	public String message;
	
}
