package com.tianfang.user.service;

import org.springframework.scheduling.annotation.Async;

/**		
 * <p>Title: IEmailSendService </p>
 * <p>Description: 类描述:发送邮件</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月18日下午3:26:29
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IEmailSendService {
	
	@Async
	public void sendEmail(int randomNumber,String email,String content, String from, String subject);
}
