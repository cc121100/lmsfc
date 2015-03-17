package com.cc.lmsfc.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tomchen on 15-3-15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:*/test-spring-redis.xml"})
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> template; // inject the template as ListOperations


    @Test
    public void redisTest(){
//        template.opsForValue().set("testtest111","ccc");
        System.out.print("value:" + template.opsForValue().get("testtest"));
    }

    @Test
    public void redisTestSub(){
    }

}
