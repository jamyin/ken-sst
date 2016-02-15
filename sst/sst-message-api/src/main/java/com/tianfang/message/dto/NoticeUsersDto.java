package com.tianfang.message.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class NoticeUsersDto {
	
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
    private String noticeId;
	
	@Getter
	@Setter
    private String userId;
	
	@Getter
	@Setter
    private String [] userIds;   //接收公告者集合
	
	@Getter
	@Setter
    private Integer readstat;   //消息阅读状态: 是否阅读(0:未读;1:已读)
	
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