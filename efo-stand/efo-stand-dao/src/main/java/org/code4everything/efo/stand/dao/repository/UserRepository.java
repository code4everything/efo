package org.code4everything.efo.stand.dao.repository;

import org.code4everything.efo.stand.dao.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2019-04-11
 */
@Repository
public interface UserRepository extends JpaRepository<UserDO, Long> {

    UserDO getByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
