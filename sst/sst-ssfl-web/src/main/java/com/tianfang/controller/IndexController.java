package com.tianfang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.dto.CompRound;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.service.INoticeService;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionNoticeDto;
import com.tianfang.train.dto.CompetitionRoundDto;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.service.ICompetitionMatchService;
import com.tianfang.train.service.ICompetitionNoticeService;
import com.tianfang.train.service.ICompetitionRoundService;
import com.tianfang.train.service.ICompetitionTeamService;

/**
 * @author wk.s
 *
 */
@Controller
public class IndexController extends BaseController{
	
	@Autowired
	private ICompetitionTeamService iCompetitionTeamService;
	
	/**
	 * 联赛公告展示信息
	 */
	@Autowired
	private ICompetitionNoticeService iCompetitionNoticeService;
	
	/**
	 * 官方信息
	 */
	@Autowired
	private INoticeService iNoticeService;
	
	@Autowired
	private ICompetitionRoundService roundSerivce;
	@Autowired
	private ICompetitionMatchService matchService;
	
	@RequestMapping(value="index")
	public ModelAndView index(){
		ModelAndView mv = getModelAndView();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("authInfo", getAuthInfo());
		map.put("raceNoteice", getRaceNotice());
		map.put("newRace",getnewRace());
		map.put("newRaceResult",getnewRaceResult());
		mv.addObject("dataMap", map);
		mv.setViewName("/index");
		return mv;
	}
	
	/**
	 * 获取滚动条相关的信息
	 */
	public void getRollCon(){
		
	}
	
	/**
	 * 获取官方展示信息
	 */
	public List<NoticeDto> getAuthInfo(){
		PageQuery query = new PageQuery(6); // 公告条数限制为6条
		NoticeDto dto = new NoticeDto();
		PageResult<NoticeDto> datas = iNoticeService.findNoticeViewByPage(dto, query);
		return datas.getResults();
	}
	
	/**
	 * 获取联赛公告展示信息
	 */
	public List<CompetitionNoticeDto> getRaceNotice(){
		PageQuery query = new PageQuery(6); // 公告条数限制为6条
		CompetitionNoticeDto dto = new CompetitionNoticeDto();
		PageResult<CompetitionNoticeDto> datas = iCompetitionNoticeService.findCompNoticeViewByPage(dto, query);
		return datas.getResults();
	}
	
	/**
	 * 获取最新赛程
	 */
	public List<CompetitionMatchDto> getnewRace(){
		int limit = 10;
		return matchService.findMatch(limit,0);//为开始的最新赛事
	}
	/*
	 * 最新赛果
	 */
	public List<CompetitionMatchDto> getnewRaceResult(){
		int limit = 10;
		return matchService.findMatch(limit,2);//为开始的最新赛事
	}
	/**
	 * 获取精彩图集
	 */
	public void getPictures(){
		
	}
	
	/**
	 * 获取视频展示信息
	 */
	public void getVideos(){
		
	}
	
	/**
	 * 获取积分展示信息
	 */
	public List<CompetitionTeamDto> getRecord(){
		PageQuery query = new PageQuery(10);
		CompetitionTeamDto dto = new CompetitionTeamDto();
		PageResult<CompetitionTeamDto> datas = iCompetitionTeamService.findCompetitionTeamByParam(dto, query);
		return datas.getResults();
	}
	

	
}
