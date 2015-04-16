package com.cc.lmsfc.common.service.impl;

import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.BaseModel;
import com.cc.lmsfc.common.service.BaseService;
import com.cc.lmsfc.common.util.ReflectUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tomchen on 15-3-26.
 */
public abstract class BaseServiceImpl<T extends BaseModel,R extends BaseRepository,ID extends Serializable> implements BaseService<T,R,ID> {


    protected Class<T> modelClass;


    protected abstract R getRepository();

    public BaseServiceImpl() {
        modelClass = ReflectUtils.findParameterizedType(this.getClass());
    }

    @Override
    public T getById(ID id) {
        return (T)getRepository().findOne(id);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public void deleteById(ID id) {
        getRepository().delete(id);
    }

    @Override
    public void deleteByIds(ID[] ids) {
//        List<T> list = new ArrayList<>();
//        try{
//            for(ID id : ids){
//                T t = null;
//                t = modelClass.newInstance();
//                BeanUtils.setProperty(t,"id",id);
//                list.add(t);
//            }
//
//            getRepository().delete(list);
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }

        getRepository().deleteByIds(new ArrayList<ID>(Arrays.asList(ids)));
    }

    @Override
    public void update(T t) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T oldT = (T)getRepository().findOne(BeanUtils.getProperty(t,"id"));

        String[] properties = (String[]) modelClass.getMethod("getProperties").invoke(t);

        for(String str : properties){
            String s = str.substring(0, 1).toUpperCase() + str.substring(1);
            Object obj = modelClass.getMethod("get"+s).invoke(t);
            if(obj != null){
                BeanUtils.copyProperty(oldT,str,obj);
            }
        }
        getRepository().saveAndFlush(oldT);
    }

    @Override
    public void save(T t) {
        getRepository().saveAndFlush(t);
    }

    @Override
    public Page<T> findAll(Pageable p) {
        return getRepository().findAll(p);
    }

    @Override
    public List<T> findALL(Specification<T> s) {
        return getRepository().findAll(s);
    }

    @Override
    public Page<T> findAll(Specification<T> s, Pageable p) {
        return getRepository().findAll(s,p);
    }
}
