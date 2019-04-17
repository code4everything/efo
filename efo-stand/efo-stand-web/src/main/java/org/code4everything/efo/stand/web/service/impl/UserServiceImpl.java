package org.code4everything.efo.stand.web.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.code4everything.boot.annotation.AopLog;
import org.code4everything.efo.base.constant.EfoError;
import org.code4everything.efo.base.model.vo.RegisterVO;
import org.code4everything.efo.base.model.vo.UserInfoVO;
import org.code4everything.efo.base.service.impl.BaseUserServiceImpl;
import org.code4everything.efo.base.util.ExceptionUtils;
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
public class UserServiceImpl extends BaseUserServiceImpl<UserDO> implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    @AopLog("更新用户名")
    public void updateUsername(String username) {
        UserDO user = ShiroUtils.getUser();
        if (!user.getUsername().equals(username)) {
            checkUsername(username);
            user.setUsername(username);
            userRepository.save(user);
        }
    }

    @Override
    @AopLog("更新邮箱")
    public void updateEmail(String email, String code) {
        ExceptionUtils.checkCode(email, code);
        UserDO user = ShiroUtils.getUser();
        if (!user.getEmail().equals(email)) {
            checkEmail(email);
            user.setEmail(email);
            userRepository.save(user);
        }
    }

    @Override
    @AopLog("检测有限是否已经注册")
    public void checkEmail(String email) {
        EfoError error = EfoError.EMAIL_EXISTS;
        ExceptionUtils.throwIf(userRepository.existsByEmail(email), error.getCode(), error.getMsg());
    }

    @Override
    @AopLog("检测用户名是否已经注册")
    public void checkUsername(String username) {
        EfoError error = EfoError.USERNAME_EXISTS;
        ExceptionUtils.throwIf(userRepository.existsByUsername(username), error.getCode(), error.getMsg());
    }

    @Override
    @AopLog("通过用户名或邮箱查询用户")
    public UserDO getByUsernameOrEmail(String loginName) {
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
