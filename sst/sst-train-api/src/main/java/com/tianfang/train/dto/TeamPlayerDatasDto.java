package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: TeamPlayerDatasDto </p>
 * <p>Description: 类描述:赛事球员数据</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月27日下午2:23:17
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"createAdminId","createAdminName","createTime","lastUpdateTime","stat"})
public class TeamPlayerDatasDto implements Serializable{

	private static final long serialVersionUID = -4392763497840191275L;
	
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
	 * 赛事名称
	 */
	@Getter
	@Setter
	private String compName;

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
	 * 球队标识
	 */
	@Getter
	@Setter
	private String teamIcon;

	/**
	 * 球员id
	 */
	@Getter
	@Setter
    private String playerId;

	/**
	 * 球员名称
	 */
	@Getter
	@Setter
    private String playerName;

	/**
	 * 进球数
	 */
	@Getter
	@Setter
    private Integer num;

	/**
	 * 黄牌
	 */
	@Getter
	@Setter
    private Integer yellow;

	/**
	 * 红牌
	 */
	@Getter
	@Setter
    private Integer red;

	/**
	 * 创建者id
	 */
	@Getter
	@Setter
    private String createAdminId;

	/**
	 * 创建者姓名
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

	//球员位置
	@Getter
	@Setter
    private Integer position;

	//出场次数
	@Getter
	@Setter
	private Integer times;
	
	//出场时间
	@Getter
	@Setter
	private Integer playTimes;
	
	//点球数
	@Getter
	@Setter
	private Integer points;
	
	//助攻数
	@Getter
	@Setter
	private Integer assists;
}