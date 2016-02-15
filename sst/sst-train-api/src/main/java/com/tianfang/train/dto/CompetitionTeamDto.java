package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: CompetitionTeamDto </p>
 * <p>Description: 类描述:赛事参加球队</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月28日上午9:48:48
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"createTime","lastUpdateTime","stat","orderByClause"})
public class CompetitionTeamDto implements Serializable{

	private static final long serialVersionUID = -8390489930314521403L;

	@Getter
	@Setter
	private String id;

	/**
	 * 赛事id
	 */
	@Getter
	@Setter
    private String compId;
	
	/**
	 * 球队id
	 */
	@Getter
	@Setter
    private String teamId;
	
	/**
	 * 胜场次
	 */
	@Getter
	@Setter
	private Integer win;

	/**
	 * 平场次
	 */
	@Getter
	@Setter
    private Integer draw;

	/**
	 * 负场次
	 */
	@Getter
	@Setter
    private Integer lose;

	/**
	 * 进球数
	 */
	@Getter
	@Setter
    private Integer goalIn;

	/**
	 * 失球数
	 */
	@Getter
	@Setter
    private Integer goalOut;

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
	 * 赛事名称
	 */
	@Getter
	@Setter
	private String compName;
	
	/**
	 * 赛事开始时间
	 */
	@Getter
	@Setter
	private Date startDate;
	
	/**
	 * 赛事结束时间
	 */
	@Getter
	@Setter
	private Date endDate;
	
	/**
	 * 报名是否截止(0-未截止,1已截止)
	 */
	@Getter
	@Setter
	private Integer isClosed;
	
	/**
	 * 赛事状态
	 */
	@Getter
	@Setter
	private Integer compType;
	
	/**
	 * 球队名称
	 */
	@Getter
	@Setter
	private String teamName;
	
	/**
	 * 球队标识
	 */
	@Getter
	@Setter
	private String teamIcon;
	
	/**
	 * 排序,分页字段
	 */
	@Getter
	@Setter
	private String orderByClause;
}