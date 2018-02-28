package com.zhazhapan.efo.config;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.EfoApplicationTest;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author pantao
 * @since 2018/1/26
 */
public class SettingConfigTest {

    @Test
    public void testFileSuffixPattern() {
        EfoApplicationTest.setSettings();
        assert Pattern.compile(EfoApplication.settings.getStringUseEval(ConfigConsts.FILE_SUFFIX_MATCH_OF_SETTING)).matcher("jpg").matches();
    }

    @Test
    public void testGetStoragePath() {
        EfoApplicationTest.setSettings();
        System.out.println(SettingConfig.getStoragePath(ConfigConsts.TOKEN_OF_SETTINGS));
    }
}
