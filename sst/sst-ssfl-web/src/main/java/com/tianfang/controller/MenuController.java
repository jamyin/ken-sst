package com.tianfang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.HomeMenuDto;
import com.tianfang.admin.service.IHomeMenuService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController{

	@Autowired
	private IHomeMenuService iHomeMenuService;
	
	
	@RequestMapping(value="/index")
	public ModelAndView index(String mId){
		ModelAndView mv = getModelAndView();
		HomeMenuDto menuDto = iHomeMenuService.findById(mId);
		if(menuDto==null){
			return new ModelAndView("redirect:/index.htm");
		}
		
		mv.addObject("menuDto", menuDto);
		mv.setViewName("/single/index");
		return mv;
	}

}
