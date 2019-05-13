package org.code4everything.efo.stand.web.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.code4everything.boot.log.LogMethod;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.efo.base.constant.EfoError;
import org.code4everything.efo.base.model.vo.RegisterVO;
import org.code4everything.efo.base.model.vo.UserInfoVO;
import org.code4everything.efo.base.util.Checker;
import org.code4everything.efo.stand.dao.domain.UserDO;
import org.code4everything.efo.stand.dao.repository.UserRepository;
import org.code4everything.efo.stand.web.service.UserService;
import org.code4everything.efo.stand.web.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author pantao
 * @since 2019-04-11
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public UserDO getUserByToken(String token) {
        return null;
    }

    @Override
    @LogMethod("更新用户名")
    public void updateUsername(String username) {
        UserDO user = ShiroUtils.getUser();
        if (!user.getUsername().equals(username)) {
            checkUsername(username);
            user.setUsername(username);
            userRepository.save(user);
        }
    }

    @Override
    @LogMethod("更新邮箱")
    public void updateEmail(String email, String code) {
        Checker.checkCode(email, code);
        UserDO user = ShiroUtils.getUser();
        if (!user.getEmail().equals(email)) {
            checkEmail(email);
            user.setEmail(email);
            userRepository.save(user);
        }
    }

    @Override
    @LogMethod("检测有限是否已经注册")
    public void checkEmail(String email) {
        AssertUtils.throwIf(userRepository.existsByEmail(email), EfoError.EMAIL_EXISTS);
    }

    @Override
    @LogMethod("检测用户名是否已经注册")
    public void checkUsername(String username) {
        AssertUtils.throwIf(userRepository.existsByUsername(username), EfoError.USERNAME_EXISTS);
    }

    @Override
    @LogMethod("通过用户名或邮箱查询用户")
    public UserDO getByUsernameOrEmail(String loginName) {
        return userRepository.getByUsernameOrEmail(loginName, loginName);
    }

    @Override
    @LogMethod("注册用户")
    public UserInfoVO register(RegisterVO registerVO) {
        // 校验参数
        Checker.checkPassword(registerVO.getPassword());
        Checker.checkUsername(registerVO.getUsername());
        Checker.checkCode(registerVO.getEmail(), registerVO.getCode());

        // 检测邮箱和用户名是否已经注册
        checkUsername(registerVO.getUsername());
        checkEmail(registerVO.getEmail());

        // 创建用户
        UserDO user = registerVO.copyInto(new UserDO());
        user.setSalt(RandomUtil.randomString(6));
        user.setPassword(new SimpleHash("SHA-256", registerVO.getPassword(), user.getSalt(), 16).toString());
        user.setCreateTime(LocalDateTime.now());
        user.setNickname(user.getUsername());
        user.setGender("0");
        user.setStatus("7");
        return userRepository.save(user).copyInto(new UserInfoVO());
    }
}
