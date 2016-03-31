package com.tianfang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.dto.TeamPlayerDatasDto;
import com.tianfang.train.service.ICompetitionTeamService;
import com.tianfang.train.service.ITeamPlayerDatasService;
import com.tianfang.util.SSFLDatas;

/**
 * @author YIn
 * @time:2016年3月23日 上午9:16:51
 * @ClassName: DataCenterController
 * @Description: 数据中心
 * @
 */
@Controller
@RequestMapping(value = "datacenter")
public class DataCenterController extends BaseController {

	@Autowired
	private ICompetitionTeamService competitionTeamService;
	
	@Autowired
	private ITeamPlayerDatasService teamPlayerDatasService;
	
	/**
	 * 积分榜
	 * @author YIn
	 * @time:2016年3月23日 上午9:17:06
	 * @return
	 */
	@RequestMapping(value = "points")
	public ModelAndView toPoints(CompetitionTeamDto dto,PageQuery page) {
		dto.setCompId(SSFLDatas.compId);
		ModelAndView mv = getModelAndView();
		page.setPageSize(10);
		PageResult<CompetitionTeamDto>  pageList = competitionTeamService.findCompetitionTeamByParam(dto, page);
		if(pageList.getResults().size() > 0){
			for(CompetitionTeamDto c :pageList.getResults()){
				c.setGames(c.getWin() + c.getDraw() + c.getLose());
				c.setWins(c.getGoalIn() - c.getGoalOut());
				c.setIntegral(c.getWin() * 3 + c.getDraw());
			}
		}
		mv.addObject("pageList", pageList);
		mv.setViewName("/data_center/points");
		return mv;
	}
	
	/**
	 * 射手榜
	 * @author YIn
	 * @time:2016年3月23日 上午9:17:06
	 * @return
	 */
	@RequestMapping(value = "shooter")
	public ModelAndView toShooter(PageQuery page) {
		ModelAndView mv = getModelAndView();
		String compId = SSFLDatas.compId;
		page.setPageSize(10);
		String order = "num";
		PageResult<TeamPlayerDatasDto> pageList = teamPlayerDatasService.findTeamPlayerDatasByCompId(compId, page, order);
		mv.addObject("pageList", pageList);
		mv.setViewName("/data_center/shooter");
		return mv;
	}
	
	/**
	 * 助攻榜
	 * @author YIn
	 * @time:2016年3月23日 上午9:17:06
	 * @return
	 */
	@RequestMapping(value = "assist")
	public ModelAndView toAssist(PageQuery page) {
		ModelAndView mv = getModelAndView();
		String compId = SSFLDatas.compId;
		page.setPageSize(10);
		String order = "assists";
		PageResult<TeamPlayerDatasDto> pageList = teamPlayerDatasService.findTeamPlayerDatasByCompId(compId, page, order);
		mv.addObject("pageList", pageList);
		mv.setViewName("/data_center/assist");
		return mv;
	}
	
	/**
	 * 红黄牌榜
	 * @author YIn
	 * @time:2016年3月23日 上午9:17:06
	 * @return
	 */
	@RequestMapping(value = "redAndYellow")
	public ModelAndView toRedAndYellow(PageQuery page) {
		ModelAndView mv = getModelAndView();
		String compId = SSFLDatas.compId;
		page.setPageSize(10);
		String order = "red";    //按红牌排序
		PageResult<TeamPlayerDatasDto> pageList = teamPlayerDatasService.findTeamPlayerDatasByCompId(compId, page, order);
		mv.addObject("pageList", pageList);
		mv.setViewName("/data_center/redAndYellow");
		return mv;
	}



}
