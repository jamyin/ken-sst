package com.tianfang.home.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.tianfang.user.service.ISmsSendService;

public class SmsUtils {
	public static String REMIND_INFO = "我给你发了一条提醒,请点击提醒列表查看";
	public static String NOTICE_INFO = "我给你发了一条公告,请点击公告列表查看";
	public static String VOTE_INFO = "我给你发了一条投票,请点击投票列表查看";
	
	@Autowired
	private static ISmsSendService iSmsSendService;
	
	
	@Async
	public static void sendMessage(String[] jIds,String message){
		if(jIds!=null && jIds.length>0){
			for(String str : jIds){
				String mobile = str.replace("#sst", "");
				iSmsSendService.sendSms(1, mobile, message);
			};
		}
	}
	
}
