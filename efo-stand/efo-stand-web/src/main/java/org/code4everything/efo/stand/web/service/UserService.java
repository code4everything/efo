package org.code4everything.efo.stand.web.service;

import org.code4everything.boot.annotation.AopLog;
import org.code4everything.efo.base.model.vo.RegisterVO;
import org.code4everything.efo.base.model.vo.UserInfoVO;
import org.code4everything.efo.stand.dao.domain.User;

/**
 * @author pantao
 * @since 2019-04-11
 */
public interface UserService {

    @AopLog("检测有限是否已经注册")
    void checkEmail(String email);

    @AopLog("检测用户名是否已经注册")
    void checkUsername(String username);

    @AopLog("通过用户名或邮箱查询用户")
    User getByUsernameOrEmail(String loginName);

    @AopLog("注册用户")
    UserInfoVO register(RegisterVO registerVO);
}
