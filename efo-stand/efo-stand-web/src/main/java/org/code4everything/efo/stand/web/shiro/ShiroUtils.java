package org.code4everything.efo.stand.web.shiro;

import org.apache.shiro.SecurityUtils;
import org.code4everything.efo.stand.dao.domain.User;

/**
 * @author pantao
 * @since 2019/4/17
 **/
public class ShiroUtils {

    private ShiroUtils() {}

    public static User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
