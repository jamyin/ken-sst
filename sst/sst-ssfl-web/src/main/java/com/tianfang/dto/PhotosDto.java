package com.tianfang.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PhotosDto implements Serializable {
	
	@Getter
	@Setter
	public String title;
	
	@Getter
	@Setter
	public String id;
	
	@Getter
	@Setter
	public Integer start = 0;
	
	@Getter
	@Setter
	public List<PhotoDto> data;
}
