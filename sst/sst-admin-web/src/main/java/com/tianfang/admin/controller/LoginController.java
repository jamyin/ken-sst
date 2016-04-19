package com.tianfang.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.admin.service.IAdminService;
import com.tianfang.admin.utils.Const;
import com.tianfang.admin.utils.PageData;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.model.MessageResp;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private IAdminService adminService;

	@RequestMapping(value = "/toLogin")
	public ModelAndView toLogin() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/login");
		return mv;
	}

	@RequestMapping(value = "/login")
	@ResponseBody
	public Map<String,Object> login(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "passWord") String passWord,
			HttpServletRequest request, HttpSession session) throws Exception {
		String md5passwd = MD5Coder.encodeMD5Hex(passWord);
		AdminDto admin = adminService.adminLogin(userName, md5passwd);
        if (null == admin) {
            return MessageResp.getMessage(false, "用户名/密码错误!");
        }else {
            session.setAttribute(Const.SESSION_USER, admin);
            return MessageResp.getMessage(true,"登录成功!");
        }
	}
	
	/**
	 * 访问系统首页
	 */
	@RequestMapping(value="/main")
	public ModelAndView login_index(HttpSession session){
		AdminDto user = (AdminDto) session.getAttribute(Const.SESSION_USER); // 当前登录用户

		ModelAndView mv = null;
		if (user != null) {
			mv = this.getModelAndView(user.getId());
			session.setAttribute(Const.SESSION_USERNAME,user.getAccount());
			session.setAttribute(Const.SESSION_ID,user.getId());
			mv.setViewName("/index");
		}else {
			mv = this.getModelAndView();
			mv.setViewName("/login");//session失效后跳转登录页面
		}
		
		PageData pd = new PageData();
		pd = this.getPageData();
		
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "/tab";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/login_default")
	public String defaultPage(){
		return "/default";
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpSession session){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageData pd = new PageData();
		
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_menuList);
		mv.setViewName("/login");
		mv.addObject("pd",pd);
		return mv;
	}
}