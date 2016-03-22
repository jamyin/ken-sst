package com.tianfang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Objects;
import com.sun.mail.imap.protocol.Item;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.Response;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ITeamService;

/**
 * 
 * 此类描述的是：比赛报名
 * 
 * @author: cwftalus@163.com
 * @version: 2016年3月18日 下午4:13:07
 */
@Controller
@RequestMapping(value = ("enroll"))
public class EnrollController extends BaseController {

	@Autowired
	private ITeamService iTeamService;
	
	@RequestMapping(value = ("help"))
	public ModelAndView index() {
		ModelAndView mv = getModelAndView();

		mv.setViewName("/enroll/enroll_help");
		return mv;
	}

	@RequestMapping(value = ("form"))
	public ModelAndView form() {
		ModelAndView mv = getModelAndView();

		mv.setViewName("/enroll/enroll_form");
		return mv;
	}
	
	@RequestMapping(value = ("submit"))
	@ResponseBody
	public Response<String> submit(TeamDto teamDto) {
		Response<String> data = new Response<String>();

		int result = iTeamService.addTeam(teamDto);

		if(Objects.equal(result, DataStatus.DISABLED)){//0保存不成功
			data.setMessage("数据保存失败,请检查数据是否完整");
			data.setStatus(DataStatus.HTTP_FAILE);
		}

		return data;
	}

}
