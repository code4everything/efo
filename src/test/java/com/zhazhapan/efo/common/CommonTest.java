package com.zhazhapan.efo.common;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.EfoApplicationTest;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.util.MailSender;
import org.junit.Test;

/**
 * @author pantao
 * @date 2018/1/23
 */
public class CommonTest {

    @Test
    public void testJson() {
        EfoApplicationTest.setSettings();
        System.out.println(EfoApplication.settings.getBooleanUseEval(ConfigConsts.USER_AUTH_OF_SETTINGS[0]));
    }

    @Test
    public void testSendEmail() throws Exception {
        EfoApplicationTest.setSettings();
        MailSender.config(EfoApplication.settings.getObjectUseEval(ConfigConsts.EMAIL_CONFIG_OF_SETTINGS));
        MailSender.sendMail("tao@zhazhapan.com", "test", "test");
    }
}
