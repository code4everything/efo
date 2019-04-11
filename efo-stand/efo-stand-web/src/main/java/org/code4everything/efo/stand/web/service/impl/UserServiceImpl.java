package org.code4everything.efo.stand.web.service.impl;

import org.code4everything.efo.base.service.impl.BaseUserServiceImpl;
import org.code4everything.efo.stand.dao.repository.UserRepository;
import org.code4everything.efo.stand.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2019-04-11
 */
@Service
public class UserServiceImpl extends BaseUserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {this.userRepository = userRepository;}
}
