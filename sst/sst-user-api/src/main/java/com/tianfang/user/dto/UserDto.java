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
    private String headImg;

	/**
	 * 球队id
	 */
	@Getter
	@Setter
    private String teamId;	

	/**
	 * 性别
	 */
	@Getter
	@Setter
    private Integer sex;

	/**
	 * 地区
	 */
	@Getter
	@Setter
    private String address;

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
    private String pwd;

	/**
	 * 聊天账号
	 */
	@Getter
	@Setter
    private String userAccount;

	/**
	 * 手机号码
	 */
	@Getter
	@Setter
    private String mobile;
	
	/**
	 * 用户类型(0-普通用户,1-球员,2-队长,3-教练)
	 */
	@Getter
	@Setter
	private Integer type; 

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
}