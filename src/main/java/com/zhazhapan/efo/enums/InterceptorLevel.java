package com.zhazhapan.efo.enums;

/**
 * 拦截级别
 *
 * @author pantao
 * @since 2018/1/25
 */
public enum InterceptorLevel {
    /**
     * 不拦截
     */
    NONE,

    /**
     * 用户级别拦截
     */
    USER,

    /**
     * 管理员级别拦截
     */
    ADMIN,

    /**
     * 系统用户
     */
    SYSTEM
}
