package com.cc.lmsfc.admin.util;

import com.cc.lmsfc.common.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by tomchen on 15-5-17.
 */

public class RedisUtils {

    public static void sendTaskToRun(RedisTemplate redisTemplate, Map<String,Object> map){
        redisTemplate.convertAndSend("lmsfc.topic.t1", JacksonUtil.toJSon(map));
    }

}
