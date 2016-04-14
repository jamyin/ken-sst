package com.tianfang.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "sst")
public class SstController extends BaseController{

	
	@RequestMapping(value = "about")
	public ModelAndView about() {
		ModelAndView mv = getModelAndView();
		mv.setViewName("about");
		return mv;
	}

}
