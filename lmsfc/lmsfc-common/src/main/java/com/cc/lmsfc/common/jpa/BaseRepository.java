package com.cc.lmsfc.common.jpa;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tomchen on 15-3-11.
 */

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {

    void deleteByIds(Iterable<ID> ids);
}
