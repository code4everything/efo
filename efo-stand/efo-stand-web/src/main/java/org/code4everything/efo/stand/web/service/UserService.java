package org.code4everything.efo.stand.web.service;

import org.code4everything.efo.base.model.vo.RegisterVO;
import org.code4everything.efo.base.model.vo.UserInfoVO;
import org.code4everything.efo.stand.dao.domain.User;

/**
 * @author pantao
 * @since 2019-04-11
 */
public interface UserService {

    void checkEmail(String email);

    void checkUsername(String username);

    User getByUsernameOrEmail(String loginName);

    UserInfoVO register(RegisterVO registerVO);
}
