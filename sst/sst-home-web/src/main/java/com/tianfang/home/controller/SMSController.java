package com.tianfang.home.controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.service.ISmsSendService;


/**		
 * <p>Title: SMSController </p>
 * <p>Description: 类描述:短信发送方法</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月18日下午3:35:09
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "sms")
public class SMSController {
	protected static final Log logger = LogFactory.getLog(SMSController.class);

	@Autowired
	private ISmsSendService iSmsSendService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 发送短信验证码
	 * @param mobile
	 * @param captcha
	 * @param request
	 * @return
	 * @author xiang_wang
	 * 2016年1月19日上午10:03:15
	 */
	@RequestMapping(value = "send")
	@ResponseBody
	public Response<String> regMobileVal(String mobile, String captcha, HttpServletRequest request) {
		Response<String> result = new Response<String>();			
	
		if(StringUtils.isEmpty(captcha)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码为空！");
			return result;
		}
		String randomPicSession = request.getSession().getAttribute("RandomCode").toString().toLowerCase();
		if (!captcha.toLowerCase().equals(randomPicSession)) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码输入错误！");
			return result;
		}
		
		if(!CheckSendMsg(redisTemplate, mobile, request)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("发送短信过于频繁,请您稍后再试");
			return result;			
		}
		
		if(!CheckSendMsg(redisTemplate, mobile)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("超过当天最多的发送次数");
			return result;
		}
		
		int randomNumber = (int) (Math.random() * 9000 + 1000);
		String content = "温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber + "验证码。";// 短信内容
		iSmsSendService.sendSms(randomNumber, mobile, content);
		String keyCode = "reg"+mobile ;
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90, TimeUnit.SECONDS);;
		result.setStatus(DataStatus.HTTP_SUCCESS);
		return result;
	}

	/**
	 * 验证手机
	 * @param session
	 * @param validateCode
	 * @param mobilePhone
	 * @return
	 * @author xiang_wang
	 * 2016年1月19日上午10:03:57
	 */
	@RequestMapping(value = "validate")
	@ResponseBody
	public Response<String> validate(HttpSession session, String validateCode, String mobilePhone) {
		logger.debug("validateCode =" + validateCode);
		Response<String> result = new Response<String>();
		
		if(validateCode == null || validateCode.equals("")){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码为空！");
			return result;
		}
		if (StringUtils.isEmpty(mobilePhone)) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("手机为空！");
			return result;
		}
		
		String keyCode = SessionConstants.PHONE_NUMBER;
		if(redisTemplate.opsForValue().get(keyCode)==null || redisTemplate.opsForValue().get(keyCode).equals("")){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码失效！");
			return result;
		}
		
		String checkCode = redisTemplate.opsForValue().get(keyCode).toString();
		if (validateCode.equals(checkCode)) {
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("手机验证成功！");
			return result;
		} else {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码错误！");
			return result;
		}
	}

    public static Boolean CheckSendMsg(final RedisTemplate<String, Object> redisTemplate,final String mobilePhone,HttpServletRequest request){
    	String keySessionId = mobilePhone + DateUtils.format(new Date(), DateUtils.YMD_DASH)+"diff";
    	if(redisTemplate.opsForValue().get(keySessionId)!=null){
    		String nowDate = (String)redisTemplate.opsForValue().get(keySessionId);
    		long  diffmin = DateUtils.diffNowMin(DateUtils.parse(nowDate, DateUtils.YMD_DASH_WITH_TIME));
    		if(diffmin < 10){
    			return false;
    		}
    	}else{
    		String nowDate = DateUtils.format(new Date(), DateUtils.YMD_DASH_WITH_TIME);
    		redisTemplate.opsForValue().set(keySessionId, nowDate, 10, TimeUnit.MINUTES);
    	}
    	return true;
    }
	
	public static Boolean CheckSendMsg(final RedisTemplate<String, Object> redisTemplate,final String mobilePhone){
		String phoneKey = mobilePhone + DateUtils.format(new Date(), DateUtils.YMD_DASH);
		if(redisTemplate.opsForValue().get(phoneKey)!=null){
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