package com.tianfang.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.train.dto.CompetitionApplyDto;
import com.tianfang.train.service.ICompetitionApplyService;

@Controller
public class CompReasonController extends BaseController{

	@Autowired
	private ICompetitionApplyService iCompetitionApplyService; 
	
	@RequestMapping(value = "comp/reason")
	public ModelAndView  reason(String applyId) {
		ModelAndView mv = new ModelAndView();

		CompetitionApplyDto applyDto = iCompetitionApplyService.getCompetitionApplyById(applyId);

		mv.addObject("applyDto", applyDto);
		mv.setViewName("reason");
		return mv;
	}
}
