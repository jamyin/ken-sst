package com.tianfang.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.constants.TeamPlayerPositionEnum;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.service.ICompetitionMatchService;
import com.tianfang.train.service.ITeamPlayerService;
import com.tianfang.train.service.ITeamService;

@Controller
@RequestMapping(value = "teamInfo")
public class TeamInfoController extends BaseController{
	
	protected static Log logger = LogFactory.getLog(TeamInfoController.class);
	
	@Autowired
	private ITeamService iTeamService;
	
	@Autowired
	private ITeamPlayerService iTeamPlayerService;
	
	@Autowired
	private ICompetitionMatchService iCompetitionMatchService;
	
	/**
	 * 获取球队信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getTeam")
	public ModelAndView getTeam(String id) {
		ModelAndView mv = this.getModelAndView();
		TeamDto teamDto = iTeamService.getTeamById(id);
		mv.addObject("teamDto", teamDto);
		mv.setViewName("/team/team-introduction");
		return mv;		
	}
	
	/**
	 * 获取球队成员
	 * @param id
	 * @return
	 */
	@RequestMapping("/getTeamPlayer")
	public ModelAndView getTeamPlayer(String id) {
		ModelAndView mv = this.getModelAndView();
		TeamDto teamDto = iTeamService.getTeamById(id);
		TeamPlayerDto dto = new TeamPlayerDto();
		dto.setTeamId(id);
		List<TeamPlayerDto> teamPlayerDtos= iTeamPlayerService.findTeamPlayerByParam(dto);		
		for (TeamPlayerDto teamPlayerDto : teamPlayerDtos) {
			teamPlayerDto.setPositionStr(TeamPlayerPositionEnum.getName(teamPlayerDto.getPosition()));
		}
		mv.addObject("teamDto", teamDto);
		mv.addObject("teamPlayerDtos", teamPlayerDtos);
		mv.setViewName("/team/team-member");
		return mv;
	}
	
	@RequestMapping("/getTeamHerald")
	public ModelAndView getTeamHerald(String id,PageQuery query) {
		ModelAndView mv = this.getModelAndView();
		PageResult<CompetitionMatchDto> competitionMatchDtos = iCompetitionMatchService.selectCompetitionMatchByTeamId(id,query);
		TeamDto teamDto = iTeamService.getTeamById(id);
		mv.addObject("pageList", competitionMatchDtos);
		mv.addObject("teamDto", teamDto);
		mv.setViewName("/team/team-herald");
		return mv;
	}
}
