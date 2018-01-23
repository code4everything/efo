package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.service.ICommonService;
import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.RandomUtils;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @date 2018/1/23
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Override
    public boolean sendVerifyCode(String email) {
        int code = RandomUtils.getRandomInteger(100000, 999999);
        try {
            MailSender.sendMail(email, "请查收您的验证码", "<p>您的验证码：" + code + "</p><br/><br/><p>如非本人操作，请忽略本条消息。</p>");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
