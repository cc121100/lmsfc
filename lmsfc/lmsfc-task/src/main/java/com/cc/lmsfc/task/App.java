package com.cc.lmsfc.task;

import com.cc.lmsfc.common.model.TestModel;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.springframework.integration.handler.MessageHandlerChain;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomchen on 15-3-13.
 */
@Component
public class App implements MessageHandler{

//    @Override
//    public void handleMessage(Message<?> message) throws MessagingException {
//        Map<String,String> map = (HashMap < String, String >)message.getPayload();
//        System.err.println("message receive: " + map.get("countTemplate"));
//
//    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        ArticleTaskJob atj = (ArticleTaskJob)message.getPayload();
        System.err.println("ArticleTaskJob received: " + atj.getId() + " - " + atj.getName() + " - " + atj.getCreatedDt());

    }

//    protected void handleMessageInternal(Message<?> message) throws Exception {
//        Map<String,String> map = (HashMap < String, String >)message.getPayload();
//        System.err.println("message receive: " + map.get("countTemplate"));
//    }
//
//    public void console(Message<?> msg){
//        Map<String,String> map = (HashMap < String, String >)msg.getPayload();
//        System.err.println("message receive: " + map.get("countTemplate"));
//    }


}
