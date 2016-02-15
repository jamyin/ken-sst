//package com.tianfang.common.util;
//
//import freemarker.template.Template;
//import lombok.Setter;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * Spring 发邮件工具类
// * Created by Peter on 14-9-17.
// */
//@Component
//public class SimpleSendEmailUtils{
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    private FreeMarkerConfigurer freeMarker;
//
//    /**
//     * 发送支持Html功能
//     * @param mailMessage
//     * @param templateName
//     * @throws Exception
//     */
//    public void sendHtmlEmail(SpringMailMessage mailMessage,String templateName) throws Exception{
//        MimeMessage msg = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(msg,true,"utf-8");
//        helper.setSubject(mailMessage.getSubject());
//        helper.setTo(mailMessage.getTo().split(";"));
//        helper.setFrom(mailMessage.getFrom());
//        helper.setText(renderText(mailMessage.getText(),templateName),true);
//        javaMailSender.send(msg);
//    }
//    /**
//     * 发送文本邮件功能
//     */
//    public void sendEmail(SpringMailMessage mailMessage) throws Exception{
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(mailMessage.getFrom());
//        message.setTo(mailMessage.getTo().split(";"));
//        message.setText(mailMessage.getText());
//        message.setSubject(mailMessage.getSubject());
//        javaMailSender.send(message);
//    }
//    private String renderText(String content,String templateName) throws Exception {
//        Template tmp = freeMarker.getConfiguration().getTemplate(templateName);
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("content",content);
//        return FreeMarkerTemplateUtils.processTemplateIntoString(tmp,map);
//    }
//}
