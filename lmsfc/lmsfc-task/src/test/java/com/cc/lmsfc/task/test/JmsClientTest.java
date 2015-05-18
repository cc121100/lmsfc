package com.cc.lmsfc.task.test;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.model.task.BatchArticleTaskJob;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-3-16.
 */

@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class JmsClientTest {

    public static ApplicationContext ctx = null;

    @Before
    public void setUp(){
        ctx = new ClassPathXmlApplicationContext("classpath*:spring/test-spring-task-jms.xml");
    }

//    @Test
//    public void testSendJms() throws InterruptedException {
//
//        JmsTemplate jmsTemplate = (JmsTemplate)ctx.getBean("jmsTemplate");
//        for(int i = 0; i < 10; i++){
//            jmsTemplate.send(new MessageCreator() {
//
//                public Message createMessage(Session session) throws JMSException {
//                    MapMessage mm = session.createMapMessage();
//                    mm.setString("countTemplate", new Date().toString() );
//                    return mm;
//                }
//
//            });
//            System.out.println("Send the " + i +" message." );
//            Thread.sleep(3000);
//        }
//        System.out.println("over....");
//
//    }

//    @Test
//    public void testSendBeanJms() throws InterruptedException {
//        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
//            jmsTemplate.send(new MessageCreator() {
//                @Override
//                public Message createMessage(Session session) throws JMSException {
//                    ArticleTaskJob atj = new ArticleTaskJob();
//                    atj.setId("www" +"");
//                    atj.setName("testArticleTaskJob - " + "www");
//                    atj.setCreatedDt(new Date());
//
//                    ObjectMessage om = session.createObjectMessage(atj);
//                    return om;
//                }
//            });
//
//        System.out.println("over....");
//    }

//    @Test
//    public void testSendBatchArticleJms(){
//        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
//            jmsTemplate.send(new MessageCreator() {
//                @Override
//                public Message createMessage(Session session) throws JMSException {
//                    BatchArticleTaskJob batj = new BatchArticleTaskJob();
//                    batj.setId("batj-0001");
//                    batj.setName("batj-0001-name");
//
//                    List<ArticleTaskJob> list = new ArrayList<ArticleTaskJob>();
//
//
//                    ArticleTaskJob atj1 = new ArticleTaskJob();
//                    atj1.setId("atk-0001_for_" + batj.getId());
//                    atj1.setName("testArticleTaskJob - " + "atk1-name_for_" + batj.getName());
//                    atj1.setCreatedDt(new Date());
//                    atj1.setBatchArticleTaskJob(batj);
//
//                    list.add(atj1);
//
//                    ArticleTaskJob atj2 = new ArticleTaskJob();
//                    atj2.setId("atk-0002_for_" + batj.getId());
//                    atj2.setName("testArticleTaskJob - " + "atk2-name_for_" + batj.getName());
//                    atj2.setCreatedDt(new Date());
//                    atj2.setBatchArticleTaskJob(batj);
//
//                    list.add(atj2);
//
//                    batj.setArticleTaskJobs(list);
//
//                    ObjectMessage om = session.createObjectMessage(batj);
//                    return om;
//                }
//            });
//
//        System.out.println("over....");
//    }

//    @Test
//    @Transactional
//    public void testSendArt(){
//
//        ArticleTaskJobDAO articleTaskJobDAO = (ArticleTaskJobDAO)ctx.getBean("articleTaskJobDAO");
//        ArticleTaskJob art = articleTaskJobDAO.findAll().get(0);
//        art.getBatchArticleTaskJob();
//        art.getArticleElement();
//        art.getFilterRule().getId();
//        art.getFilterRule().getFilterDetails().size();
//
//        final ArticleTaskJob finalArt = art;
//
//        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
//        jmsTemplate.send(new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                return session.createObjectMessage(finalArt);
//            }
//        });
//
//    }

//    @Test
//    public void testReassebmle(){
//        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
//
//        final Map<String,Object> map = Maps.newHashMap();
//        map.put("type", CommonConsts.REASSEMBLE_TSK);
//        jmsTemplate.send(new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                return session.createObjectMessage((java.io.Serializable) map);
//            }
//        });
//    }

    @Test
    public void testInt(){

    }

}
