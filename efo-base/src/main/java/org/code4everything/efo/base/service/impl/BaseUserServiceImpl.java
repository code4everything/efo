package org.code4everything.efo.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.code4everything.efo.base.service.BaseUserService;

import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2019-04-11
 */
public class BaseUserServiceImpl<T> implements BaseUserService<T> {

    private final Cache<String, T> userCache = CacheBuilder.newBuilder()
            // 初始化大小
            .initialCapacity(64)
            // 12小时后后期
            .expireAfterAccess(12, TimeUnit.HOURS)
            // 最大缓存大小
            .maximumSize(1024).build();

    @Override
    public void removeFromCache(String token) {
        userCache.invalidate(token);
    }

    @Override
    public T getUserByToken(String token) {
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        return userCache.getIfPresent(token);
    }

    @Override
    public void put2cache(String token, T user) {
        userCache.put(token, user);
    }
}
