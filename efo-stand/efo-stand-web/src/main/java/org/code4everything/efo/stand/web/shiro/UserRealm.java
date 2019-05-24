package org.code4everything.efo.stand.web.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.code4everything.boot.base.constant.MessageConsts;
import org.code4everything.efo.stand.dao.domain.UserDO;
import org.code4everything.efo.stand.web.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @author pantao
 * @since 2019-04-15
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private final UserService userService;

    public UserRealm(UserService userService) {this.userService = userService;}

    /**
     * 授权，验证权限时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserDO user = (UserDO) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // TODO: 2019-04-15 获取用户权限
        return info;
    }

    /**
     * 认证，登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UserDO user = userService.getByUsernameOrEmail(((UsernamePasswordToken) token).getUsername());
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException(MessageConsts.UNKNOWN_ACCOUNT_ZH);
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName("SHA-256");
        shaCredentialsMatcher.setHashIterations(16);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
