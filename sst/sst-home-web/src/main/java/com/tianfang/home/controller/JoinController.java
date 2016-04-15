package com.tianfang.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
	 * 此类描述的是：申请加入球队的接口
	 * @author: cwftalus@163.com
	 * @version: 2016年4月15日 下午1:16:39
 */
@Controller
public class JoinController extends BaseController{
	
	@RequestMapping(value = "join")
	public ModelAndView index(String userId,String teamId,String type) {
		ModelAndView mv = getModelAndView();
		mv.setViewName("join");
		return mv;
	}

}
