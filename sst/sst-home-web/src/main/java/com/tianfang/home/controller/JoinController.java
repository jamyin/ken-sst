package com.tianfang.home.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.TeamPlayerPositionEnum;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.UserApplyTeamDto;
import com.tianfang.user.dto.UserInfoDto;
import com.tianfang.user.enums.AuditType;
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
	
	@Autowired
	private TeamController teamController;
	
	
	@RequestMapping(value = "join")
	public ModelAndView index(String userId,String teamId) {
		ModelAndView mv = getModelAndView();		

		UserInfoDto userInfo = iUserInfoService.getUserInfo(userId);
		
		
		if(!StringUtils.isEmpty(teamId)){
			mv.addObject("opType",DataStatus.ENABLED);
		}else{
			mv.addObject("opType",DataStatus.DISABLED);
		}
		
		if(StringUtils.isBlank(teamId)){
			teamId = null;
		}
		
		mv.addObject("userId", userId);
		mv.addObject("teamId", teamId);
		mv.addObject("userInfo", userInfo);
		mv.addObject("teamPlayer", TeamPlayerPositionEnum.values());
		
		mv.setViewName("join");
		return mv;
	}
	
	@RequestMapping(value = "saveSubmit")
	@ResponseBody
	public Response<String> joinSubmit(UserInfoDto userInfo) {
		Response<String> response = new Response<String>();
		
		UserInfoDto dto = iUserInfoService.getUserInfo(userInfo.getUserId());
		if(dto!=null){
			userInfo.setId(dto.getId());
			iUserInfoService.updateUserInfo(userInfo);
		}else{
			iUserInfoService.addUserInfo(userInfo);
		}
		return response;
	}
	
	@RequestMapping(value = "joinSubmit")
	@ResponseBody
	public Response<String> joinSubmit(UserInfoDto userInfo,UserApplyTeamDto applyTeam) {
		Response<String> response = new Response<String>();
		UserInfoDto dto = iUserInfoService.getUserInfo(userInfo.getUserId());
		if(dto!=null){
			userInfo.setId(dto.getId());
			iUserInfoService.updateUserInfo(userInfo);
		}else{
			iUserInfoService.addUserInfo(userInfo);
		}		
		if (teamController.checkUserApplyTeam(response, userInfo.getUserId(), applyTeam.getTeamId())){
			applyTeam.setStatus(AuditType.UNAUDIT.getIndex());
			iUserApplyTeamService.save(applyTeam);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setMessage("申请成功,请耐心等待队长审核...");
		}	
		return response;
	}

}
