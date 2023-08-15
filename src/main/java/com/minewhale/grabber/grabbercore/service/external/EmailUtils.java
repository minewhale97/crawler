package com.minewhale.grabber.grabbercore.service.external;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Component
public class EmailUtils extends ApplicationObjectSupport {
    /**
     * 自动配置邮件发送
     */
    private static JavaMailSender javaMailSender;

    private static ApplicationContext applicationContext = null;

    @Override
    protected void initApplicationContext(ApplicationContext context) throws BeansException {
        super.initApplicationContext(context);
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    @Autowired
    private void setJavaMailSender(JavaMailSender javaMailSender) {
        EmailUtils.javaMailSender = javaMailSender;
    }

    /**
     * 自动配置模版引擎
     */
    private static TemplateEngine templateEngine;

    @Autowired
    private void setTemplateEngine(TemplateEngine templateEngine) {
        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setCacheable(true);
        springResourceTemplateResolver.setPrefix("classpath:/templates/");
        springResourceTemplateResolver.setSuffix(".html");
        springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
        springResourceTemplateResolver.setApplicationContext(applicationContext);

        templateEngine.addTemplateResolver(springResourceTemplateResolver);
        EmailUtils.templateEngine = templateEngine;
    }

    public ITemplateResolver getClassLoaderResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setSuffix("html");
        resolver.setSuffix("templates");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    /**
     * 发送人邮箱
     */
    private static String from;

    @Value("${spring.mail.username}")
    private void setFrom(String from) {
        EmailUtils.from = from;
    }

    /**
     * 解析页
     */
    private static String html;

    @Value("${spring.mail.thymeleaf-html}")
    private void setHtml(String html) {
        EmailUtils.html = html;
    }

    /**
     * 标题
     */
    private static String subject;

    @Value("${spring.mail.subject}")
    private void setSubject(String subject) {
        EmailUtils.subject = subject;
    }

    /**
     * 普通发送邮件
     *
     * @param text
     * @param toMails
     */
    public static int sendSimpleMail(String text, String... toMails) {
        // text or toMails is null
        if (StringUtils.isEmpty(text) || ArrayUtils.isEmpty(toMails)) {
            return 0;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(subject);
            message.setFrom(from);
            message.setBcc(from);
            message.setTo(toMails);
            message.setText(text);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 发送 thymeleaf 页面邮件 - 单参数
     *
     * @param goldCoinCode
     * @param toMails
     * @throws MessagingException
     */
    public static int sendThymeleafMail(String goldCoinCode, String... toMails) {
        // goldCoinCode is null
        if (StringUtils.isEmpty(goldCoinCode)) {
            return 0;
        }
        // 发送邮件
        return sendThymeleafMail(null, goldCoinCode, toMails);
    }

    /**
     * 发送 thymeleaf 页面邮件 - 多参数
     *
     * @param map
     * @param toMails
     * @throws MessagingException
     */
    public static int sendThymeleafMail(Map<String, Object> map, String... toMails) {
        // map is null
        if (MapUtils.isEmpty(map)) {
            return 0;
        }
        // 发送邮件
        return sendThymeleafMail(map, null, toMails);
    }

    /**
     * 发送 thymeleaf 页面邮件 - 最终执行
     *
     * @param map
     * @param toMails
     * @throws MessagingException
     */
    private static int sendThymeleafMail(Map<String, Object> map, String goldCoinCode, String... toMails) {
        // toMails is null
        if (ArrayUtils.isEmpty(toMails)) {
            return 0;
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setBcc(from);
            helper.setTo(toMails);
            Context context = new Context();
            if (!MapUtils.isEmpty(map)) {
                context.setVariables(map);
            } else {
                context.setVariable("GoldCoinCode", goldCoinCode);
            }
            String process = templateEngine.process(html, context);
            helper.setText(process, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

}
