package com.tianfang.user.service;

import org.springframework.scheduling.annotation.Async;

/**		
 * <p>Title: ISmsSendService </p>
 * <p>Description: 类描述:发送短信</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月18日下午3:31:31
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface ISmsSendService {
	
	@Async
	public String sendSms(int randomNumber,String mobilePhone,String content);
}
