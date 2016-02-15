package com.tianfang.user.service;

import org.springframework.stereotype.Service;

import com.bcloud.msg.http.HttpSender;
import com.tianfang.common.model.RequestResult;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.user.service.ISmsSendService;

@Service
public class SmsSendServiceImpl implements ISmsSendService {

	@Override
	public String sendSms(int randomNumber, String mobilePhone, String content) {
		String uri = PropertiesUtils.getProperty("msg.url");// 应用地址
		String account = PropertiesUtils.getProperty("msg.account");// 账号
		String pswd = PropertiesUtils.getProperty("msg.password");// 密码
		String product = PropertiesUtils.getProperty("msg.product");// "132903592";//
																	// 产品ID
		String extno = PropertiesUtils.getProperty("msg.extno");// "08";// 扩展码

		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false

		String returnString = "";
		try {
			returnString = HttpSender.batchSend(uri, account, pswd, mobilePhone,
					content, needstatus, product, extno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.getJsonStr(new RequestResult(true, returnString + " "
				+ randomNumber));
	}
}