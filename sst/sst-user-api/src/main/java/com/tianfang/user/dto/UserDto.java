package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: UserDto </p>
 * <p>Description: 类描述:用户对象</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月12日下午3:51:37
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class UserDto implements Serializable{

	private static final long serialVersionUID = -1175162308240625934L;
	
	@Getter
	@Setter
    private String id;

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
	 * 球队id
	 */
	@Getter
	@Setter
    private String teamId;	

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
	 * 市
	 */
	@Getter
	@Setter
	private String area;
	
	/**
	 * 对应省份-页面显示
	 */
	@Getter
	@Setter
	private String provinceStr;
	
	/**
	 * 用户所在地 区域 -页面显示
	 */
	@Getter
	@Setter
	private String locationStr;
	
	/**
	 * 市  -页面显示
	 */
	@Getter
	@Setter
	private String areaStr;
	
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
	 * 密码
	 */
	@Getter
	@Setter
    private String password;

	/**
	 * 手机号码
	 */
	@Getter
	@Setter
    private String mobile;
	
	/**
	 * 邮箱
	 */
	@Getter
	@Setter
	private String email;
	
	
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
	 * 二维码生成路径
	 */
	@Getter
	@Setter
	private String qrcode;

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
	
	/**
	 * 最后登录时间
	 */
	@Getter
	@Setter
	private Date lastLoginTime;
	
	@Setter
    @Getter
    private String userAccount; //登录时的用户账号
	
	/**
	 * 中文名
	 */
	@Setter
    @Getter
	private String cname;

	/**
	 * 英文名
	 */
	@Setter
    @Getter
    private String ename;

	/**
	 * 证件图片
	 */
	@Setter
    @Getter
    private String papersPic;

	/**
	 * 在团队中的位置，比如左边锋
	 */
	@Setter
    @Getter
    private String position;

	/**
	 * 缩略图url
	 */
	@Setter
    @Getter
    private String microPic;

	/**
	 * 是否可见（0：不可见，1：可见）
	 */
	@Setter
    @Getter
    private Integer visibleStat;

	/**
	 * 
	 */
	@Setter
    @Getter
    private Integer audit;

	/**
	 * QQ第三方登录ID
	 */
	@Setter
    @Getter
    private String qq;

	/**
	 * 微信第三方登录ID                 
	 */
	@Setter
    @Getter
    private String weixin;

	/**
	 * 新浪第三方登录ID
	 */
	@Setter
    @Getter
    private String sina;

	/**
	 * 座机号码
	 */
	@Setter
    @Getter
    private String telephone;

	/**
	 * 邮编
	 */
	@Setter
    @Getter
    private String postcode;

	/**
	 * 培训主讲人(0:不是;1:是培训主讲人)
	 */
	@Setter
    @Getter
    private Integer lecturer;

	/**
	 * 自我介绍
	 */
	@Setter
    @Getter
    private String description;
	
	@Getter
	@Setter
    private String createTimeStr;
	
	/**
	 * 最后登录时间-页面显示
	 */
	@Getter
	@Setter
	private String lastLoginTimeStr;
	
	/**
	 * 球队Name
	 */
	@Getter
	@Setter
    private String teamName;	
	
	/**
	 * 球队标志
	 */
	@Getter
	@Setter
    private String teamIcon;
}