package com.tianfang.admin.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MenuDto implements Serializable{
	
	@Setter
	@Getter
	private HomeMenuDto homeMenuDto;
	
	@Setter
	@Getter
	private List<HomeMenuDto> secondList;
}
