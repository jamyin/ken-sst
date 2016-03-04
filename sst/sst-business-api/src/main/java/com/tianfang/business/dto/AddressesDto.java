package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author YIn
 * @time:2016年3月4日 上午10:18:02
 * @ClassName: AddressDto
 * @
 */
public class AddressesDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter	
    private Integer id;
	
	@Getter
	@Setter
    private String parentId;
	
	@Getter
	@Setter
    private String name;
	
	@Getter
	@Setter
    private Integer level;
	
	@Getter
	@Setter
    private Date createTime;
	
	@Getter
	@Setter
    private Date lastUpdateTime;
	
	@Getter
	@Setter
    private Integer stat;
	
}