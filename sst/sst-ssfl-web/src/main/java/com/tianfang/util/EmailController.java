package com.tianfang.util;

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
 * 此类描述的是：邮箱验证controller
 * 
 * @author: jam_yin
 * @version: 2015年3月31日 上午15:46
 */

@Controller
@RequestMapping(value = "/email")
public class EmailController {
	@Autowired
	private IEmailSendService iEmailSendService;
//	@Autowired
//	private IUserService iUserService;

	@Autowired
	private RedisTemplate<String, Integer> redisTemplate;

	/**
	 * 此方法描述的是：邮件发送方法
	 * 
	 * @author: jam_yin
	 * @version: 2015年3月31日 上午15:49
	 */
	protected static final Log logger = LogFactory
			.getLog(EmailController.class);

	// private ApplicationContext ctx;
	@RequestMapping(value = "/send")
	@ResponseBody
	public Response<String> send(String email, HttpSession session,
			HttpServletRequest request) {
		Response<String> result = new Response<String>();
//		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
//		if (loginUserDto == null) {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("用户未登录");
//			return result;
//		}

		// ctx = SpringContextUtil.getApplicationContext();
		// JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");
		// SimpleMailMessage mail = new SimpleMailMessage(); //<span
		// style="color: #ff0000;">注意SimpleMailMessage只能用来发送text格式的邮件</span>
		int randomNumber = (int) (Math.random() * 9000 + 1000); // 验证码
		String content = "温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber + "验证码。";// 短信内容
		String from_ = "jujusports@ssic.cn"; // 发送的邮箱
		String subject = "聚运动邮箱验证"; // 主题
		// session.setAttribute(SessionConstants.EMAIL_VALIDAT_NUMBER, "");
		// session.setAttribute(SessionConstants.EMAIL_VALIDAT_NUMBER,
		// randomNumber);
		// session.setAttribute(SessionConstants.EMAIL_NUMBER, email);
		// session.setMaxInactiveInterval(90); //设置session有效期为90秒

//		 redisTemplate.opsForValue().set(SessionConstants.EMAIL_NUMBER,
//		 randomNumber);
//		String keyCode = SessionConstants.EMAIL_NUMBER + loginUserDto.getId();
		String keyCode = SessionConstants.EMAIL_NUMBER;
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90,
				TimeUnit.SECONDS);
		try {
			// mail.setTo(email);//接受者
			// mail.setFrom("jamhihi@126.com");//发送者,这里还可以另起Email别名，不用和xml里的username一致
			// mail.setSubject("验证邮箱");//主题
			// mail.setText(content);//邮件内容
			// sender.send(mail);
			iEmailSendService.sendEmail(randomNumber, email, content, from_,
					subject);
		} catch (Exception e) {
			// return JsonUtil.getJsonStr(new RequestResult(true,"发送邮件失败！"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("发送邮件失败！");
			return result;
		}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("发送邮件成功！");
		return result;

		/*
		 * String uri = "http://117.135.144.12/msg/index.jsp";//应用地址 String
		 * account = "qjtone_jjparty";//账号 String pswd = "Jjparyz201503#";//密码
		 * //String mobiles = "13800210021,13800138000";//手机号码，多个号码使用","分割
		 * //String mobiles =request.getParameter(mobilephone) ;
		 * logger.debug("mobilePhone ="+mobilePhone); String mobiles =
		 * mobilePhone; int randomNumber = (int) (Math.random()*9000+1000);
		 * logger.debug("randomNumber ="+randomNumber); String content =
		 * "【聚运动】温馨提示，为了保护您的隐私，请您在90秒内输入"+randomNumber +"验证码。";//短信内容 boolean
		 * needstatus = true;//是否需要状态报告，需要true，不需要false String product =
		 * "132903592";//产品ID String extno = "08";//扩展码 String returnString="";
		 * session.setAttribute(SessionConstants.VALIDAT_NUMBER, "");
		 * session.setAttribute(SessionConstants.VALIDAT_NUMBER, randomNumber);
		 * session.setMaxInactiveInterval(90); //设置session有效期为90秒 try {
		 * returnString = HttpSender.send(uri, account, pswd, mobiles, content,
		 * needstatus, product, extno); System.out.println(returnString); }
		 * catch (Exception e) { e.printStackTrace(); } return
		 * JsonUtil.getJsonStr(new RequestResult(true,returnString + " " +
		 * randomNumber));
		 */
	}

	
	@RequestMapping(value = "/validate.do")
	@ResponseBody
	public Response<String> validate(HttpSession session, String validateCode,
			String email) {
		logger.debug("validateCode =" + validateCode);

		// System.out.println("----"+redisTemplate.opsForValue().get(SessionConstants.EMAIL_VALIDAT_NUMBER));

		Response<String> result = new Response<String>();

//		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
//		if (loginUserDto == null) {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("用户未登录");  
//			return result;
//		}

		if (validateCode == null || validateCode.equals("")) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码为空");
			return result;
		}

//		String keyCode = SessionConstants.EMAIL_NUMBER + loginUserDto.getId();
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
//			String userAccountId = SessionUtil.getLoginSession(session).getId();
//			UsersDto usersDto = iUserService
//					.selectUsersByUserAccountId(userAccountId);
//			usersDto.setEmail(email);
//			usersDto.setUserAccountId(userAccountId);
//			int flag = iUserService.updateUsers(usersDto);
//			if (flag > 0) {
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("邮箱验证成功！");
//			} else {
//				result.setStatus(DataStatus.HTTP_FAILE);
//				result.setMessage("邮箱验证失败！");
//			}
			return result;
		} else {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码错误！");
			return result;

		}

	}
//
//	@RequestMapping(value = "/editmail.do")
//	@ResponseBody
//	public Response<String> editmail(HttpSession session, String email) {
//
//		Response<String> result = new Response<String>();
//
//		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
//		if (loginUserDto == null) {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("用户未登录");
//			return result;
//		}
//
//		if (StringUtils.isEmpty(email)) {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("邮箱为空");
//			return result;
//		}
//		String userAccountId = SessionUtil.getLoginSession(session).getId();
//		UsersDto usersDto = iUserService
//				.selectUsersByUserAccountId(userAccountId);
//		usersDto.setEmail(email);
//		usersDto.setUserAccountId(userAccountId);
//		int flag = iUserService.updateUsers(usersDto);
//		if (flag > 0) {
//			result.setStatus(DataStatus.HTTP_SUCCESS);
//			result.setMessage("邮箱修改成功！");
//		} else {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("邮箱修改失败！");
//		}
//		return result;
//
//	}
}
