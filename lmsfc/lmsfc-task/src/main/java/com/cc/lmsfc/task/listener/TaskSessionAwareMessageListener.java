package com.cc.lmsfc.task.listener;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by tomchen on 15-4-1.
 */
public class TaskSessionAwareMessageListener implements SessionAwareMessageListener {
    @Override
    public void onMessage(Message message, Session session) throws JMSException {

    }
}
