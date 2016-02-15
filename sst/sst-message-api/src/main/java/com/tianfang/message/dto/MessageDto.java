package com.tianfang.message.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class MessageDto {
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
    private String fromUserId;
	
	@Getter
	@Setter
    private String toUserId;
	
	@Getter
	@Setter
    private String content;
	
	@Getter
	@Setter
    private Integer msgState;
	
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

}