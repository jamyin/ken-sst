package com.tianfang.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.DateUtils;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.service.ICompetitionMatchService;
import com.tianfang.train.service.ICompetitionTeamService;
import com.tianfang.util.SSFLDatas;

/**
 * @author YIn
 * @time:2016年3月23日 上午9:16:51
 * @ClassName: CompetitionController
 * @Description: 赛事Controller
 * @
 */
@Controller
@RequestMapping(value = "competition")
public class CompetitionMatchController extends BaseController {

	@Autowired
	private ICompetitionMatchService competitionMatchService;
	
	@Autowired
	private ICompetitionTeamService competitionTeamService;
	
	/**
	 * 赛事预告
	 * @author YIn
	 * @time:2016年3月23日 上午9:17:06
	 * @return
	 */
	@RequestMapping(value = "/match/trailer")   
	public ModelAndView trailer(PageQuery page) {
		//赛事预告
		ModelAndView mv = getModelAndView();
		CompetitionMatchDto competitionMatchDto = new CompetitionMatchDto();
		competitionMatchDto.setMatchType(0);   //表示未开始
		page.setPageSize(5);
		PageResult<CompetitionMatchDto> pageList = competitionMatchService.findCompetitionMatchViewByPage(competitionMatchDto ,page);
		if(pageList.getResults() != null && pageList.getResults().size() > 0){
			for(CompetitionMatchDto dto: pageList.getResults()){
				dto.setMatchTimeString(DateUtils.format(dto.getMatchTime(), DateUtils.YMD_DASH_DATE_MINUTES));
			}
		}
		
		//积分榜
		CompetitionTeamDto dto = new CompetitionTeamDto();
		dto.setCompId(SSFLDatas.compId);
		PageQuery pg = new PageQuery();
		pg.setPageSize(10);
		PageResult<CompetitionTeamDto>  pointList = competitionTeamService.findCompetitionTeamByParam(dto, pg);
		if(pointList.getResults() != null && pointList.getResults().size() > 0){
			for(CompetitionTeamDto c :pointList.getResults()){
				c.setGames(c.getWin() + c.getDraw() + c.getLose());
				c.setWins(c.getGoalIn() - c.getGoalOut());
				c.setIntegral(c.getWin() * 3 + c.getDraw());
			}
		}
		mv.addObject("pointList", pointList);
		mv.addObject("pageList", pageList);
		mv.setViewName("/competition/data_trailer");
		return mv;
	}
	
	/**
	 * 赛事预告详情
	 * @author YIn
	 * @time:2016年3月23日 上午9:17:06
	 * @return
	 */
	@RequestMapping(value = "/match/trailerDetil")   
	public ModelAndView trailerDetil(String id) {
		ModelAndView mv = getModelAndView();
		CompetitionMatchDto competitionMatchDto = new CompetitionMatchDto();
		competitionMatchDto.setId(id);
		List<CompetitionMatchDto> list = competitionMatchService.findCompetitionMatch(competitionMatchDto);
		if(list != null && list.size() > 0){
			for(CompetitionMatchDto dto: list){
				dto.setMatchTimeString(DateUtils.format(dto.getMatchTime(), DateUtils.YMD_DASH_DATE_TIME));
				try {
					SimpleDateFormat sdf =   new SimpleDateFormat(DateUtils.YMD_DASH_DATE_TIME);
			        String str = sdf.format(dto.getMatchTime());
					dto.setWeek(DateUtils.dayForWeek(str,DateUtils.YMD_DASH_DATE_TIME));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mv.addObject("matchDto", list.get(0));
		}
		mv.setViewName("/competition/match_trailer");
		return mv;
	}
}
