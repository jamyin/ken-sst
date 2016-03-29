package com.tianfang.home.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.sun.mail.handlers.image_gif;
import com.tianfang.business.dto.AppVersionDto;
import com.tianfang.business.service.IAppVersionService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.Response;

/**
 * 分配安卓用户和ios用户访问的URL地址
 * 
 */
@Controller
@RequestMapping("/appurl")
public class AppUrlController {

	@Autowired
	private IAppVersionService iAppVersionService;
	
	@RequestMapping("geturl")
	@ResponseBody
	public Response<AppVersionDto> getUrl(AppVersionDto dto,HttpServletResponse response,HttpServletRequest request) {
		Response<AppVersionDto> data = new Response<AppVersionDto>();
//		id = 1 ios 2 android
		
		AppVersionDto dtoObj = iAppVersionService.getAppVersionBy(dto);
		if(Objects.equal(dto, null)){
			data.setStatus(DataStatus.HTTP_FAILE);
			data.setMessage("数据服务异常");
			return data;
		}
		
		if(Objects.equal(dto.getVersionNum(),dtoObj.getVersionNum())){	//判断移动的版本不一样则需要跟新
			data.setStatus(DataStatus.HTTP_SUCCESS);
			dtoObj.setHttpUrl(dtoObj.getVersionUrl());
			data.setData(dtoObj);
			data.setMessage("您的程序需要跟新啦");
			return data;
		}else{	//分享
			dtoObj.setTitle(dto.getTitle());
			dtoObj.setHttpUrl(dtoObj.getShareUrl());
			data.setStatus(DataStatus.HTTP_SUCCESS);
			data.setData(dtoObj);
		}
		return data;
//		// 缓存浏览器平台版本
//		String v = request.getHeader("User-Agent").toLowerCase();
//		
//		// 判断是苹果还是安卓,返回相应的url
//		if (v.contains("iphone")) {
//			return "redirect:https://itunes.apple.com/cn/app/ju-yun-dong/id1004825276?mt=8";
//
//		} else if (v.contains("android")) {
//			return "redirect:http://103.36.132.2/download/JuJuSport.apk";
//		}
//		return "redirect:http://www.jujusports.cn";
	}
}
