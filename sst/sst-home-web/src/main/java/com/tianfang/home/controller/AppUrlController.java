package com.tianfang.home.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分配安卓用户和ios用户访问的URL地址
 * 
 * @author 张亚伟
 */
@Controller
@RequestMapping("/appurl")
public class AppUrlController {

	@RequestMapping("geturl")
	public String getUrl(HttpServletResponse response,
			HttpServletRequest request) {

		// 缓存浏览器平台版本
		String v = request.getHeader("User-Agent").toLowerCase();
		
		// 判断是苹果还是安卓,返回相应的url
		if (v.contains("iphone")) {
			return "redirect:https://itunes.apple.com/cn/app/ju-yun-dong/id1004825276?mt=8";

		} else if (v.contains("android")) {
			return "redirect:http://103.36.132.2/download/JuJuSport.apk";
		}
		return "redirect:http://www.jujusports.cn";
		
	}
}
