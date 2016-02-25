package com.tianfang.message.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: ActivityDto </p>
 * <p>Description: 类描述:活动</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月29日上午11:13:26
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"createAdminId", "createAdminName", "createTime", "lastUpdateTime", "stat"})
public class ActivityDto implements Serializable{

	private static final long serialVersionUID = -6789126849180106526L;
	
	@Getter
	@Setter
	private String id;

	/**
	 * 标题
	 */
	@Getter
	@Setter
    private String title;

	/**
	 * 活动概述
	 */
	@Getter
	@Setter
    private String summary;
	
	/**
	 * 缩略图
	 */
	@Getter
	@Setter
	private String pic;

	/**
	 * 活动状态(0-即将开始,1-进行中)
	 */
	@Getter
	@Setter
    private Integer status;

	/**
	 * 内容
	 */
	@Getter
	@Setter
    private String content;

	/**
	 * 活动地点
	 */
	@Getter
	@Setter
    private String address;

	/**
	 * 活动日期(yyyy-mm-dd)
	 */
	@Getter
	@Setter
    private String activityDate;

	/**
	 * 活动开始时间(hh:mm)
	 */
	@Getter
	@Setter
    private String startTime;

	/**
	 * 活动结束时间(hh:mm)
	 */
	@Getter
	@Setter
    private String endTime;
	
	/**
     * 创建者id
     */
    @Getter
    @Setter
    private String createAdminId;

    /**
     * 创建者
     */
    @Getter
    @Setter
    private String createAdminName;

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