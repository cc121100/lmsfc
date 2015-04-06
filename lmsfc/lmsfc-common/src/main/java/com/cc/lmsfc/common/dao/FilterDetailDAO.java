package com.cc.lmsfc.common.dao;

import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.filter.FilterDetail;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tomchen on 15-3-17.
 */
public interface FilterDetailDAO extends BaseRepository<FilterDetail,String>{

    @Query(value = "select fd from FilterDetail fd where fd.category='innercss' or fd.category='outercss' order by fd.category")
    List<FilterDetail> findCommonFilterDetais();
}
