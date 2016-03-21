package com.tianfang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

}
