package com.tianfang.controller;

import com.google.common.base.Objects;
import com.tianfang.admin.dto.MenuDto;
import com.tianfang.admin.service.IHomeMenuService;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.IpUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ITeamService;
import com.tianfang.user.dto.NotificationsDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.IEmailSendService;
import com.tianfang.user.service.INotificationsService;
import com.tianfang.user.service.ISmsSendService;
import com.tianfang.user.service.IUserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BaseController {
	protected Logger logger = Logger.getLogger(BaseController.class);
	public static final String LAST_LOGIN_IP = "SST_USER_LAST_LOGIN_IP"; // 用户最后登陆ip标识
	public static final String CHANGEPWD_CONTENT = "尊敬的用户，您好，您的密码已修改。如非本人操作，请访问"+PropertiesUtils.getProperty("wwwdomain")+"。";	// 修改密码提示语
	public static final String LOGIN_CONTENT = "尊敬的用户，您好，欢迎您于$time$登陆上海业余足球超级杯联赛系统,感谢您的使用。";	// 登陆提示语
	public static final String LOGIN_EXCEPTION_CONTENT = "尊敬的用户，您好，本次登陆在$city$。如非本人操作，请访问"+PropertiesUtils.getProperty("wwwdomain")+"。";	// 异常登陆提示语
	public static final String FROM_ = "jujusports@ssic.cn"; // 发送的邮箱
	public static final String SUBJECT = "聚运动安全提醒"; // 主题

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private IUserService iUserService;
	
    @Autowired
    private IHomeMenuService iHomeMenuService;

	@Autowired
	private INotificationsService notificationsService;

	@Autowired
	private ISmsSendService smsSendService;

	@Autowired
	private IEmailSendService emailSendService;
	
	@Autowired
	private ITeamService iTeamService;


	/**
	 * 获取缓存中用户信息
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2016年1月18日下午4:45:10
	 * @throws Exception 
	 */
	public UserDto getUserByCache(String userId){	
		String keyCode = "SSTUSER"+userId;
		UserDto dto = null;
		if(null != redisTemplate.opsForValue().get(keyCode)){
			dto = (UserDto)redisTemplate.opsForValue().get(keyCode);
		}else{
			dto = iUserService.getUserById(userId);
			redisTemplate.opsForValue().set(keyCode, dto, 1, TimeUnit.HOURS);
		}
		return dto;
	}

	public ModelAndView getModelAndView(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", getUserAccountByUserId());
		mv.addObject("menuList", getMenuList());
		mv.addObject("cur",StringToInteger(getRequest().getParameter("cur")));
		mv.addObject("request_url",getRequest().getRequestURI());
		mv.addObject("wwwdomain",PropertiesUtils.getProperty("wwwdomain"));
		mv.addObject("teamId",getUserByTeam(getSessionUserId()));
		return mv;
	}
	
	
	public String getUserByTeam(String userId){
		if(!StringUtils.isEmpty(userId)){
			return null;
		}else{
			TeamDto teamDto = new TeamDto();
			teamDto.setCreateUserId(userId);
			List<TeamDto> teamList = iTeamService.findTeam(teamDto);
			if(Objects.equal(teamList,null)){
				return teamList.get(0).getId();
			}
		}
		return null;
	}
	
	public Integer StringToInteger(String value){
		if(!StringUtils.isEmpty(value)){
			return Integer.valueOf(value);
		}else{
			return 0;
		}
	}

	public ModelAndView getUserModelAndView(String userId) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", getUserByCache(userId));
		mv.addObject("menuList", getMenuList());
		
		return mv;
	}

	public UserDto getUserAccountByUserId(){
		UserDto user = (UserDto)getRequest().getSession().getAttribute(SessionConstants.LOGIN_USER_INFO);
		if(user==null){
			return null;
		}
		return user;		
	}

	public String getSessionUserId(){
		UserDto user = (UserDto)getRequest().getSession().getAttribute(SessionConstants.LOGIN_USER_INFO);
		if(user==null){
			return null;
		}
		return user.getId();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	
	public List<MenuDto> getMenuList(){
		return iHomeMenuService.findHomeMenuList(null);
	}

	/**
	 * 向redis缓存中存放用户登陆ip
	 * @param userId
     */
	public void setLastLoginIp(String userId){
		String ip = IpUtils.getIRealIPAddr(getRequest());
		redisTemplate.opsForValue().set(LAST_LOGIN_IP+userId, ip);
	}

	/**
	 * 获取用户上次登陆ip
	 * @param userId
	 * @return
     */
	public String getLastLoginIp(String userId){
		Object ip= redisTemplate.opsForValue().get(LAST_LOGIN_IP+userId);
		if (null != ip){
			return (String)ip;
		}
		return null;
	}

	/**
	 * 用户修改密码操作,发送安全提醒逻辑
	 * @Author wangxiang
	 * @param userId
	 * @param type 枚举变量(ChangePwd,Login)
	 * @Date 2016/3/18 11:53
	 */
	public void sendRemind(String userId, Point type){
		if (StringUtils.isNotBlank(userId)){
			UserDto user = getUserByCache(userId);
			NotificationsDto noti = notificationsService.getNotificationsByUserId(userId);
			switch (type){
				case ChangePwd:
						if (null != noti){
							// 修改密码提醒
							if (null != noti.getUpPasswordStat()){
								// 手机提醒
								if (noti.getUpPasswordStat() ==  NotificationsDto.Reminding.Phone.getState()){
									sendMoblie(user.getMobile(), CHANGEPWD_CONTENT);
								}
								// 邮件提醒
								if (noti.getUpPasswordStat() == NotificationsDto.Reminding.Email.getState()){
									sendEmail(noti.getEmail(), CHANGEPWD_CONTENT);
								}
							}
						}
					break;
				case Login:
					if (null != noti){
						String ip = IpUtils.getIRealIPAddr(getRequest());
						// 异地登陆
						if (null != noti.getNonlocalLoginStat()){
							String lastIp = getLastLoginIp(userId);
							if (StringUtils.isNotBlank(lastIp) || ip.equals(lastIp)){
								String newAddr = IpUtils.getCity(ip);
								String lastAddr = IpUtils.getCity(lastIp);
								if (null != newAddr && null != lastAddr){
									if (!newAddr.equals(lastAddr)){
										if (noti.getNonlocalLoginStat() ==  NotificationsDto.Reminding.Phone.getState()){
											sendMoblie(user.getMobile(), LOGIN_EXCEPTION_CONTENT.replace("$city$",newAddr));
										}
										if (noti.getNonlocalLoginStat() == NotificationsDto.Reminding.Email.getState()){
											sendEmail(noti.getEmail(), LOGIN_EXCEPTION_CONTENT.replace("$city$",newAddr));
										}
									}
								}
							}
						}

						// 设置登陆保护
						if (null != noti.getLoginStat()){
							if (noti.getLoginStat() ==  NotificationsDto.Reminding.Phone.getState()){
								sendMoblie(user.getMobile(), LOGIN_CONTENT.replace("$time$",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")));
							}
							if (noti.getLoginStat() == NotificationsDto.Reminding.Email.getState()){
								sendEmail(noti.getEmail(), LOGIN_CONTENT.replace("$time$",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")));
							}
						}
					}
					setLastLoginIp(userId);
					break;
				default:break;
			}
		}
	}

	/**
	 * 修改密码,登陆操作,校验是否发送安全提醒
	 */
	public enum Point{
		ChangePwd, Login;
	}

	/**
	 * 发送邮件给用户
	 * @param email
	 * @param content
     */
	private void sendEmail(String email, String content) {
		logger.info("发送邮件至:"+email+",内容为:"+content);
		if (StringUtils.isNotBlank(email)){
			emailSendService.sendEmail(0,email, content, FROM_ , SUBJECT);
		}else{
			logger.error("邮箱为空.未发送安全邮件!");
		}

	}

	/**
	 * 发送短信给用户
	 * @param mobile
	 * @param content
     */
	private void sendMoblie(String mobile, String content) {
		logger.info("发送短信至:"+mobile+",内容为:"+content);
		if (StringUtils.isNotBlank(mobile)){
			if(!CheckSendMsg(mobile)){
				logger.error("发送短信至:"+mobile+",超过当天最多的发送次数");
				return;
			}
			smsSendService.sendSms(0, mobile, content);
		}else{
			logger.error("手机号码为空.未发送安全短信!");
		}
	}

	/**
	 * 校验今日的短信提醒是否超数量
	 * @param mobile
	 * @return
     */
	public boolean CheckSendMsg(String mobile){
		String phoneKey = mobile + DateUtils.format(new Date(), DateUtils.YMD_DASH);
		if(null != redisTemplate.opsForValue().get(phoneKey)){
			Integer tempRounds = (Integer)redisTemplate.opsForValue().get(phoneKey);
			if(tempRounds > Integer.valueOf(PropertiesUtils.getProperty("maxSendNumber"))){
				return false;
			}
			redisTemplate.delete(phoneKey);
			redisTemplate.opsForValue().set(phoneKey, (tempRounds+1), 24, TimeUnit.HOURS);
		}else{
			redisTemplate.opsForValue().set(phoneKey, 1, 24, TimeUnit.HOURS);
		}
		return true;
	}
}
