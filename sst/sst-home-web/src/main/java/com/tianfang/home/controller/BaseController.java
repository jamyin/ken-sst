package com.tianfang.home.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.SessionConstants;
import com.tianfang.home.utils.SessionUtil;
import com.tianfang.user.dto.LoginUserDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.IUserService;

public class BaseController {
	protected Logger logger = Logger.getLogger(BaseController.class);
	public static final String SST_USER = "SSTUSER";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private IUserService iUserService;


	/**
	 * 获取缓存中用户信息
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2016年1月18日下午4:45:10
	 * @throws Exception 
	 */
	public UserDto getUserByCache(String userId){	
		String keyCode = SST_USER+userId;
		UserDto dto = null;
		if(null != redisTemplate.opsForValue().get(keyCode)){
			dto = (UserDto)redisTemplate.opsForValue().get(keyCode);
		}else{
			dto = iUserService.getUserById(userId);
			redisTemplate.opsForValue().set(keyCode, dto, 1, TimeUnit.HOURS);
		}
		return dto;
	}

	public ModelAndView getModelAndView(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", getUserAccountByUserId());
		return mv;
	}

	public ModelAndView getUserModelAndView(String userId) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", getUserByCache(userId));
		return mv;
	}

	public LoginUserDto getUserAccountByUserId(){
		LoginUserDto user = (LoginUserDto)getRequest().getSession().getAttribute(SessionConstants.LOGIN_USER_INFO);
		if(user==null){
			return null;
		}
		return user;		
	}

	public String getSessionUserId(){
		LoginUserDto user = (LoginUserDto)getRequest().getSession().getAttribute(SessionConstants.LOGIN_USER_INFO);
		if(user==null){
			return null;
		}
		return user.getId();
	}
	
	public LoginUserDto getLoginUser(){
		return SessionUtil.getLoginSession(getRequest().getSession());
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
}