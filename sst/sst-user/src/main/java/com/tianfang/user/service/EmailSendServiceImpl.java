package com.tianfang.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSendServiceImpl implements IEmailSendService {
   
    @Autowired
	private JavaMailSender sender;
	
	@Override
	public void sendEmail(int randomNumber, String email, String content, String from, String subject) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);//接受者 
		mail.setFrom(from);//发送者,这里还可以另起Email别名，不用和xml里的username一致  
		mail.setSubject(subject);//主题  
		mail.setText(content);//邮件内容  
		sender.send(mail);
	}
}