package com.cc.lmsfc.web.controller;

import com.cc.lmsfc.web.redis.plugin.RedisKit;
import com.cc.lmsfc.web.response.ArtAndCatJsonResponse;
import com.cc.lmsfc.web.response.ArticleJsonResponse;
import com.cc.lmsfc.web.response.ArticleResponse;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import redis.clients.jedis.Pipeline;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tomchen on 15-4-24.
 */

public class IndexController extends Controller {

    private static int pageCount = 5;

    public void test_do(){
        renderText("Hello JFinal");
    }

    @ActionKey("loadmore_do")
    public void loadmore(){

        try{
            // category pathname, page
            String catPathName = getPara("cpn","default");
            int page = getParaToInt("page",1);
            page = page > 0 ? page : 1;
            int begin = pageCount * (page - 1);
            int end = (pageCount * page) - 1;

            Object[] artIdSet =  RedisKit.zrange("art.zset." + catPathName , begin, end).toArray();
            List<String> artKeys = new ArrayList<>();
            for(Object str : artIdSet){
                String key = "art.hash." + str;
                artKeys.add(key);
            }

            List<Object> responseList = RedisKit.pipelinedHgetall(artKeys);
            List<Map<String,String>> artResList = new ArrayList<>();

            for(int i = 0 ; i < responseList.size();i++){
                Object res = responseList.get(i);
                Map<String, String> map = (Map<String, String>) res;
                map.put("aid",artIdSet[i].toString());
                map.remove("pre");
                map.remove("next");
                artResList.add(map);
            }

            //res
            ArticleJsonResponse response = new ArticleJsonResponse();
            response.setStateCode(200);
            response.setMsg("Success");
            response.setDate(new Date());
            response.setData(artResList);
            response.setPage(page);

            renderJson(response);
        }catch (Exception e){
            ArticleJsonResponse response = new ArticleJsonResponse();
            response.setStateCode(500);
            response.setMsg("Error");
            response.setDate(new Date());
            renderJson(response);
        }

    }

    @ActionKey("loadArtAndCat_do")
    public void loadArtAndCat_do(){

        ArtAndCatJsonResponse acResponse = null;

        try {
            //param rType aid cPathName
            String rType = getPara("rType");
            String aid = getPara("aid");
            String catPathName = getPara("cpn");

            acResponse = new ArtAndCatJsonResponse();
            List<Map<String,String>> avs = new ArrayList<>();
            Map<String, String> avMap = null;

            if("l".equals(rType)){
                // get first page of art
                Object[] artIdSet =  RedisKit.zrange("art.zset." + catPathName , 0, pageCount - 1).toArray();
                List<String> artKeys = new ArrayList<>();
                for(Object str : artIdSet){
                    String key = "art.hash." + str;
                    artKeys.add(key);
                }

                List<Object> responseList = RedisKit.pipelinedHmget(artKeys, "viewCount");

                for(int i = 0; i < responseList.size(); i++){
                    avMap = new HashMap<>();
                    avMap.put("aid", artIdSet[i].toString());
                    avMap.put("viewCount", ((List<String>)responseList.get(i)).get(0));

                    avs.add(avMap);
                }


            }else if("a".equals(rType)){
                if(null == aid || aid.length() <= 0){
                    throw new RuntimeException("aid is null or empty.");
                }
                List<String> list = RedisKit.hmget("art.hash." + aid,"viewCount","pre","next");
                avMap = new HashMap<>();
                avMap.put("aid",aid);
                avMap.put("viewCount",list.get(0));
                avMap.put("pre",list.get(1));
                avMap.put("nxet",list.get(2));

                avs.add(avMap);
            }

            // cList
            List<Map<String,String>> cList = new ArrayList<>();
            String[] catIdSet = RedisKit.zrange("cat.zset", 0, -1).toArray(new String[0]);
            List<String> catKeys = new ArrayList<>();
            for(String str : catIdSet){
                String key = "cat.hash." + str;
                catKeys.add(key);
            }
            List<Object> catList = RedisKit.pipelinedHgetall(catKeys);
            Map<String,String> catMap = null;
            for(int i = 0; i < catList.size();i++){
                catMap = (Map<String, String>) catList.get(i);
                catMap.put("cid",catIdSet[i]);

                cList.add(catMap);
            }


            acResponse.setAvs(avs);
            acResponse.setcList(cList);
            acResponse.setStateCode(200);
            acResponse.setMsg("Success");
            acResponse.setDate(new Date());
            renderJson(acResponse);

        } catch (Exception e) {
            acResponse.setStateCode(500);
            acResponse.setMsg("Error");
            acResponse.setDate(new Date());
            acResponse.setAvs(null);
            acResponse.setcList(null);

            renderJson(acResponse);

        }

    }
}
