package com.minewhale.grabber.grabbercore.external;

import com.minewhale.grabber.grabbercore.GrabberCoreApplicationBaseTest;
import com.minewhale.grabber.grabbercore.service.external.EmailUtils;
import com.minewhale.grabber.grabbercore.service.external.MailService;
import org.junit.Test;

import javax.annotation.Resource;

public class MailServiceTest extends GrabberCoreApplicationBaseTest {
    @Resource
    private MailService mailService;


    @Test
    public void testSendingMail() {
        mailService.sendMail("minewhale@qq.com", "邮箱测试 no-reply", "邮箱发件测试");
    }

    @Test
    public void testSendingMailU() {
        EmailUtils.sendThymeleafMail("zmw", "minewhale@qq.com");
    }
}
