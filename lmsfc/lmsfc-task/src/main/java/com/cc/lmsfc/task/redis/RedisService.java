package com.cc.lmsfc.task.redis;

import com.cc.lmsfc.common.dao.ArticleCategoryDAO;
import com.cc.lmsfc.common.dao.ArticleDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import com.cc.lmsfc.common.util.JacksonUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by tomchen on 15-5-11.
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Transactional
    public void initLmsfcRedis(){

        // 1 get active article list and article category list
        List<Article> articleList = new LinkedList<>();
        articleList = articleDAO.findByState(1);


        List<ArticleCategory> categoryList = new ArrayList<>();
        categoryList = articleCategoryDAO.findActiveCatOrderBySequence();

        // 2 delete redis if exsited those list
        List<String> deleteKeys = Lists.newArrayList("art.zset.*","art.hash.*","cat.zset","cat.hash.*");
        redisTemplate.delete(deleteKeys);

        // 3 add new list to redis
        Map<String,Map<String,String>> artMap = Maps.newHashMap();
        Map<String,List<String>> catArtMap = Maps.newLinkedHashMap();
        Map<String,Map<String,String>> catMap = Maps.newLinkedHashMap();


        formatDatas(articleList,categoryList,artMap,catMap,catArtMap);


        for(Map.Entry<String,List<String>> entry : catArtMap.entrySet()){
            String catPath = entry.getKey();
            List<String> artIdsOfCat = entry.getValue();
            String key = "art.zset." + catPath;
            initLmsfcRedisList(key, artIdsOfCat.toArray());
        }
        for(Map.Entry<String,Map<String,String>> entry : artMap.entrySet()){
            String aid = entry.getKey();
            Map<String, String> hash = entry.getValue();
            String key = "art.hash." + aid;
            initArtHashes(key,hash);
        }

        initLmsfcRedisList("cat.zset", Lists.newArrayList(catMap.keySet()).toArray());
        for(Map.Entry<String, Map<String,String>> entry : catMap.entrySet()){
            String cid = entry.getKey();
            Map<String,String> cMap = entry.getValue();
            String key = "cat.hash." + cid;
            initArtHashes(key,cMap);
        }


        Map map = redisTemplate.opsForHash().entries("art.hash.402868c94ca0eb01014ca125e7d50021");
        System.err.println("");

        Set s = redisTemplate.opsForZSet().range("art.zset.java",0,4);
        System.err.println("");
    }

    private void formatDatas(List<Article> articleList,List<ArticleCategory> articleCategories, Map<String,Map<String,String>> artMap,Map<String,Map<String,String>> catMap,Map<String,List<String>> catArtMap){

        for(int i = 0 ; i < articleList.size(); i++ ){
            Article art = articleList.get(i);
            Map map = Maps.newHashMap();

            StringBuffer sb = new StringBuffer();
//            sb.append("{")
//                    .append("'title':'").append(art.getName()).append("',")
//                    .append("'des':'").append(art.getDescription()).append("',")
//                    .append("'postTime':'").append(art.getGenerateTime()).append("',")
//                    .append("'url':'").append(art.getArticleElement().getId()).append(".html'")
//                    .append("}");
            sb.append("{")
                    .append("\"title\":\"").append(art.getName()).append("\",")
                    .append("\"des\":\"").append(art.getDescription()).append("\",")
                    .append("\"postTime\":\"").append(art.getGenerateTime()).append("\",")
                    .append("\"url\":\"").append(art.getArticleElement().getId()).append(".html\"")
                    .append("}");
//            sb.append("{");
//                    .append("title:").append(art.getName()).append(",")
//                    .append("des:").append(art.getDescription()).append(",")
//                    .append("postTime:").append(art.getGenerateTime()).append(",")
//                    .append("url:").append(art.getArticleElement().getId()).append(".html")
//                    .append("}");
            map.put("jsonStr", sb.toString());
            map.put("viewCount",art.getViewCount() == null ? "0" : "" + art.getViewCount().toString() +"");
            if(i == 0){
                map.put("pre", "");
            }else{
                Article preArt = articleList.get(i -1);
                String str = preArt.getArticleElement().getId() + "_" +preArt.getName();
                map.put("pre", str);
            }

            if(i == articleList.size()-1){
                map.put("next", "");
            }else{
                Article nextArt = articleList.get(i + 1);
                String str = nextArt.getArticleElement().getId() + "_" +nextArt.getName();
                map.put("next", str);
            }

            artMap.put(art.getId(),map);
            String aid = art.getId();
            String catPath = art.getArticleCategory().getPathName();
            if(catArtMap.containsKey(catPath)){
                catArtMap.get(catPath).add(aid);
            }else {
                catArtMap.put(catPath,Lists.newArrayList(aid));
            }
        }

        for(ArticleCategory cat : articleCategories){
            Map map = Maps.newHashMap();
            StringBuffer sb = new StringBuffer();
            sb.append("{").append("name:").append( cat.getName()).append(",")
                    .append("url':").append(cat.getPathName()).append("")
                    .append("}");
            map.put("jsonStr", sb.toString());
            map.put("artCount", "" + cat.getArticleList().size() + "");

//            sb.append("{").append("'name':").append("'" + cat.getName()).append("',")
//                    .append("'url':").append("'" +cat.getPathName()).append("'")
//                    .append("}");
//            map.put("jsonStr", sb.toString());
//            map.put("artCount", "" + cat.getArticleList().size() + "");

            catMap.put(cat.getId(),map);
        }

    }

    private void initLmsfcRedisList(String key, Object[] values){
        Set<ZSetOperations.TypedTuple<Object>> zsetValus = new HashSet<>();
        for(int i = 0;i< values.length;i++){
            ZSetOperations.TypedTuple<Object> tuple = new DefaultTypedTuple<Object>(values[i],new Double(i+1+""));
            zsetValus.add(tuple);
        }

        redisTemplate.opsForZSet().add(key, zsetValus);
    }

    private void  initArtHashes(String key,Map<String,String> values){
        redisTemplate.opsForHash().putAll(key,values);
    }
}
