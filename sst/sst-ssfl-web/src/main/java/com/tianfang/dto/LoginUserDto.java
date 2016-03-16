package com.tianfang.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author YIn
 * @time:2015年11月19日 下午2:58:59
 * @Fields serialVersionUID : TODO
 */
public class LoginUserDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private String id;
	
	@Setter
	@Getter
	private String userAccount;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String oldPassword;   //修改前密码
	
	@Getter
	@Setter
	private String newPassword;	//修改后密码
	@Getter
	@Setter
	private String teamId;
}
