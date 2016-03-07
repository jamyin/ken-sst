package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: UserApplyTeamDto </p>
 * <p>Description: 类描述:用户申请球队记录表</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日上午10:03:36
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"lastUpdateTime","stat"})
public class UserApplyTeamDto implements Serializable{

	private static final long serialVersionUID = 5185425965369123009L;

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
	 * 用户id
	 */
	@Getter
	@Setter
    private String userId;
	
	/**
	 * 用户昵称
	 */
	@Getter
	@Setter
	private String nickName;

	/**
	 * 用户头像
	 */
	@Getter
	@Setter
	private String pic;
	
	/**
	 * 性别(1.男  2.女)
	 */
	@Getter
	@Setter
    private Integer gender;

	/**
	 * 对应省份
	 */
	@Getter
	@Setter
    private String province;
	
	/**
	 * 用户所在地 区域
	 */
	@Getter
	@Setter
	private String location;
	
	/**
	 * 地址详情
	 */
	@Getter
	@Setter
	private String detailedAddress;

	/**
	 * 生日
	 */
	@Getter
	@Setter
    private String birthday;
	
	/**
	 * 手机号码
	 */
	@Getter
	@Setter
    private String mobile;
	
	/**
	 * 用户类型(1，普通会员；2，教练；3，队长)
	 */
	@Getter
	@Setter
	private Integer utype; 
	
	/**
	 * 教练级别0：顶级教练，1：教练 2  vip用户可查看课件
	 */
	@Getter
	@Setter
	private Integer trainerLevel;
	
	/**
	 * 申请状态
	 */
	@Getter
	@Setter
    private Integer status;

	/**
	 * 申请理由
	 */
	@Getter
	@Setter
    private String reason; 

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