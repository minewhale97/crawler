package com.minewhale.grabber.grabbercore.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    //用于打印日志
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);//用当前类做参数表明打印日志时，将使用当前类名作为开头

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from; // 发件方的邮箱

    //参数依次为：收件方的邮箱、邮件主题、邮件内容
    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败：" + e.getMessage());
        }
    }

//    public void send(EmailDetails theDetails) {
//        String subscriptionId = theDetails.getSubscription().toUnqualifiedVersionless().getValue();
//        ourLog.info("Sending email for subscription {} to recipients: {}", subscriptionId, theDetails.getTo());
//        StopWatch sw = new StopWatch();
//
//        StringTemplateResolver templateResolver = new StringTemplateResolver();
//        templateResolver.setTemplateMode(TemplateMode.TEXT);
//
//        SpringStandardDialect dialect = new SpringStandardDialect();
//        dialect.setEnableSpringELCompiler(true);
//
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setDialect(dialect);
//        engine.setEnableSpringELCompiler(true);
//        engine.setTemplateResolver(templateResolver);
//
//        Context context = new Context();
//
//        String body = engine.process(theDetails.getBodyTemplate(), context);
//        String subject = engine.process(theDetails.getSubjectTemplate(), context);
//
//        MimeMessage email = mySender.createMimeMessage();
//
//        try {
//            email.setFrom(trim(theDetails.getFrom()));
//            email.setRecipients(Message.RecipientType.TO, toTrimmedCommaSeparatedString(theDetails.getTo()));
//            email.setSubject(subject);
//            email.setText(body);
//            email.setSentDate(new Date());
//            email.addHeader("X-FHIR-Subscription", subscriptionId);
//        } catch (MessagingException e) {
//            throw new InternalErrorException("Failed to create email messaage", e);
//        }
//
//        mySender.send(email);
//
//        ourLog.info("Done sending email (took {}ms)", sw.getMillis());
//    }
}
