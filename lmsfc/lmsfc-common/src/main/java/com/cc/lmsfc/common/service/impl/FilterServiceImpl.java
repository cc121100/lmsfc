package com.cc.lmsfc.common.service.impl;

import com.cc.lmsfc.common.dao.FilterDAO;
import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.filter.Filter;
import com.cc.lmsfc.common.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tomchen on 15-3-26.
 */
@Service
public class FilterServiceImpl extends BaseServiceImpl<Filter,FilterDAO,String> implements FilterService {

    @Autowired
    private FilterDAO filterDAO;

    @Override
    protected FilterDAO getRepository() {
        return filterDAO;
    }

}
