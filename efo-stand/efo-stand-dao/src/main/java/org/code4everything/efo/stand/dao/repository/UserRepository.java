package org.code4everything.efo.stand.dao.repository;

import org.code4everything.efo.stand.dao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2019-04-11
 */
@Repository
public interface UserRepository extends JpaRepository<Long, User> {}
