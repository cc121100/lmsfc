package com.cc.lmsfc.task;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * Created by tomchen on 15-3-16.
 */
public class StartUp {

    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring/spring-task.xml");

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring/spring-task.xml");

        MessageChannel controlChannel = ac.getBean("jmsTaskInputChl", MessageChannel.class);
        controlChannel.send(new GenericMessage<Object>("@getRedisTask.start()"));
//        controlChannel.send(new GenericMessage<Object>("@getJmsTask.start()"));


    }
}
