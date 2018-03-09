package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.config.SettingConfig;
import com.zhazhapan.efo.dao.AuthDAO;
import com.zhazhapan.efo.entity.Auth;
import com.zhazhapan.efo.model.AuthRecord;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IAuthService;
import com.zhazhapan.efo.util.BeanUtils;
import com.zhazhapan.efo.util.ServiceUtils;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pantao
 * @since 2018/2/1
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private final AuthDAO authDAO;

    @Autowired
    public AuthServiceImpl(AuthDAO authDAO) {this.authDAO = authDAO;}

    @Override
    public boolean addAuth(String files, String users, String auths) {
        if (Checker.isNotEmpty(files) && Checker.isNotEmpty(users) && Checker.isNotEmpty(auths)) {
            String[] file = files.split(ValueConsts.COMMA_SIGN);
            String[] user = users.split(ValueConsts.COMMA_SIGN);
            for (String f : file) {
                long fileId = Formatter.stringToLong(f);
                for (String u : user) {
                    int userId = Formatter.stringToInt(u);
                    if (Checker.isNull(authDAO.exists(userId, fileId))) {
                        Auth auth = new Auth(userId, fileId);
                        auth.setAuth(BeanUtils.getAuth(auths));
                        authDAO.insertAuth(auth);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean batchDelete(String ids) {
        return Checker.isNotEmpty(ids) && authDAO.batchDelete(ids);
    }

    @Override
    public boolean updateAuth(long id, String auths) {
        int[] auth = BeanUtils.getAuth(auths);
        return authDAO.updateAuthById(id, auth[0], auth[1], auth[2], auth[3], auth[4]);
    }

    @Override
    public List<AuthRecord> getAuth(String usernameOrEmail, String fileName, int offset) {
        long fileId = ServiceUtils.getFileId(fileName);
        int userId = ServiceUtils.getUserId(usernameOrEmail);
        return authDAO.getAuthBy(ValueConsts.ZERO_INT, userId, fileId, fileName, offset);
    }

    @Override
    public AuthRecord getByFileIdAndUserId(long fileId, int userId) {
        List<AuthRecord> authRecords = authDAO.getAuthBy(ValueConsts.ZERO_INT, userId, fileId, ValueConsts
                .EMPTY_STRING, ValueConsts.ZERO_INT);
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
