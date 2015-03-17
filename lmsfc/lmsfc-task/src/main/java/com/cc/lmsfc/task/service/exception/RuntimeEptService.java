package com.cc.lmsfc.task.service.exception;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Created by tomchen on 15-3-16.
 */
@Component
public class RuntimeEptService {

    public void handler(Message<?> msg){
        //TODO handler exception if fail task job
        RuntimeException e = (RuntimeException)msg.getPayload();
//        e.printStackTrace();
        System.out.println("Exception handler: " + e.getCause());
    }
}
