package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.config.SettingConfig;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.efo.service.ICommonService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author pantao
 * @since 2018/1/23
 */
@Service
public class CommonServiceImpl implements ICommonService {

    private static final String EMAIL_TITLE = "请查收您的验证码";
    private static Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Override
    public int sendVerifyCode(String email) {
        int code = RandomUtils.getRandomInteger(ValueConsts.VERIFY_CODE_FLOOR, ValueConsts.VERIFY_CODE_CEIL);
        String content = "<p>您的验证码：" + code + "</p><br/><br/><p>如非本人操作，请忽略本条消息。</p>";
        try {
            MailSender.sendMail(email, EMAIL_TITLE, content);
            return code;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public String uploadAvatar(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            String name = RandomUtils.getRandomStringOnlyLowerCase(ValueConsts.SIXTEEN_INT) + ValueConsts.DOT_SIGN +
                    FileExecutor.getFileSuffix(multipartFile.getOriginalFilename());
            if (Checker.isImage(name) && multipartFile.getSize() < ValueConsts.MB * DefaultValues.TWO_INT) {
                String path = SettingConfig.getAvatarStoragePath() + ValueConsts.SEPARATOR + name;
                try {
                    FileExecutor.writeByteArrayToFile(new File(path), multipartFile.getBytes());
                    return name;
                } catch (IOException e) {
                    logger.error("upload avatar error: " + e.getMessage());
                }
            }
        }
        return "";
    }
}
