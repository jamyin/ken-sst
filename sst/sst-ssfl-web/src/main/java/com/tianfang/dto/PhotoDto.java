package com.tianfang.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class PhotoDto implements Serializable{
	
	@Getter
	@Setter
	private String alt;
	
	@Getter
	@Setter
	private String pid;
	
	@Getter
	@Setter
	private String src;
	
	@Getter
	@Setter
	private String thumb;

}
