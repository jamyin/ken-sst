package com.tianfang.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ITeamService;

@Controller
@RequestMapping(value = "teamInfo")
public class TeamInfoController extends BaseController{
	
	protected static Log logger = LogFactory.getLog(TeamInfoController.class);
	
	@Autowired
	private ITeamService iTeamService;
	
	@RequestMapping("/getTeam")
	public ModelAndView getTeam(String id) {
		ModelAndView mv = this.getModelAndView();
		TeamDto teamDto = iTeamService.getTeamById(id);
		mv.addObject("teamDto", teamDto);
		mv.setViewName("/team-introduction.htm");
		return mv;
		
	}
	

}
