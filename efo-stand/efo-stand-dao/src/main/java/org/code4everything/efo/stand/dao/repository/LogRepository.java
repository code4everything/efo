package org.code4everything.efo.stand.dao.repository;

import org.code4everything.efo.stand.dao.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2019-04-16
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {}
