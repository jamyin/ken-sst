package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author YIn
 * @time:2016年1月15日 上午11:56:47
 * @ClassName: CompetitionMatchDto
 * @Description: TODO
 * @
 */
@JsonIgnoreProperties({"createAdminId","createAdminName","createTime","lastUpdateTime","stat","createTimeStr","lastUpdateTimeStr","matchDateStr","matchTimeStr"})
public class CompetitionMatchDto implements Serializable{
	
	private static final long serialVersionUID = -8883966920307608799L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String compId;

	@Getter
	@Setter
	private String compName;

	@Getter
	@Setter
	private String croundId;

	@Getter
	@Setter
	private String croundName;

	@Getter
	@Setter
	private String homeTeamId;

	@Getter
	@Setter
	private String homeTeamIcon;

	@Getter
	@Setter
	private String homeTeamName;

	@Getter
	@Setter
	private String visitingTeamId;

	@Getter
	@Setter
	private String visitingTeamIcon;

	@Getter
	@Setter
	private String visitingTeamName;

	/**
	 * NOT(0,"未开始"), ING(1,"进行中"), OVER(2, "已结束");
	 */
	@Getter
	@Setter
	private Integer matchType;
	
	@Getter
	@Setter
	private Date matchTime;

	@Getter
	@Setter
	private Integer homeScore;

	@Getter
	@Setter
	private Integer visitingScore;

	@Getter
	@Setter
	private String address;
	
	/**
	 * 比赛几人制(1-11人制,2-7人制,3-5人制)
	 */
	@Getter
	@Setter
	private Integer peopleType;

	@Getter
	@Setter
	private String createAdminId;

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
	
	@Getter
	@Setter
	private String createTimeStr; //页面显示创建时间
	
	@Getter
	@Setter
	private String lastUpdateTimeStr; //页面显示更新时间
	
	/**
	 * 比赛日期(yyyy-MM-dd)
	 */
	@Getter
	@Setter
	private String matchDateStr;
	
	/**
	 * 比赛时间(HH:mm)
	 */
	@Getter
	@Setter
	private String matchTimeStr;
}