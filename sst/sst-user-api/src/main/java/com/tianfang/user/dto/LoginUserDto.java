package com.tianfang.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: LoginUserDto </p>
 * <p>Description: 类描述:登录用户信息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月18日下午4:08:08
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class LoginUserDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

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
	 * 手机号码
	 */
	@Getter
	@Setter
    private String mobile;
	
	public LoginUserDto() {
		super();
	}

	public LoginUserDto(String id, String nickName, String headImg,
			String teamId, String mobile) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.headImg = headImg;
		this.teamId = teamId;
		this.mobile = mobile;
	}
}