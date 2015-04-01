package com.cc.lmsfc.common.jpa;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tomchen on 15-3-11.
 */
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private static Logger logger = Logger.getLogger(BaseRepositoryImpl.class);

    private final EntityManager em;


    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                              EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }



    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        //super(domainClass, em);
        this(JpaEntityInformationSupport.getMetadata(domainClass, entityManager), entityManager);
    }

//    @Transactional
    @Override
    public void deleteByIds(Iterable<ID> ids) {
        String query = "delete from " + getDomainClass().getSimpleName() + " c where c.id in :param";

        em.createQuery(query)
                .setParameter("param", ids).executeUpdate();
    }
}
