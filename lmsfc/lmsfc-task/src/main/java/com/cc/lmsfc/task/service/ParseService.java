package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.model.filter.Filter;
import com.cc.lmsfc.common.model.filter.FilterDetail;
import com.cc.lmsfc.common.model.filter.FilterRule;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-3-17.
 */
@Component
public class ParseService {

    private static Logger logger = Logger.getLogger(ParseService.class);


    public ArticleTaskJob parse(ArticleTaskJob art){
        logger.info("Start parse article for " + art.getName());

        try {
            //1 get NodeFilter for title/content/innercss/outercss
            Map<String,NodeFilter> nodeFilterMap =  generateNodeFilter(art.getFilterRule());

            //2 get html of each
            byte[] bytes = (byte[])art.getTempMap().get("respBytes");
            parseForEach(bytes, art.getTempMap(), nodeFilterMap);
            logger.info("Finish parse article.");
            return art;

        } catch (Exception e) {
            //todo handler exception
            e.printStackTrace();
        }
        return null;

    }

    private void parseForEach(byte[] htmlBytes, Map<String,Object> resultMap, Map<String,NodeFilter> nodeMap) throws ParserException, UnsupportedEncodingException {

        if(nodeMap == null || nodeMap.isEmpty()){
            logger.error("NodeMap is null or empty when parse.");
            return;
        }

        //empty bytes release memory
        resultMap.clear();

        String htmlStr = new String(htmlBytes,"UTF-8");

        Parser parser = new Parser();
        parser.setEncoding("UTF-8");

        for(Map.Entry<String,NodeFilter> entry : nodeMap.entrySet()){
            parser.setResource(htmlStr);

            String category = entry.getKey();
            NodeFilter nodeFilter = entry.getValue();

            String html = parser.parse(nodeFilter).toHtml();

            resultMap.put(category,html);
        }

        //set bytes empty

    }

    private Map<String,NodeFilter> generateNodeFilter(FilterRule filterRule) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, NodeFilter> resultMap = new HashMap<>();

        Map<String,List<FilterDetail>> filterDetailMap = filterRule.formatFilterDetailMap();

        for(Map.Entry<String,List<FilterDetail>> entry : filterDetailMap.entrySet()){
            String category = entry.getKey();
            List<FilterDetail> filterDetailsForCate = entry.getValue();
            if(CollectionUtils.isEmpty(filterDetailsForCate)){
                logger.error("Error occurs when parse html, filterDetail list is null or empty for filterRule: " + filterRule.getName());
                return null;
            }

            Map<FilterDetail,NodeFilter> tempMap = new HashMap<>();

            for (FilterDetail spfDetail : filterDetailsForCate) {
                Filter filter = spfDetail.getFilter();
                //logger.info("Start generate filter [" + filter.getFilterName() + ", subNum=" + spfDetail.getSubNum() + "] for sourcePageFilter [" + spFilter.getSourcePageFilterName() + "]");

                Class<NodeFilter> filterClass = (Class<NodeFilter>) Class.forName(filter.getFilterClassName());

                if ("Construct".equals(filter.getSetParamMethodName())) {
                    String[] classParams = filter.getFilterClassParams().split(",");
                    if (classParams == null || classParams.length < 1) {
                        logger.error("Error occurs when get class params, null or empty");
                        return null;
                    }

                    Class[] classParamsClass = new Class[classParams.length];
                    for (int i = 0; i < classParams.length; i++) {
                        classParamsClass[i] = Class.forName(classParams[i]);
                    }

                    Constructor<NodeFilter> cons = filterClass.getConstructor(classParamsClass);
                    NodeFilter node = null;

                    if (StringUtils.isNotEmpty(spfDetail.getParamValue1()) && StringUtils.isNotEmpty(spfDetail.getParamValue2())) {
                        node = cons.newInstance(spfDetail.getParamValue1(),spfDetail.getParamValue2());
                    } else if (StringUtils.isNotEmpty(spfDetail.getParamValue1())) {
                        node = cons.newInstance(spfDetail.getParamValue1());
                    } else {

                        List<NodeFilter> childNodelist = new ArrayList<>();
                        for (Map.Entry<FilterDetail, NodeFilter> exsitedEntry : tempMap.entrySet()) {
                            if (spfDetail.getId().equals(exsitedEntry.getKey().getParentNode().getId())) {
                                childNodelist.add(exsitedEntry.getValue());
                            }
                        }

                        if (childNodelist.size() < 1) {
                            logger.error("Error orrurs when get child node for parent node");
                            return null;
                        } else if (childNodelist.size() == 1) {
                            node = cons.newInstance(childNodelist.get(0));
                        } else {
                            NodeFilter[] nodeFilterArr = new NodeFilter[childNodelist.size()];
                            Object[] objs = { childNodelist.toArray(nodeFilterArr) };
                            node = cons.newInstance(objs);
                        }
                    }

                    tempMap.put(spfDetail, node);

                } else {
                    //TODO use SET method to generate
                }
                //logger.info("End generate filter [" + filter.getFilterName() + ", subNum=" + spfDetail.getSubNum() + "] for sourcePageFilter [" + spFilter.getSourcePageFilterName() + "]");
            }

            NodeFilter nodeFilter = null;
            for (Map.Entry<FilterDetail, NodeFilter> e : tempMap.entrySet()) {
                if (e.getKey().getSubNum() == 1) {
                    nodeFilter = e.getValue();
                    resultMap.put(category,nodeFilter);
                    break;
                }
            }

        }

        return resultMap;
    }

//    public NodeFilter generateNodeFilterForSourcePageFilter(SourcePageFilter spFilter) throws Exception{
//
//        Map<SourcePageFilterDetail, NodeFilter> resultMap= new LinkedHashMap<SourcePageFilterDetail, NodeFilter>();
//        List<SourcePageFilterDetail> sourcePageFilterDetails = spFilter.getSourcePageFilterDetails();
//
//        if (sourcePageFilterDetails == null || sourcePageFilterDetails.size() < 1) {
//            logger.error("Error occurs when parse html, sourcePageFilterDetail list is null or empty for sourcePageFilter: " + spFilter.getSourcePageFilterName());
//            return null;
//        }
//
//        // sort sourcePageFilterDetails by subNum desc
//        Collections.sort(sourcePageFilterDetails,new Comparator<SourcePageFilterDetail>() {
//            @Override
//            public int compare(SourcePageFilterDetail o1,
//                               SourcePageFilterDetail o2) {
//                return o2.getSubNum().compareTo(o1.getSubNum());
//            }
//        });
//
//        for (SourcePageFilterDetail spfDetail : sourcePageFilterDetails) {
//            Filter filter = spfDetail.getFilter();
//            //logger.info("Start generate filter [" + filter.getFilterName() + ", subNum=" + spfDetail.getSubNum() + "] for sourcePageFilter [" + spFilter.getSourcePageFilterName() + "]");
//
//            Class<NodeFilter> filterClass = (Class<NodeFilter>) Class.forName(filter.getFilterClassName());
//
//            if (BaseConstants.FILTER_REFLECT_METHOD_CONST.equals(filter.getSetParamMethodName())) {
//                String[] classParams = filter.getFilterClassParams().split(",");
//                if (classParams == null || classParams.length < 1) {
//                    logger.error("Error occurs when get class params, null or empty");
//                    return null;
//                }
//
//                Class[] classParamsClass = new Class[classParams.length];
//                for (int i = 0; i < classParams.length; i++) {
//                    classParamsClass[i] = Class.forName(classParams[i]);
//                }
//
//                Constructor<NodeFilter> cons = filterClass.getConstructor(classParamsClass);
//                NodeFilter node = null;
//
//                if (StringUtils.isNotEmpty(spfDetail.getParamValue1()) && StringUtils.isNotEmpty(spfDetail.getParamValue2())) {
//                    node = cons.newInstance(spfDetail.getParamValue1(),spfDetail.getParamValue2());
//                } else if (StringUtils.isNotEmpty(spfDetail.getParamValue1())) {
//                    node = cons.newInstance(spfDetail.getParamValue1());
//                } else {
//
//                    List<NodeFilter> childNodelist = new ArrayList<NodeFilter>();
//                    for (Entry<SourcePageFilterDetail, NodeFilter> entry : resultMap.entrySet()) {
//                        if (spfDetail.getId().equals(entry.getKey().getParentNode().getId())) {
//                            childNodelist.add(entry.getValue());
//                        }
//                    }
//
//                    if (childNodelist.size() < 1) {
//                        logger.error("Error orrurs when get child node for parent node");
//                        return null;
//                    } else if (childNodelist.size() == 1) {
//                        node = cons.newInstance(childNodelist.get(0));
//                    } else {
//                        NodeFilter[] nodeFilterArr = new NodeFilter[childNodelist.size()];
//                        Object[] objs = { childNodelist.toArray(nodeFilterArr) };
//                        node = cons.newInstance(objs);
//                    }
//                }
//
//                resultMap.put(spfDetail, node);
//
//            } else {//TODO use SET method to generate
//
//            }
//            //logger.info("End generate filter [" + filter.getFilterName() + ", subNum=" + spfDetail.getSubNum() + "] for sourcePageFilter [" + spFilter.getSourcePageFilterName() + "]");
//        }
//
//        NodeFilter nodeFilter = null;
//        for (Entry<SourcePageFilterDetail, NodeFilter> entry : resultMap.entrySet()) {
//            if (entry.getKey().getSubNum() == 1) {
//                nodeFilter = entry.getValue();
//                break;
//            }
//        }
//
//        return nodeFilter;
//
//    }

}