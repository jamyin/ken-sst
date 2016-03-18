package com.tianfang.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.dto.LoginUserDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.IUserService;

/**
 * 
	 * 此类描述的是：针对用户登录处理
	 * @author: cwftalus@163.com
	 * @version: 2016年3月18日 上午9:18:47
	 * @serial !@#$%^&*())_+
 */
@Controller
@RequestMapping(value=("login"))
public class LoginController extends BaseController{

	@Autowired
	private IUserService iUserService;
	
	
	@RequestMapping(value=("do"))
	@ResponseBody
	private Response<String> login(String userAccount,String password,HttpSession session){
		Response<String> response = new Response<String>();
		UserDto dto = new UserDto();
		if(StringUtils.isEmpty(userAccount)){
			response.setMessage("账号不能为空");
			response.setStatus(DataStatus.HTTP_FAILE);
			return response;
		}
		if(StringUtils.isEmpty(password)){
			response.setMessage("密码不能为空");
			response.setStatus(DataStatus.HTTP_FAILE);
			return response;
		}

		checkParam(dto, userAccount,password);

		UserDto userDto = iUserService.checkUser(dto);
		if(Objects.equal(userDto, null)){
			response.setMessage("账号不存在或者密码错误");
			response.setStatus(DataStatus.HTTP_FAILE);
			return response;
		}
		LoginUserDto seuserDto = new LoginUserDto();
		seuserDto.setId(userDto.getId());
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, seuserDto);
		// 添加用户登陆安全提醒
		sendRemind(userDto.getId(), Point.Login);
		return response;
	}

	public void checkParam(UserDto dto,String userAccount,String password){
		int indexOf = userAccount.indexOf("@");
		if(Objects.equal(indexOf, -1)){
			dto.setMobile(userAccount);
		}else{
			dto.setEmail(userAccount);
		}
		try {
			dto.setPassword(MD5Coder.encodeMD5Hex(password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
