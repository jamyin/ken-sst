package com.tianfang.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 轮播图Dto
 * @author YIn
 * @time:2016年1月15日 上午9:50:23
 * @ClassName: BannerDto
 * @Description: TODO
 * @
 */
public class BannerDto implements Serializable {

	private static final long serialVersionUID = 9198670212245015030L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
    private String title;

	@Getter
	@Setter
    private String img;

	@Getter
	@Setter
    private String url;

	@Getter
	@Setter
    private String createAdminId;

	@Getter
	@Setter
    private String createAdminName;

	@Getter
	@Setter
    private String updateAdminId;

	@Getter
	@Setter
    private String updateAdminName;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;
	
	@Getter
	@Setter
	private String createTimeStr; //页面显示创建时间
	
	@Getter
	@Setter
	private String lastUpdateTimeStr; //页面显示更新时间

	@Getter
	@Setter
    private String note;   //轮播图描述

}