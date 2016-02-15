package com.tianfang.message.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class NoticeDto {
	
	@Getter
	@Setter
    private String id;
	
	@Getter
	@Setter
    private String title;
	
	@Getter
	@Setter
    private String createUserId;
	
	@Getter
	@Setter
    private String createUserName;
	
	@Getter
	@Setter
    private String content;
	
	@Getter
	@Setter
    private String updateUserId;
	
	@Getter
	@Setter
    private String updateUserName;  
	
	@Getter
	@Setter
    private String groupId;
	
	@Getter
	@Setter
    private String groupName;
	
	@Getter
	@Setter
    private String pic;
	
	@Getter
	@Setter
    private Date createTime;
	
	@Getter
	@Setter
    private Date lastUpdateTime;
	
	@Getter
	@Setter
	private String createTimeStr; //页面显示创建时间
	
	@Getter
	@Setter
	private String lastUpdateTimeStr; //页面显示更新时间
	
	@Getter
	@Setter
    private Integer stat;
	
	@Getter
	@Setter
	private Integer ReadStat;   //阅读状态:0-未读;1-已读
	
	@Getter
	@Setter
    private String [] userIds;   //接收公告者集合

}