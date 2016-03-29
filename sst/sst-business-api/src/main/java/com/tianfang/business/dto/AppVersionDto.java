package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * 
	 * 此类描述的是：移动端分享和升级公用dto
	 * @author: cwftalus@163.com
	 * @version: 2016年3月29日 下午2:10:04
 */
public class AppVersionDto implements Serializable{

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private Integer versionNum;

	@Getter
	@Setter
    private String versionType;

	@Getter
	@Setter
    private String versionUrl;
	
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
    private String title;
	
	@Getter
	@Setter
    private Integer type;
	
	
	@Getter
	@Setter
    private String httpUrl;
	
	
	@Getter
	@Setter
    private String shareUrl;


}
