package com.cc.lmsfc.common.service;

import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.BaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by tomchen on 15-3-26.
 */
public interface BaseService<T extends BaseModel,R extends BaseRepository,ID extends Serializable> {

    T getById(ID id);

    void deleteById(ID id);

    void deleteByIds(ID[] ids);

    void update(T t) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void save(T t);


    List<T> findAll();

    Page<T> findAll(Pageable p);

    List<T> findALL(Specification<T> s);

    Page<T> findAll(Specification<T> s, Pageable p);

}
