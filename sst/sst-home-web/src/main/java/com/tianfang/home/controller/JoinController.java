package com.tianfang.home.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.Response;
import com.tianfang.user.dto.UserApplyTeamDto;
import com.tianfang.user.dto.UserInfoDto;
import com.tianfang.user.service.IUserApplyTeamService;
import com.tianfang.user.service.IUserInfoService;

/**
 * 
	 * 此类描述的是：申请加入球队的接口
	 * @author: cwftalus@163.com
	 * @version: 2016年4月15日 下午1:16:39
 */
@Controller
public class JoinController extends BaseController{
	
	@Autowired
	private IUserApplyTeamService iUserApplyTeamService;
	
	@Autowired
	private IUserInfoService iUserInfoService;
	
	
	@RequestMapping(value = "join")
	public ModelAndView index(String userId,String teamId) {
		ModelAndView mv = getModelAndView();		

		UserInfoDto userInfo = iUserInfoService.getUserInfo(userId);
		
		
		if(!StringUtils.isEmpty(teamId)){
			mv.addObject("opType",DataStatus.ENABLED);
		}else{
			mv.addObject("opType",DataStatus.DISABLED);
		}
		mv.addObject("userId", userId);
		mv.addObject("teamId", teamId);
		mv.addObject("userInfo", userInfo);
		mv.setViewName("join");
		return mv;
	}
	
	@RequestMapping(value = "saveSubmit")
	@ResponseBody
	public Response<String> joinSubmit(UserInfoDto userInfo) {
		Response<String> response = new Response<String>();
	
		iUserInfoService.addUserInfo(userInfo);
		
		return response;
	}
	
	@RequestMapping(value = "joinSubmit")
	@ResponseBody
	public Response<String> joinSubmit(UserInfoDto userInfo,UserApplyTeamDto applyTeam) {
		Response<String> response = new Response<String>();
		

		iUserApplyTeamService.save(applyTeam);
		
		
		iUserInfoService.addUserInfo(userInfo);
		
		return response;
	}

}
