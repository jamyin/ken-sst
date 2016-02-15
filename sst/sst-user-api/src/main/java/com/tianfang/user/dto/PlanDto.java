package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: PlanDto </p>
 * <p>Description: 类描述:用户训练计划</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月22日上午9:43:57
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"createTime","lastUpdateTime","stat","startTime","endTime","planTimeStr","year"})
public class PlanDto implements Serializable{

	private static final long serialVersionUID = -3777878396174724683L;

	@Getter
	@Setter
	private String id;

	/**
	 * 用户id
	 */
	@Getter
	@Setter
    private String userId;

	/**
	 * 用户名称
	 */
	@Getter
	@Setter
    private String userName;

	/**
	 * 标题
	 */
	@Getter
	@Setter
    private String title;

	/**
	 * 提醒时间
	 */
	@Getter
	@Setter
    private Date planTime;
	
	/**
	 * 提醒时间(格式:yyyy-MM-dd HH:mm 用于提交数据)
	 */
	@Getter
	@Setter
	private String planTimeStr;
	
	/**
	 * 是否完成(0-未完成,1-已完成)
	 */
	@Getter
	@Setter
	private Integer isFinish;
	
	/**
	 * 是否定时提醒(0-否,1-是)
	 */
	@Getter
	@Setter
	private Integer isRemind;

	/**
	 * 内容
	 */
	@Getter
	@Setter
    private String content;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;
	
	/**
	 * 提醒的开始时间(查询参数)
	 */
	@Getter
	@Setter
	private Date startTime;
	
	/**
	 * 提醒的结束时间(查询参数)
	 */
	@Getter
	@Setter
	private Date endTime;
	
	/**
	 * 查询该年的数据(查询参数)
	 */
	@Getter
	@Setter
	private String year;
}