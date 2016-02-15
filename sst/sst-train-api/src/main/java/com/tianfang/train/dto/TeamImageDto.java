package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TeamImageDto implements Serializable {
	private static final long serialVersionUID = 4359295263377728811L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String teamId;

	@Getter
	@Setter
	private String title;

	@Getter
	@Setter
	private String imgUrl;
	
	@Getter
	@Setter
	private String imgUrls;      //前台传过来的图片集合字符串

	@Getter
	@Setter
	private String note;

	@Getter
	@Setter
	private String createUserId;

	@Getter
	@Setter
	private String createUserName;

	@Getter
	@Setter
	private String updateUserId;

	@Getter
	@Setter
	private String updateUserName;

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
}