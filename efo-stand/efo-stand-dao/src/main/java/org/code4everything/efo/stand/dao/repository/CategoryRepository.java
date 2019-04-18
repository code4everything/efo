package org.code4everything.efo.stand.dao.repository;

import org.code4everything.efo.stand.dao.domain.CategoryDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2019-04-18
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryDO, Integer> {}
