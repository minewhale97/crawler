package com.minewhale.grabber.grabbercore.web.controller;

import com.minewhale.grabber.grabbercore.service.external.EmailUtils;
import com.minewhale.grabber.grabbercore.service.external.GetCode;
import com.minewhale.grabber.grabbercore.service.external.dto.BodyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
@RestController
public class IndexController {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private GetCode getCode;
    @GetMapping("/sendmail/{to}")
    public String sendmail(@PathVariable String to) throws MessagingException {
        //Thymeleaf提供了TemplateEngine来对模板进行渲染，通过Context构造模板中变量需要的值
        Context context = new Context();
        context.setVariable("title", "自定义的HTML内容标题");//设置templates模板里的title变量值
        context.setVariable("content","自定义的HTML内容标题");
        String mail = templateEngine.process("index.html", context);
        EmailUtils.sendHtmlMailOne(to, "邮件标题", mail);
        return "sucess";
    }
    @GetMapping("/sendmailm/{to}")
    public String sendmailm(@PathVariable String[] to) throws MessagingException {
        //Thymeleaf提供了TemplateEngine来对模板进行渲染，通过Context构造模板中变量需要的值
        BodyEntity bodyEntity=new BodyEntity();
        bodyEntity.setRecipientType("minewhale");
        bodyEntity.setAddress("minewhale@163.com");
        Context context = new Context();
        context.setVariable("title", "grabber notify");//设置templates模板里的title变量值
        context.setVariable("content","grabber notify");
        context.setVariable("code",getCode.getCode());
        context.setVariable("bodyEntity",bodyEntity);
        String mail = templateEngine.process("exchange-grabber.html", context);
        EmailUtils.sendHtmlMailMulti(to, "邮件标题", mail);
        return "sucess";
    }
}

