package com.tianfang.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JoinController extends BaseController{

	@RequestMapping(value = "join")
	public ModelAndView index(String userId,String teamId) {
		ModelAndView mv = getModelAndView();
		mv.setViewName("join");
		return mv;
	}

}
