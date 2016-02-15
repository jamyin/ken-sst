package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: TeamResultDto </p>
 * <p>Description: 类描述:球队主要成绩</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月27日上午10:23:07
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class TeamResultDto implements Serializable{

	private static final long serialVersionUID = -8407708443261354621L;
	
	@Getter
	@Setter
	private String id;

	/**
	 * 球队id
	 */
	@Getter
	@Setter
    private String teamId;
	
	/**
	 * 球队名称
	 */
	@Getter
	@Setter
	private String teamName;

	/**
	 * 年份
	 */
	@Getter
	@Setter
    private String year;

	/**
	 * 比赛名称
	 */
	@Getter
	@Setter
    private String gameName;

	/**
	 * 成绩
	 */
	@Getter
	@Setter
    private String result;

	/**
	 * 创建人id
	 */
	@Getter
	@Setter
    private String createUserId;

	/**
	 * 创建人姓名
	 */
	@Getter
	@Setter
    private String createUserName;

	/**
	 * 更新人id
	 */
	@Getter
	@Setter
    private String updateUserId;

	/**
	 * 更新人姓名
	 */
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
}