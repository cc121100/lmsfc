package com.cc.lmsfc.task.test;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.util.JacksonUtil;
import com.cc.lmsfc.task.redis.RedisService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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

    @Autowired
    private RedisTemplate redisTemplate;

//    @Test
//    public void testInitRedis(){
//        redisService.initLmsfcRedis();
//    }


    @Test
    public void testZsetAdd(){
        redisTemplate.opsForZSet().add("z2","111",0);
        redisTemplate.opsForZSet().rank("z2","111");

    }

    @Test
    public void testSendMsgToReassembel(){
        Map<String,String> map = Maps.newHashMap();
        map.put("type", CommonConsts.REASSEMBLE_TSK);
        redisTemplate.convertAndSend("lmsfc.topic.t1", JacksonUtil.toJSon(map));
    }
}
