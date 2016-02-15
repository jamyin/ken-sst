package com.tianfang.home.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author YIn
 * @time:2015年11月24日 下午1:57:11
 * @Fields serialVersionUID : TODO
 */
public class UploadDto implements Serializable{

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private String url;//图片地址

}
