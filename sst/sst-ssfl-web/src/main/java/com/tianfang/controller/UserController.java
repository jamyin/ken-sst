package com.tianfang.controller;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.model.Response;
import com.tianfang.common.tools.RandomPicTools;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.IEmailSendService;
import com.tianfang.user.service.ISmsSendService;
import com.tianfang.user.service.IUserService;

/**
 * @author YIn
 * @time:2016年3月11日 上午10:50:38
 * @ClassName: UserController
 * @Description: 用户controller
 * @
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController{
	
	protected static final Log logger = LogFactory.getLog(UserController.class);
    
    @Autowired
	private ISmsSendService iSmsSendService;
    
    @Autowired
	private IEmailSendService iEmailSendService;
    
	@Autowired
	private IUserService userService;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
	@RequestMapping(value = "/SMS/send")
	@ResponseBody
	public Response<String> sendSMS(String mobilePhone,String picCaptcha,HttpSession session,HttpServletRequest request) {
		Response<String> result = new Response<String>();
		UserDto dto = new UserDto();
		dto.setMobile(mobilePhone);
		List<UserDto> list = userService.findUserByParam(dto);
		if (list == null || list.size() <= 0) {
			result.setStatus(-1);
			result.setMessage("此号码未注册过请先注册！");
			return result;
		}
		if(StringUtils.isEmpty(picCaptcha)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码为空！");
			return result;
		}
		String randomPicSession = session.getAttribute("RandomCode").toString().toLowerCase();
		if (!picCaptcha.toLowerCase().equals(randomPicSession)) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码输入错误！");
			return result;
		}		
//		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
//		if (loginUserDto == null) {
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("用户未登录");
//			return result;
//		}
//		
//		if (editPhone!=null) {
//			String userAccountId = SessionUtil.getLoginSession(session).getId();
//			UsersDto oldusersDto = iUserService
//					.selectUsersByUserAccountId(userAccountId);
//			mobilePhone=oldusersDto.getMobileNo();
//		}
		
		/*if(!CheckSendMsg(redisTemplate, mobilePhone, request)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("发送短信过于频繁,请您稍后再试");
			return result;			
		}*/

		/*if(!CheckSendMsg(redisTemplate, mobilePhone)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("超过当天最多的发送次数");
			return result;
		}*/
		
		int randomNumber = (int) (Math.random() * 9000 + 1000);
		String content = "温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber + "验证码。";// 短信内容
		//String returnString = iSmsSendService.sendSms(randomNumber, mobilePhone, content);
//		String keyCode = SessionConstants.PHONE_NUMBER+loginUserDto.getId();
		String keyCode = mobilePhone + "forget";
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90, TimeUnit.SECONDS);
		System.out.println("mobilePhone:"+mobilePhone);
		System.out.println("keyCode:"+keyCode);
		System.out.println("randomNumber:"+randomNumber);
		
//		session.setAttribute(SessionConstants.SMS_VALIDAT_NUMBER, "");
//		session.setAttribute(SessionConstants.SMS_VALIDAT_NUMBER, randomNumber);
//		session.setAttribute(SessionConstants.PHONE_NUMBER, mobilePhone);
//		session.setMaxInactiveInterval(90); // 设置session有效期为90秒
		//return JsonUtil.getJsonStr(new RequestResult(true, returnString + " " + randomNumber));
		result.setStatus(DataStatus.HTTP_SUCCESS);
//		result.setMessage(returnString + " " + randomNumber);
		return result;
	}
    
    @RequestMapping(value = "/email/send")
	@ResponseBody
	public Response<String> sendEmail(String email, HttpSession session,
			HttpServletRequest request) {
		Response<String> result = new Response<String>();
		UserDto dto = new UserDto();
		dto.setEmail(email);
		List<UserDto> list = userService.findUserByParam(dto);
		if (list == null || list.size() <= 0) {
			result.setStatus(-1);
			result.setMessage("此号码未注册过请先注册！");
			return result;
		}
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
	}
    
    @RequestMapping("/forget")
    public ModelAndView index(HttpServletRequest request){
    	ModelAndView mv = getModelAndView();
    	mv.setViewName("/forget");
        return mv;
    }
    
    /**
     * 获取图片验证码
     * @param reponse
     * @param request
     * @param session
     */
    @RequestMapping(value = "/drawRandom")
    @ResponseBody
    public void drawRandom(HttpServletResponse reponse,
            HttpServletRequest request, HttpSession session) {
        RandomPicTools randomPicTools = new RandomPicTools();
        int width = 80;// 图片宽
        int height = 26;// 图片高
        int lineSize = 40;// 干扰线数量
        int stringNum = 4;// 随机产生字符数量
        session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(randomPicTools.getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            randomPicTools.drowLine(g, width, height);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = randomPicTools.drowString(g, randomString, i);
        }
        session.removeAttribute("RandomCode");
        session.setAttribute("RandomCode", randomString);
        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", reponse.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 比较图片验证码
     * @param imgCode
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/authImageCode")
    public Response<String> authImageCode(String imgCode,HttpSession session){
        Response<String> result =new Response<String>();
        String randomCode = session.getAttribute("RandomCode").toString().toLowerCase();
        if(randomCode.equals(imgCode.toLowerCase())){
            result.setStatus(DataStatus.HTTP_SUCCESS);;
        }else{
            result.setStatus(DataStatus.HTTP_FAILE);
            result.setMessage("图片验证码错误,请重新输入图片验证码~~");
        }
        return result;
    }
    
    /**
     * 
     * mobliephone：手机用户找回密码
     * @param validateCode
     * @param mobilePhone
     * @param password
     * @return
     * @throws Exception 
     * @exception	
     * @author Administrator
     * @date 2015年11月19日 下午6:06:16
     */
    @RequestMapping("/mobliephone")
    @ResponseBody
    public Response<String> mobliephone( String validateCode,String mobilePhone,String password) throws Exception {
        logger.debug("validateCode =" + validateCode);
        Response<String> result = new Response<String>();
        String md5oldPwd = MD5Coder.encodeMD5Hex(password);
        String keyCode = mobilePhone + "forget";
        if(validateCode ==null){
            result.setStatus(DataStatus.HTTP_FAILE);
            result.setMessage("验证码失效！");
            return result;
        }
        if(redisTemplate.opsForValue().get(keyCode)==null || redisTemplate.opsForValue().get(keyCode).equals("")){
            result.setStatus(DataStatus.HTTP_FAILE);
            result.setMessage("验证码失效！");
            return result;
        }
        String checkCode = redisTemplate.opsForValue().get(keyCode).toString();
        if (validateCode.equals(checkCode)) {
        	UserDto dto = new UserDto();
        	dto.setMobile(mobilePhone);
    		List<UserDto> list = userService.findUserByParam(dto);
    		if(list == null || list.size() == 0){
    			 result.setStatus(DataStatus.HTTP_SUCCESS);
                 result.setMessage("此手机号码没注册过请先注册！");
                 return result;
    		}
    		list.get(0).setPassword(md5oldPwd);
            Integer flag = userService.update(list.get(0));
            if (flag == 1) {
                result.setStatus(DataStatus.HTTP_SUCCESS);
                result.setMessage("手机找回密码成功！");
            }
            if(flag == 0){
              result.setStatus(DataStatus.HTTP_FAILE);
              result.setMessage("手机验证失败！");   
            }
            return result;
        }else {
            result.setStatus(DataStatus.HTTP_FAILE);
            result.setMessage("验证码错误！");
            return result;
        }
    }   
    
    @RequestMapping("/email")
    @ResponseBody
    public Response<String> email(String validateCode,String email,String password) throws Exception {
        logger.debug("validateCode =" + validateCode);
        Response<String> result = new Response<String>();
        String md5oldPwd = MD5Coder.encodeMD5Hex(password);
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
        	UserDto dto = new UserDto();
        	dto.setEmail(email);
        	List<UserDto> list = userService.findUserByParam(dto);
        	if(list == null || list.size() == 0){
        		result.setStatus(DataStatus.HTTP_SUCCESS);
                result.setMessage("此手邮箱没注册过请先注册！");
                return result;
        	}
        	list.get(0).setPassword(md5oldPwd);
            Integer flag = userService.update(list.get(0));
            if (flag ==1) {
                result.setStatus(DataStatus.HTTP_SUCCESS);
                result.setMessage("邮箱找回密码成功！");
            }
            if (flag == 0) {
              result.setStatus(DataStatus.HTTP_FAILE);
              result.setMessage("邮箱验证失败！");   
            }
            return result;
        }else {
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
	

	/**
	 * 用户注册
	 * @param request
	 * @return
	 */
	@RequestMapping("/regiest")
    public ModelAndView regiest(HttpServletRequest request){
    	ModelAndView mv = getModelAndView();
    	mv.setViewName("/regiest");
        return mv;
    }
	
}
