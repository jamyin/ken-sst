package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Yin
 *
 */
public class UserInfoDto implements Serializable {

	private static final long serialVersionUID = -1153807221715865713L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String userId;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private Integer age;

	@Getter
	@Setter
	private String cardNo;

	@Getter
	@Setter
	private String mobile;

	@Getter
	@Setter
	private String school;

	@Getter
	@Setter
	private String studentNo; // 学籍号

	@Getter
	@Setter
	private String photo;

	/**
	 * 创建时间
	 */
	@Getter
	@Setter
	private Date createTime;

	/**
	 * 最后更新时间
	 */
	@Getter
	@Setter
	private Date lastUpdateTime;

	/**
	 * 数据状态
	 */
	@Getter
	@Setter
	private Integer stat;

	@Getter
	@Setter
	private String nickName; // 用户昵称

	@Getter
	@Setter
	private String createTimeStr; // 页面显示创建时间

	@Getter
	@Setter
	private String lastUpdateTimeStr; // 页面显示最后更新时间

	@Getter
	@Setter
	private Integer gender; // 姓别(1:男;2:女)

	@Getter
	@Setter
	private Integer chenyiCup; // 1 是 0 否

	@Getter
	@Setter
	private Integer joinUnion; // 1 是 0 否

	@Getter
	@Setter
	private String localtion;

	@Getter
	@Setter
	private String playPosition;

	@Getter
	@Setter
	private String workLoca;

}