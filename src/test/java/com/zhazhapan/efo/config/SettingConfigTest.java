package com.zhazhapan.efo.config;

import com.zhazhapan.efo.EfoApplicationTest;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import org.junit.Test;

/**
 * @author pantao
 * @date 2018/1/26
 */
public class SettingConfigTest {

    @Test
    public void testGetStoragePath() {
        EfoApplicationTest.setSettings();
        System.out.println(SettingConfig.getStoragePath(ConfigConsts.TOKEN_OF_SETTINGS));
    }
}
