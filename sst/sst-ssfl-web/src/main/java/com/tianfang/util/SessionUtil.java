package com.tianfang.util;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import com.tianfang.common.constants.SessionConstants;
import com.tianfang.user.dto.LoginUserDto;
/**
 * @author YIn
 * @time:2015年11月19日 下午6:50:58
 * @Fields serialVersionUID : TODO
 */
public class SessionUtil implements Serializable{
	private static final long serialVersionUID = 1L;
	public static LoginUserDto getLoginSession(HttpSession session){
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			return (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
		}else{
			return null;
		}
	}
}
