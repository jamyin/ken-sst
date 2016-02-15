package com.tianfang.common.model;

import java.io.Serializable;

import com.tianfang.common.constants.DataStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 7782981616682775073L;

	public Response(){
		
	}
	
	public Response(int status, String message,T data){
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	@Getter
	@Setter
	public int status = DataStatus.HTTP_SUCCESS;
	
	@Getter
	@Setter
	public String message;
	
	@Getter
	@Setter
	public T data;
	
}
