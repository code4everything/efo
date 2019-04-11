package org.code4everything.efo.base.service;

import org.code4everything.boot.service.BootUserService;

/**
 * @author pantao
 * @since 2019-04-11
 */
public interface BaseUserService<T> extends BootUserService<T> {

    void removeFromCache(String token);

    void put2cache(String token, T user);
}
