package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.config.SettingConfig;
import com.zhazhapan.efo.dao.AuthDAO;
import com.zhazhapan.efo.entity.Auth;
import com.zhazhapan.efo.model.AuthRecord;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IAuthService;
import com.zhazhapan.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pantao
 * @date 2018/2/1
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    AuthDAO authDAO;

    @Override
    public AuthRecord getByFileId(long fileId) {
        List<AuthRecord> authRecords = authDAO.getAuthBy(0, 0, fileId, 0);
        if (Checker.isNotEmpty(authRecords)) {
            return authRecords.get(0);
        }
        return null;
    }

    @Override
    public boolean insertDefaultAuth(int userId, long fileId) {
        int[] defaultAuth = SettingConfig.getAuth(ConfigConsts.AUTH_DEFAULT_OF_SETTING);
        Auth auth = new Auth(userId, fileId);
        auth.setAuth(defaultAuth[0], defaultAuth[1], defaultAuth[2], defaultAuth[3], defaultAuth[4]);
        return insertAuth(auth);
    }

    @Override
    public boolean insertAuth(Auth auth) {
        return authDAO.insertAuth(auth);
    }

    @Override
    public boolean removeByFileId(long fileId) {
        return authDAO.removeAuthByFileId(fileId);
    }
}
