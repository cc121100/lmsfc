package com.cc.lmsfc.task.test;

import com.cc.lmsfc.task.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tomchen on 15-5-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/spring-task.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testInitRedis(){
        redisService.initLmsfcRedis();


    }
}
