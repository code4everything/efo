package org.code4everything.efo.stand.web.shiro;

import org.apache.shiro.SecurityUtils;
import org.code4everything.efo.stand.dao.domain.UserDO;

/**
 * @author pantao
 * @since 2019/4/17
 **/
public class ShiroUtils {

    private ShiroUtils() {}

    public static UserDO getUser() {
        return (UserDO) SecurityUtils.getSubject().getPrincipal();
    }
}
