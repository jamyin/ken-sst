package com.tianfang.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.admin.dto.AdminMenuDto;
import com.tianfang.admin.service.IAdminMenuService;
import com.tianfang.admin.utils.Const;
import com.tianfang.admin.utils.Page;
import com.tianfang.admin.utils.PageData;
import com.tianfang.common.constants.CacheKey;
import com.tianfang.common.constants.PlayerPosition;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.UUIDGenerator;


public class BaseController {
	
	protected Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	private IAdminMenuService menuService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 得到最左边的菜单列表
	 */
	
	@SuppressWarnings("unchecked")
	public List<AdminMenuDto> getLeftMenuListByCache(String userId){
		String keyCode = CacheKey.CACHE_ADMIN_LEFT_MENU+userId;
		List<AdminMenuDto> dataList = new ArrayList<AdminMenuDto>();
		if(null != redisTemplate.opsForValue().get(keyCode)){
			dataList = (List<AdminMenuDto>)redisTemplate.opsForValue().get(keyCode);
		}else{
			dataList = menuService.findMenuByAdminId(userId);
			redisTemplate.opsForValue().set(keyCode, dataList, 1, TimeUnit.HOURS);
		}
		return dataList;
	}
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("leftUrl",getRequest().getRequestURI());
		mv.addObject("pId",getRequest().getParameter("pId"));
		mv.addObject("positionMap", PlayerPosition.map);
		mv.addObject("formationMap", PlayerPosition.formationMap);
		mv.addObject("wwwdomain", PropertiesUtils.getProperty("wwwdomain"));
		String sessionId = this.getRequest().getSession().getId();
		mv.addObject("sessionId", sessionId);
		return mv;
	}
	
	public ModelAndView getModelAndView(String userId){
		ModelAndView mv = new ModelAndView();
		mv.addObject("leftUrl",getRequest().getRequestURI());
		mv.addObject("pId",getRequest().getParameter("pId"));
		mv.addObject("menuList",getLeftMenuListByCache(userId));
		mv.addObject("positionMap", PlayerPosition.map);
		mv.addObject("formationMap", PlayerPosition.formationMap);
		mv.addObject("statusMap", PlayerPosition.statusMap);
		mv.addObject("wwwdomain", PropertiesUtils.getProperty("wwwdomain"));
		String sessionId = this.getRequest().getSession().getId();
		mv.addObject("sessionId", sessionId);
		return mv;
	}
	
	public String getSessionUserId(){
		AdminDto admin = (AdminDto)getRequest().getSession().getAttribute(Const.SESSION_USER);
		if(admin==null){
			return "";
		}
		return admin.getId();
	}	
	
	public String getSessionUserName(){
		AdminDto admin = (AdminDto)getRequest().getSession().getAttribute(Const.SESSION_USER);
		if(null == admin){
			return "";
		}
		return admin.getAccount();
	}
	
	public AdminDto getSessionAdmin(){
		AdminDto admin = (AdminDto)getRequest().getSession().getAttribute(Const.SESSION_USER);
		return admin;
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		
		return UUIDGenerator.getUUID32Bit();
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
}