package com.cc.lmsfc.task.test;

import com.cc.lmsfc.common.dao.*;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import com.cc.lmsfc.common.model.filter.Filter;
import com.cc.lmsfc.common.model.filter.FilterDetail;
import com.cc.lmsfc.common.model.filter.FilterRule;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.model.task.BatchArticleTaskJob;
import com.cc.lmsfc.common.util.SpringPropertiesUtil;
import com.cc.lmsfc.task.constant.TaskConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tomchen on 15-3-17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/spring-common.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class DataInitTest {

    @Autowired
    private FilterDAO filterDAO;

    @Autowired
    private FilterRuleDAO filterRuleDAO;

    @Autowired
    private FilterDetailDAO filterDetailDAO;

    @Autowired
    private ArticleTaskJobDAO articleTaskJobDAO;

    @Autowired
    private BatchArticleTaskJobDAO batchArticleTaskJobDAO;

    @Autowired
    private ArticleElementDAO articleElementDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Test
    public void initTaskJobTestData(){

        List<Filter> filterList = filterDAO.findAll();

        List<FilterDetail> filterDetails = new ArrayList<>();

        FilterDetail d1 = new FilterDetail();
        d1.setCategory("title");
        d1.setFilter(getFilter(filterList, "HasParentFilter"));
        d1.setSubNum(1);


        FilterDetail d3 = new FilterDetail();
        d3.setCategory("title");
        d3.setSubNum(2);
        d3.setFilter(getFilter(filterList, "HasAttributeFilter"));
        d3.setParamValue1("id");
        d3.setParamValue2("cb_post_title_url");
        d3.setParentNode(d1);

        filterDetails.add(d1);
        filterDetails.add(d3);

        //content
        FilterDetail dc1 = new FilterDetail();
        dc1.setCategory("content");
        dc1.setFilter(getFilter(filterList,"AndFilter"));
        dc1.setSubNum(1);

        FilterDetail dc2 = new FilterDetail();
        dc2.setCategory("content");
        dc2.setSubNum(2);
        dc2.setFilter(getFilter(filterList,"TagNameFilter"));
        dc2.setParamValue1("div");
        dc2.setParentNode(dc1);

        FilterDetail dc3 = new FilterDetail();
        dc3.setCategory("content");
        dc3.setSubNum(2);
        dc3.setFilter(getFilter(filterList,"HasAttributeFilter"));
        dc3.setParamValue1("class");
        dc3.setParamValue2("postBody");
        dc3.setParentNode(dc1);

        filterDetails.add(dc1);
        filterDetails.add(dc2);
        filterDetails.add(dc3);

        //outercss

        FilterDetail do1 = new FilterDetail();
        do1.setCategory("outercss");
        do1.setFilter(getFilter(filterList,"AndFilter"));
        do1.setSubNum(1);

        FilterDetail do2 = new FilterDetail();
        do2.setCategory("outercss");
        do2.setSubNum(2);
        do2.setFilter(getFilter(filterList,"TagNameFilter"));
        do2.setParamValue1("link");
        do2.setParentNode(do1);

        FilterDetail do3 = new FilterDetail();
        do3.setCategory("outercss");
        do3.setSubNum(2);
        do3.setFilter(getFilter(filterList,"HasAttributeFilter"));
        do3.setParamValue1("type");
        do3.setParamValue2("text/css");
        do3.setParentNode(do1);

        filterDetails.add(do1);
        filterDetails.add(do2);
        filterDetails.add(do3);

        //innercss
        FilterDetail di1 = new FilterDetail();
        di1.setCategory("innercss");
        di1.setFilter(getFilter(filterList,"AndFilter"));
        di1.setSubNum(1);

        FilterDetail di2 = new FilterDetail();
        di2.setCategory("innercss");
        di2.setSubNum(2);
        di2.setFilter(getFilter(filterList,"TagNameFilter"));
        di2.setParamValue1("style");
        di2.setParentNode(di1);

        FilterDetail di3 = new FilterDetail();
        di3.setCategory("innercss");
        di3.setSubNum(2);
        di3.setFilter(getFilter(filterList,"HasAttributeFilter"));
        di3.setParamValue1("type");
        di3.setParamValue2("text/css");
        di3.setParentNode(di1);

        filterDetails.add(di1);
        filterDetails.add(di2);
        filterDetails.add(di3);

        //image

        FilterRule r1 = new FilterRule();
        r1.setName("Filter Rule for task job1");
        r1.setFilterDetails(filterDetails);
        r1.setSourceDomain("http://www.cnblogs.com");

        for(FilterDetail fd : filterDetails){
           fd.setFilterRule(r1);
        }


        ArticleTaskJob art = new ArticleTaskJob();
//        art.setId(UUID.randomUUID().toString().replaceAll("-",""));
        art.setName("Test article task job1");
        art.setState(0);
        art.setType(0);
        art.setFilterRule(r1);
        art.setUrl("http://www.cnblogs.com/huang0925/p/4069921.html");

//        r1.setArticleTaskJob(art);

//        articleTaskJobDAO.saveAndFlush(art);
        filterRuleDAO.saveAndFlush(r1);

        ArticleCategory articleCategory1 = new ArticleCategory();
        articleCategory1.setName("默认分类");
        articleCategory1.setPathName("default");
        articleCategoryDAO.saveAndFlush(articleCategory1);

        ArticleCategory articleCategory2 = new ArticleCategory();
        articleCategory2.setName("我的分类");
        articleCategory2.setPathName("mycategory1");
        articleCategoryDAO.saveAndFlush(articleCategory2);


    }

    @Test
    public void deleteTestData(){

        filterDetailDAO.deleteAll();
        filterRuleDAO.deleteAll();
        articleTaskJobDAO.deleteAll();

        articleElementDAO.deleteAll();

        articleDAO.deleteAll();
        articleCategoryDAO.deleteAll();
    }

    @Test
    public void testGet(){
        ArticleTaskJob art = articleTaskJobDAO.findAll().get(0);
        art.getBatchArticleTaskJob();
        art.getArticleElement();
        art.getFilterRule().getId();
        art.getFilterRule().getFilterDetails().size();


    }

    public Filter getFilter(List<Filter> list,String filterName){
        for(Filter f :list){
            if(f.getFilterName().equals(filterName)){
                return f;
            }

        }
        return null;
    }

    @Test
    public void testtt(){
//        System.err.println("temp: " + TaskConstants.TEMP_FLODER);
//        System.err.println("name: " + TaskConstants.NAME);
//
//        System.err.println("temp:" + SpringPropertiesUtil.getProperty("TEMP_FLODER"));
    }

    @Test
    public void testAddCategory(){
        ArticleCategory cateJava = new ArticleCategory();
        cateJava.setName("Java");
        cateJava.setPathName("java");
        articleCategoryDAO.saveAndFlush(cateJava);

        ArticleCategory cateJavaee = new ArticleCategory();
        cateJavaee.setName("Java EE");
        cateJavaee.setPathName("javaee");
        articleCategoryDAO.saveAndFlush(cateJavaee);

        ArticleCategory cateDB = new ArticleCategory();
        cateDB.setName("数据库");
        cateDB.setPathName("db");
        articleCategoryDAO.saveAndFlush(cateDB);

    }
}
