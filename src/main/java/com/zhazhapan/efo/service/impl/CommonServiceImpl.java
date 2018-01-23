package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.service.ICommonService;
import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @date 2018/1/23
 */
@Service
public class CommonServiceImpl implements ICommonService {

    private static Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Override
    public int sendVerifyCode(String email) {
        int code = RandomUtils.getRandomInteger(100000, 999999);
        try {
            MailSender.sendMail(email, "请查收您的验证码", "<p>您的验证码：" + code + "</p><br/><br/><p>如非本人操作，请忽略本条消息。</p>");
            return code;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }
}
