package com.zhazhapan.efo.service;

/**
 * @author pantao
 * @since 2018/1/22
 */
public interface IConfigService {

    /**
     * 获取全局配置
     *
     * @return {@link String}
     */
    String getGlobalConfig();

    /**
     * 获取用户配置
     *
     * @return {@link String}
     */
    String getUserConfig();
}
