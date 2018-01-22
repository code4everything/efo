package com.zhazhapan.efo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IConfigService;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @date 2018/1/22
 */
@Service
public class ConfigServiceImpl implements IConfigService {

    @Override
    public String getGlobalConfig() {
        JSONObject jsonObject = EfoApplication.settings.getObjectUseEval(ConfigConsts.GLOBAL_OF_SETTINGS);
        jsonObject.remove(ConfigConsts.UPLOAD_PATH_OF_GLOBAL);
        return jsonObject.toString();
    }
}
