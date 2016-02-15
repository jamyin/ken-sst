package com.tianfang.home.controller;

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

import com.tianfang.user.service.IEmailSendService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;

/**		
 * <p>Title: EmailController </p>
 * <p>Description: 类描述:邮箱验证controller</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月18日下午3:30:28
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "email")
public class EmailController {
	@Autowired
	private IEmailSendService iEmailSendService;

	@Autowired
	private RedisTemplate<String, Integer> redisTemplate;

	protected static final Log logger = LogFactory.getLog(EmailController.class);

	/**
	 * 发送邮箱验证码
	 * @param email
	 * @param session
	 * @param request
	 * @return
	 * @author xiang_wang
	 * 2016年1月19日上午10:04:30
	 */
	@RequestMapping(value = "send")
	@ResponseBody
	public Response<String> send(String email, HttpSession session,
			HttpServletRequest request) {
		Response<String> result = new Response<String>();
		int randomNumber = (int) (Math.random() * 9000 + 1000); // 验证码
		String content = "温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber + "验证码。";// 短信内容
		String from_ = "jujusports@ssic.cn"; // 发送的邮箱
		String subject = "聚运动邮箱验证"; // 主题
		String keyCode = SessionConstants.EMAIL_NUMBER;
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90, TimeUnit.SECONDS);
		try {
			iEmailSendService.sendEmail(randomNumber, email, content, from_,
					subject);
		} catch (Exception e) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("发送邮件失败！");
			return result;
		}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("发送邮件成功！");
		return result;
	}

	
	/**
	 * 验证邮箱
	 * @param session
	 * @param validateCode
	 * @param email
	 * @return
	 * @author xiang_wang
	 * 2016年1月19日上午10:04:22
	 */
	@RequestMapping(value = "validate")
	@ResponseBody
	public Response<String> validate(HttpSession session, String validateCode,
			String email) {
		logger.debug("validateCode =" + validateCode);

		Response<String> result = new Response<String>();

		if (validateCode == null || validateCode.equals("")) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码为空");
			return result;
		}

		String keyCode = SessionConstants.EMAIL_NUMBER;
		if (redisTemplate.opsForValue().get(keyCode) == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码失效");
			return result;
		}

		if (StringUtils.isEmpty(email)) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("邮箱为空");
			return result;
		}
		String checkCode = redisTemplate.opsForValue().get(keyCode).toString();
		if (validateCode.equals(checkCode)) {
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("邮箱验证成功！");
			return result;
		} else {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码错误！");
			return result;

		}

	}
}