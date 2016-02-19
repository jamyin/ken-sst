package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author YIn
 * @time:2016年1月15日 上午11:55:23
 * @ClassName: CompetitionNewsDto
 * @Description: TODO
 * @
 */
@JsonIgnoreProperties({"createAdminId","createAdminName","updateAdminId","updateAdminName","lastUpdateTime","stat","createTimeStr","lastUpdateTimeStr"})
public class CompetitionNoticeDto implements Serializable{
	
	private static final long serialVersionUID = -2910543421008897096L;

	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
    private String compId;
	
	@Getter
	@Setter
    private String compName; //赛事公告名称
	
	@Getter
	@Setter
    private String title;
	
	/**
	 *  公告简介
	 */
	@Getter
	@Setter
	private String summary;
	
	@Getter
	@Setter
    private String content;
	
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

}