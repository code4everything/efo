package org.code4everything.efo.stand.web.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.code4everything.boot.annotation.AopLog;
import org.code4everything.efo.base.constant.ErrorCode;
import org.code4everything.efo.base.model.vo.RegisterVO;
import org.code4everything.efo.base.model.vo.UserInfoVO;
import org.code4everything.efo.base.service.impl.BaseUserServiceImpl;
import org.code4everything.efo.base.util.ExceptionUtils;
import org.code4everything.efo.stand.dao.domain.User;
import org.code4everything.efo.stand.dao.repository.UserRepository;
import org.code4everything.efo.stand.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public User getByUsernameOrEmail(String loginName) {
        return userRepository.getByUsernameOrEmail(loginName, loginName);
    }

    @Override
    @AopLog("注册用户")
    public UserInfoVO register(RegisterVO registerVO) {
        // 校验参数
        ExceptionUtils.checkPassword(registerVO.getPassword());
        ExceptionUtils.checkUsername(registerVO.getUsername());
        ExceptionUtils.checkCode(registerVO.getEmail(), registerVO.getCode());

        checkUsername(registerVO.getUsername());
        checkEmail(registerVO.getEmail());

        // 创建用户
        User user = registerVO.copyInto(new User());
        user.setSalt(RandomUtil.randomString(6));
        user.setPassword(new SimpleHash("SHA-256", registerVO.getPassword(), user.getSalt(), 16).toString());
        user.setCreateTime(LocalDateTime.now());
        user.setNickname(user.getUsername());
        user.setGender("0");
        user.setStatus("7");
        return userRepository.save(user).copyInto(new UserInfoVO());
    }
}
