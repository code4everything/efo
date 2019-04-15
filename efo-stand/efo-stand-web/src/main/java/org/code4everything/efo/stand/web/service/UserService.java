package org.code4everything.efo.stand.web.service;

import org.code4everything.boot.annotation.AopLog;

/**
 * @author pantao
 * @since 2019-04-11
 */
public interface UserService {

    @AopLog("检测有限是否已经注册")
    void checkEmail(String email);

    @AopLog("检测用户名是否已经注册")
    void checkUsername(String username);
}
