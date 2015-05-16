package com.cc.lmsfc.task.converter;

import com.cc.lmsfc.common.util.JacksonUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomchen on 15-5-15.
 */
@Component
public class RedisMessageConverter implements MessageConverter {
    @Override
    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        return null;
    }

    @Override
    public Message<?> toMessage(Object payload, MessageHeaders headers) {
        Map<String,String> map = JacksonUtil.readValue(payload.toString(), HashMap.class);
        if (payload == null) {
            return null;
        }
        if (headers != null) {
            MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(headers, MessageHeaderAccessor.class);
            if (accessor != null && accessor.isMutable()) {
                return MessageBuilder.createMessage(payload, accessor.getMessageHeaders());
            }
        }
        return MessageBuilder.withPayload(map).copyHeaders(headers).build();
    }
}
