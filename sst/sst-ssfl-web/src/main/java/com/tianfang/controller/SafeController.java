package com.tianfang.controller;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.NotificationsDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.INotificationsService;
import com.tianfang.user.service.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**		
 * <p>Title: SafeController </p>
 * <p>Description: 类描述:账号安全相关操作</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月16日上午9:21:01
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "safe")
public class SafeController extends BaseController{
	protected static Log logger = LogFactory.getLog(SafeController.class);
	@Autowired
	private IUserService userService;
	@Autowired
	private INotificationsService notificationsService;

	/**
	 * 账户安全首页
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日上午11:54:35
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("safe");
		String userId = getSessionUserId();
		// 安全提醒 and 安全邮箱
		NotificationsDto noti = notificationsService.getNotificationsByUserId(userId);
		// 证件信息
		UserDto user = userService.getUserById(userId);
		mv.addObject("noti", noti);
		mv.addObject("user", user);
		mv.addObject("reminding", getReminding(noti));
		mv.addObject("settingEmail", getEmail(noti));
		mv.addObject("paper", getPaper(user));
		
		return mv;
	}

	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @author xiang_wang
	 * 2016年3月16日上午9:28:14
	 */
	@RequestMapping(value = "changePwd")
	@ResponseBody
	public Response<String> changePwd(String oldPwd, String newPwd){
		Response<String> response = new Response<String>();
		String userId = getSessionUserId();
		UserDto user = userService.getUserById(userId);
		try {
			if (!user.getPassword().equals(MD5Coder.encodeMD5Hex(oldPwd))){
				response.setMessage("原始密码输入有误!");
				response.setStatus(DataStatus.HTTP_FAILE);
				return response;
			}
			user.setPassword(MD5Coder.encodeMD5Hex(newPwd));
			userService.update(user);
			response.setMessage("恭喜您,密码修改成功!");
			sendRemind(userId, Point.ChangePwd);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("系统异常!");
			response.setStatus(DataStatus.HTTP_FAILE);
		}
		return response;
	}
	
	/**
	 * 更改用户安全提醒
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日上午11:36:47
	 */
	@RequestMapping(value = "changeReminding")
	@ResponseBody
	public Response<String> changeReminding(NotificationsDto dto){
		Response<String> response = new Response<String>();
		try {
			changeNotifications(dto);
			response.setMessage("恭喜您,安全提醒更改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("对不起,系统繁忙!");
		}
		return response;
	}

	/**
	 * 更换安全邮箱地址
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日下午1:17:14
	 */
	@RequestMapping(value = "changeEmail")
	@ResponseBody
	public Response<String> changeEmail(NotificationsDto dto){
		Response<String> response = new Response<String>();
		if (StringUtils.isBlank(dto.getEmail())){
			response.setMessage("对不起,邮箱不能为空!");
			return response;
		}
		if (!checkEmail(dto.getEmail())){
			response.setMessage("对不起,请输入正确的邮箱地址!");
			return response;
		}
		try {
			changeNotifications(dto);
			response.setMessage("恭喜您,安全邮箱更改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("对不起,系统繁忙!");
		}
		return response;	
	}
	
	/**
	 * 修改证件信息
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日下午3:41:47
	 */
	@RequestMapping(value = "changePaper")
	@ResponseBody
	public Response<String> changePaper(UserDto dto){
		Response<String> response = new Response<String>();
		try {
			String userId = getSessionUserId();
			dto.setId(userId);
			userService.update(dto);
			response.setMessage("恭喜您,证件信息更改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("对不起,系统繁忙!");
		}
		return response;	
	}

	/**
	 * 新增或者更新安全提醒逻辑
	 * @param dto
	 * @Author wangxiang
	 * @Date 2016/3/17 11:11
	 */
	private void changeNotifications(NotificationsDto dto) {
		if (StringUtils.isBlank(dto.getId())){
			String userId = getSessionUserId();
			NotificationsDto data = notificationsService.getNotificationsByUserId(userId);
			if (null == data){
				dto.setOwnerId(userId);
				notificationsService.save(dto);
			}else{
				dto.setId(data.getId());
				notificationsService.update(dto);
			}
		}else{
			notificationsService.update(dto);
		}
	}

	/**
	 * 校验email格式是否正确
	 * @param email
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日下午1:13:11
	 */
	private boolean checkEmail(String email){
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";    
		Pattern regex = Pattern.compile(check);    
		Matcher matcher = regex.matcher(email);    
		return matcher.matches();    
	}
	
	/**
	 * 判断用户是否设置安全邮箱
	 * @param noti
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日下午1:51:15
	 */
	private int getEmail(NotificationsDto noti) {
		if (null != noti){
			if (StringUtils.isNotBlank(noti.getEmail())){
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 判断用户是否填写证件信息
	 * @param user
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日下午1:48:57
	 */
	private int getPaper(UserDto user) {
		if (null != user){
			if (StringUtils.isNotBlank(user.getCname())){
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 判断用户是否设置安全提醒
	 * @param noti
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日下午1:48:23
	 */
	private int getReminding(NotificationsDto noti) {
		if (null != noti){
			if (null != noti.getLoginStat()){
				return 1;
			}
			if (null != noti.getNonlocalLoginStat()){
				return 1;
			}
			if (null != noti.getUpPasswordStat()){
				return 1;
			}
		}
		return 0;
	}
}