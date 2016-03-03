package com.tianfang.home.utils;

import java.util.HashMap;
import java.util.Map;

import com.tianfang.common.util.HttpClientUtil;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;

/**		
 * <p>Title: TigaseUtil </p>
 * <p>Description: 类描述:Tigase聊天服务器工具类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月3日下午1:47:22
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class TigaseUtil {
	private static String REGISTERED_URL = "http://${ip}/user/regUser.do";
	private static String RESETPASSWORD_URL = "http://${ip}/user/changePassword.do";
	private static final String ip;
	private static final String suffix;
	
	static{
		ip = PropertiesUtils.getProperty("tigaseTomcat");
		suffix = PropertiesUtils.getProperty("tiagseSuffix");
		REGISTERED_URL = REGISTERED_URL.replaceAll("${ip}", ip);
		RESETPASSWORD_URL = RESETPASSWORD_URL.replaceAll("${ip}", ip);
	}
	
	/**
	 * Tigase聊天服务器.注册用户操作
	 * @param mobile
	 * @param password
	 * @return
	 * @author xiang_wang
	 * 2016年3月3日下午1:33:34
	 */
	public static boolean registered(String mobile, String password){
		checkAccountAndPassword(mobile, password);
		Map<String, String> params = assemblyRequestParams(password, suffix);
		String result = HttpClientUtil.sendPostRequestByJava(REGISTERED_URL, params);
		return analysisResult(result);
	}

	/**
	 * Tigase聊天服务器.修改密码操作
	 * @param mobile
	 * @param password
	 * @return
	 * @author xiang_wang
	 * 2016年3月3日下午1:34:02
	 */
	public static boolean resetPassword(String mobile, String password){
		Map<String, String> params = assemblyRequestParams(password, suffix);
		String result = HttpClientUtil.sendPostRequestByJava(RESETPASSWORD_URL, params);
		return analysisResult(result);
	}
	
	/**
	 * 组装请求参数
	 * @param password
	 * @param suffix
	 * @return
	 * @author xiang_wang
	 * 2016年3月3日下午1:32:36
	 */
	private static Map<String, String> assemblyRequestParams(String password,
			String suffix) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("userAccount", "mobile"+suffix);
		params.put("password", password);
		return params;
	}
	
	/**
	 * 解析返回状态码
	 * @param result
	 * @return
	 * @author xiang_wang
	 * 2016年3月3日下午1:39:02
	 */
	private static boolean analysisResult(String result) {
		if (StringUtils.isNotBlank(result) && result.split("`")[1].equals("200")){
			return true;
		}
		return false;
	}

	/**
	 * 校验请求参数是否为空
	 * @param mobile
	 * @param password
	 * @author xiang_wang
	 * 2016年3月3日下午1:32:51
	 */
	private static void checkAccountAndPassword(String mobile, String password) {
		if (StringUtils.isBlank(mobile)){
			throw new RuntimeException("聊天服务器注册:账号不能为空!");
		}
		if (StringUtils.isBlank(password)){
			throw new RuntimeException("聊天服务器注册:密码不能为空!");
		}
	}
}
