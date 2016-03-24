package com.tianfang.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.util.DateUtils;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.service.ICompetitionMatchService;

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
	
	/**
	 * 赛事预告详情
	 * @author YIn
	 * @time:2016年3月23日 上午9:17:06
	 * @return
	 */
	@RequestMapping(value = "/match/trailer")   
	public ModelAndView trailer(String id) {
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
