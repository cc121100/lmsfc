package com.cc.lmsfc.common.jpa.specification;

import com.cc.lmsfc.common.model.TestModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tomchen on 15-3-12.
 */
public class TestModelSpfc {

    public Specification<TestModel> condition1(final TestModel tm){
        return new Specification<TestModel>() {
            @Override
            public Predicate toPredicate(Root<TestModel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(tm.getName())){
                    list.add(cb.like(root.get("name").as(String.class), "%" + tm.getName()));
                }
                if (tm.getCreatedDt() != null){
                    list.add(cb.lessThan(root.get("createdDt").as(Date.class), tm.getCreatedDt()));
                }
                Predicate[] p = new Predicate[list.size()];

                return cb.and(list.toArray(p));
            }

        };
    }
}
