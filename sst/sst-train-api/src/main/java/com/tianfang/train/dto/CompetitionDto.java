package com.tianfang.train.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianfang.common.util.PropertiesUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties({"createAdminId","createAdminName","updateAdminId","updateAdminName","createTime","lastUpdateTime","stat","createTimeStr","lastUpdateTimeStr","startDateStr","endDateStr"})
public class CompetitionDto implements Serializable{
	
	private static final long serialVersionUID = 3503577572398481909L;

	@Getter
	@Setter
    private String id;
	
	@Getter
	@Setter
    private String title;
	
	@Getter
	@Setter
    private String content;
	
	@Getter
	@Setter
    private String icon;
	
	@Getter
	@Setter
    private Date startDate;
	
	@Getter
	@Setter
    private Date endDate;
	
	/**
	 * 报名是否截止(0-未截止,1已截止)
	 */
	@Getter
	@Setter
	private Integer isClosed;
	
	@Getter
	@Setter
    private Integer compType;
	
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
    private String startDateStr;    	//赛事开始时间显示
	
	@Getter
	@Setter
    private String endDateStr;		//赛事结束时间显示
	
	@Getter
	@Setter
    private Integer extra = Integer.valueOf(PropertiesUtils.getProperty("extra"));//0不需要额外参数  1 需要额外参数
	

	@Getter
	@Setter
	private Integer auditType;		// 审核状态(0-未审核,1-通过,2-拒绝)

	@Getter
	@Setter
	private String applyId;			// 审核id
}