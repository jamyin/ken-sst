package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TeamPlayerDto implements Serializable{

	private static final long serialVersionUID = 6770591068459495730L;

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
	 * 球队标识
	 */
	@Getter
	@Setter
    private String teamIcon;

	/**
	 * 名字
	 */
	@Getter
	@Setter
    private String name;

	/**
	 * 描述
	 */
	@Getter
	@Setter
    private String summary;

	/**
	 * 球号
	 */
	@Getter
	@Setter
    private Integer num;

	/**
	 * 位置
	 */
	@Getter
	@Setter
    private Integer position;

	/**
	 * 头像
	 */
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
    private Integer stat;
	
	
	@Getter
	@Setter
	private String createTimeStr; //页面显示创建时间
	
	@Getter
	@Setter
	private String lastUpdateTimeStr; //页面显示更新时间
}