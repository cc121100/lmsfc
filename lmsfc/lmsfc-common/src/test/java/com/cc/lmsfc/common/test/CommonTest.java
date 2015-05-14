package com.cc.lmsfc.common.test;

import com.cc.lmsfc.common.dao.ArticleCategoryDAO;
import com.cc.lmsfc.common.dao.ArticleDAO;
import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.dao.FilterDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.filter.Filter;
import com.cc.lmsfc.common.service.FilterService;
import com.cc.lmsfc.common.service.UserService;
import com.cc.lmsfc.common.specification.ArticleSpec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomchen on 15-3-11.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:*/test-spring-common.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class CommonTest {

//    @Autowired
//    private TestModelDAO testModelDAO;

    @Autowired
    private ArticleTaskJobDAO articleTaskJobDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private FilterDAO filterDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
//    private RedisTemplate<String, String> template; // inject the template as ListOperations

//    @Test
//    public void test1(){
//        TestModel testModel = new TestModel();
//        testModel.setName("kk3kk2");
//
//        testModelDAO.saveAndFlush(testModel);
//    }
//
//    @Test
//    public void test2(){
//
//        TestModel testModel = new TestModel();
////        testModel.setName("ame1");
////        testModel.setCreatedDt(new Date());
//
//        PageRequest pq = new PageRequest(0,1);
//        Page<TestModel> page = testModelDAO.findAll(new TestModelSpfc().condition1(testModel),pq);
//
//        System.out.println(page.getTotalElements());
//    }

    @Test
    public void test3(){
//        User user = userService.getById("402868cc4c4e8e0d014c4e8e18390007");
//        System.err.println("==================:" + user.getUserName());

        PageRequest pr = new PageRequest(0,10, Sort.Direction.ASC,"filterName");
        Page<Filter> page = filterService.findAll(pr);
        System.err.println("Total count: " + page.getTotalElements());
        System.err.println("Total page: " + page.getTotalPages() );
        System.err.println("current page: " +page.getNumber());
        System.err.println("current page element count:" + page.getNumberOfElements());
        System.err.println("page size: " + page.getSize());
        for(Filter f : page.getContent()){
            System.err.println("........ " + f.getFilterName());
        }
        System.err.println();
    }

    @Test
    public void testFilterDelete(){
        String[] ids = new String[]{"4028819d474d2dbc01474d2dccb4000k","4028819d474d2dbc01474d2dccb4000i"};
        filterService.deleteByIds(ids);
//        filterDAO.delete(ids);
    }

    @Test
    public void testUpdateAtjState(){
        articleTaskJobDAO.updateArticleState("8ee483b14c6e5234014c6e56a3340000",110);
    }

    @Test
    public void testAddUrlColumnForArt(){
        articleDAO.findAll();
    }

    @Test
    public void testSpec(){

//        Page<Article> page = articleDAO.findAll(new ArticleSpec().findByCategory("402868cf4c364e2d014c364e37a4000d"), new PageRequest(1, 10));
//        System.out.println(page.getContent().size());
    }

}
