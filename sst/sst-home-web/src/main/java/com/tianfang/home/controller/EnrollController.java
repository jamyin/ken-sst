package com.tianfang.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
	 * 此类描述的是：赛事报名时存在的额外信息填写
	 * @author: cwftalus@163.com
	 * @version: 2016年4月15日 下午1:22:12
 */
@Controller
public class EnrollController extends BaseController{

	@RequestMapping(value = "enroll")
	public ModelAndView index(String userId,String compId) {
		ModelAndView mv = getModelAndView();
		mv.setViewName("enroll");
		return mv;
	}

}
