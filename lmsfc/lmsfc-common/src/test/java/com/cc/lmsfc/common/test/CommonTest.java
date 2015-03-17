package com.cc.lmsfc.common.test;

import com.cc.lmsfc.common.dao.TestModelDAO;
import com.cc.lmsfc.common.jpa.specification.TestModelSpfc;
import com.cc.lmsfc.common.model.TestModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by tomchen on 15-3-11.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:*/test-spring-common.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class CommonTest {

    @Autowired
    private TestModelDAO testModelDAO;

    @Autowired
    private RedisTemplate<String, String> template; // inject the template as ListOperations

    @Test
    public void test1(){
        TestModel testModel = new TestModel();
        testModel.setName("kk3kk2");

        testModelDAO.saveAndFlush(testModel);
    }

    @Test
    public void test2(){

        TestModel testModel = new TestModel();
//        testModel.setName("ame1");
//        testModel.setCreatedDt(new Date());

        PageRequest pq = new PageRequest(0,1);
        Page<TestModel> page = testModelDAO.findAll(new TestModelSpfc().condition1(testModel),pq);

        System.out.println(page.getTotalElements());
    }




}
