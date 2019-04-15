package org.code4everything.efo.stand.web.service.impl;

import org.code4everything.efo.base.constant.ErrorCode;
import org.code4everything.efo.base.service.impl.BaseUserServiceImpl;
import org.code4everything.efo.base.util.ExceptionUtils;
import org.code4everything.efo.stand.dao.repository.UserRepository;
import org.code4everything.efo.stand.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2019-04-11
 */
@Service
public class UserServiceImpl extends BaseUserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public void checkEmail(String email) {
        ExceptionUtils.throwIf(userRepository.existsByEmail(email), ErrorCode.EMAIL_EXISTS, "邮箱已存在");
    }

    @Override
    public void checkUsername(String username) {
        ExceptionUtils.throwIf(userRepository.existsByUsername(username), ErrorCode.USERNAME_EXISTS, "用户名已存在");
    }
}
