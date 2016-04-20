package com.tianfang.home.utils;

import com.tianfang.common.util.HttpClientUtil;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.home.controller.AddressesController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;
import java.util.Map;

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
	
	protected static final Log logger = LogFactory.getLog(TigaseUtil.class);
	
	public static String REMIND_INFO = "我给你发了一条提醒,请点击提醒列表查看";
	public static String NOTICE_INFO = "我给你发了一条公告,请点击公告列表查看";
	public static String VOTE_INFO = "我给你发了一条投票,请点击投票列表查看";
	
	private static String REGISTERED_URL = "http://${ip}/user/regUser.do";
	private static String RESETPASSWORD_URL = "http://${ip}/user/changePassword.do";
	private static String GETUSERBYACCOUNT_URL = "http://${ip}/user/getUserByAccount.do";
	private static final String ip;
	private static final String suffix;
	
	static{
		ip = PropertiesUtils.getProperty("tigaseTomcat");
		suffix = PropertiesUtils.getProperty("tiagseSuffix");
		REGISTERED_URL = REGISTERED_URL.replace("${ip}", ip); 
		RESETPASSWORD_URL = RESETPASSWORD_URL.replace("${ip}", ip);
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
		Map<String, String> params = assemblyRequestParams(password, mobile, Type.create);
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
		Map<String, String> params = assemblyRequestParams(password, mobile, Type.update);
		String result = HttpClientUtil.sendPostRequestByJava(RESETPASSWORD_URL, params);
		return analysisResult(result);
	}
	
	/**
	 * 组装请求参数
	 * @param password
	 * @param mobile
	 * @return
	 * @author xiang_wang
	 * 2016年3月3日下午1:32:36
	 */
	private static Map<String, String> assemblyRequestParams(String password,
			String mobile, Type type) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("userAccount", mobile+suffix);
		if (type == Type.create){
			params.put("password", password);
		}
		if (type == Type.update){
			params.put("newPassword", password);
		}

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
	
	@Async
	public static void sendMessage(String mobile,String pic,String nickName,String[] jIds,String message){
		
		Map<String, String> params = new HashMap<String, String>(7);
		params.put("from", mobile+suffix);
		params.put("fromIcon", pic);
		params.put("fromNickName", nickName);
		params.put("message", message);
		params.put("messageType", "text");
		
		StringBuffer sb = new StringBuffer();
		if(jIds!=null && jIds.length>0){
			sb.append("[");
			for(String str : jIds){
				sb.append("\"").append(str).append("\",");
			}
			sb.subSequence(0, sb.length()-1);
			sb.append("]");
		}
		params.put("userList", sb.toString());
		params.put("type", "0");
		
		String result = HttpClientUtil.sendPostRequestByJava(PropertiesUtils.getProperty("tigase_http"), params);
		
//		StringBuffer sb = new StringBuffer();
//		sb.append("from=").append(mobile).append("#").append(PropertiesUtils.getProperty("project")).append("&");
//		sb.append("fromIcon=").append(pic).append("&");
//		sb.append("fromNickName=").append(nickName).append("&");
//		sb.append("message=").append("来自"+nickName+"的提醒").append("&"); 
//		sb.append("messageType=").append("text").append("&");
//		sb.append("userList=").append(jIds).append("&");
//		sb.append("type=").append(0).append("&");
//		System.out.println("sb+sb"+sb.toString());
//		GetPostUtil.post("http://192.168.1.234/message/sendMessages.do?", sb.toString());
	}

	private enum Type{
		create,update;
	}
	
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("18721472363#sst,13040691917#sst,");
		System.out.println(sb.subSequence(0, sb.length()-1));
	}
	/**
	 * 
		 * 此方法描述的是：判断tigase用户是否存在
		 * @author: cwftalus@163.com
		 * @version: 2016年4月20日 上午11:07:31
	 */
	

	public static boolean getUserByAccount(String mobile){
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("userAccount", mobile+suffix);
		String result = HttpClientUtil.sendPostRequestByJava(GETUSERBYACCOUNT_URL, params);
		return analysisResult(result);
	}
	
}