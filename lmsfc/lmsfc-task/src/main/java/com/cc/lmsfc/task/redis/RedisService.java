package com.cc.lmsfc.task.redis;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArticleCategoryDAO;
import com.cc.lmsfc.common.dao.ArticleDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tomchen on 15-5-11.
 */
@Component
public class RedisService {

    private Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Transactional
    public void initLmsfcRedis(Message msg) {
        logger.info("Init LMsfc Redis data.");

        try{
            Map<String, Object> m = (Map<String, Object>) msg.getPayload();
            String type = (String) m.get("type");
            if (CommonConsts.INIT_PAGE_REDIS_TSK.equals(type) || CommonConsts.INIT_REDIS_TSK.equals(type)) {

                redisTemplate.opsForHash().keys(CommonConsts.REDIS_ART_HASH_PRE + "*");

                // 1 get active article list and article category list
                List<Article> articleList = new LinkedList<>();
                articleList = articleDAO.findByState(1);


                List<ArticleCategory> categoryList = new ArrayList<>();
                categoryList = articleCategoryDAO.findActiveCatOrderBySequence();

                Map<String,String> artIdAndCountMap = Maps.newHashMap();

                // update art's countView in db before flushdb
                for(ArticleCategory artCat : categoryList){
                    String artZsetKey = CommonConsts.REDIS_ART_ZSET_PRE + artCat.getPathName();
                    if(redisTemplate.hasKey(artZsetKey)){
                        Set<Object> artKeySet = redisTemplate.opsForZSet().range(artZsetKey, 0, -1);
                        if(!artKeySet.isEmpty()){
                            Iterator it = artKeySet.iterator();
                            while (it.hasNext()){
                                String artKey = (String) it.next();
                                String artHashKey = CommonConsts.REDIS_ART_HASH_PRE + artKey;
                                Object viewCountObj = redisTemplate.opsForHash().get(artHashKey,"viewCount");
                                artIdAndCountMap.put(artKey, (String) viewCountObj);
                            }
                        }

//                        if(!artKeySet.isEmpty()){
//                            final Iterator it = artKeySet.iterator();
//                            List<Object> list = redisTemplate.executePipelined(new RedisCallback<String>() {
//                                @Override
//                                public String doInRedis(RedisConnection connection) throws DataAccessException {
//                                    while (it.hasNext()){
//                                        String artKey = (String) it.next();
//                                        String artHashKey = CommonConsts.REDIS_ART_HASH_PRE + artKey;
//                                        byte[] b = redisTemplate.getKeySerializer().serialize(artHashKey);
//                                        Map map = connection.hGetAll(b);
//                                        System.out.println(map.get("viewCount"));
////                                        byte[] viewCountBytes = connection.hGetAll(redisTemplate.getStringSerializer().serialize(artHashKey),
////                                                redisTemplate.getHashKeySerializer().serialize("viewCount"));
//
//
////                                        String viewCountStr = redisTemplate.getStringSerializer().deserialize(viewCountBytes);
////                                        artIdAndCountMap.put(artKey,viewCountStr);
//                                    }
//                                    return null;
//                                }
//                            });
//                        }
                    }
                }

                for(int i = 0; i < articleList.size(); i++){
                    String artId = articleList.get(i).getId();
                    String viewCountStr = artIdAndCountMap.get(artId);
                    int viewCount = 0;
                    try{
                        viewCount = Integer.parseInt(viewCountStr);
                    }catch (NumberFormatException e){
                        logger.error("NumberFormatException occurs when format viewCount " + viewCountStr +" for art " + artId);
                        viewCount = 0;
                    }
                    articleList.get(i).setViewCount(viewCount);
                }


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
                    String key = CommonConsts.REDIS_ART_ZSET_PRE + catPath;
                    initLmsfcRedisList(key, artIdsOfCat.toArray());
                }
                for(Map.Entry<String,Map<String,String>> entry : artMap.entrySet()){
                    String aid = entry.getKey();
                    Map<String, String> hash = entry.getValue();
                    String key = CommonConsts.REDIS_ART_HASH_PRE + aid;
                    initArtHashes(key,hash);
                }

                initLmsfcRedisList(CommonConsts.REDIS_CAT_ZSET, Lists.newArrayList(catMap.keySet()).toArray());
                for(Map.Entry<String, Map<String,String>> entry : catMap.entrySet()){
                    String cid = entry.getKey();
                    Map<String,String> cMap = entry.getValue();
                    String key = CommonConsts.REDIS_CAT_HASH_PRE + cid;
                    initArtHashes(key,cMap);
                }


//                Map map = redisTemplate.opsForHash().entries("art.hash.402868c94ca0eb01014ca125e7d50021");
//                System.err.println("");
//
//                Set s = redisTemplate.opsForZSet().range("art.zset.java",0,4);
                System.out.println("Success Init lmsfc redis data");
                logger.info("Success Init lmsfc redis data.");

            } else {

            }
        }catch (Exception e){
//            e.printStackTrace();;
            logger.error("Error occurs when init redis datas: " + e.getMessage());
        }


    }



    private void formatDatas(List<Article> articleList,List<ArticleCategory> articleCategories, Map<String,Map<String,String>> artMap,Map<String,Map<String,String>> catMap,Map<String,List<String>> catArtMap){

        //format articleList to Map<cat,List<Art>>

        Map<String,List<Article>> artListOfCatMap = Maps.newHashMap();
        for(Article art : articleList){
            String cPathName = art.getArticleCategory().getPathName();
            if(artListOfCatMap.containsKey(cPathName)){
                artListOfCatMap.get(cPathName).add(art);
            }else {
                artListOfCatMap.put(cPathName,Lists.newArrayList(art));
            }

            String aid = art.getId();
            if(catArtMap.containsKey(cPathName)){
                catArtMap.get(cPathName).add(aid);
            }else {
                catArtMap.put(cPathName,Lists.newArrayList(aid));
            }

        }

        for(Map.Entry<String, List<Article>> entry : artListOfCatMap.entrySet()){
            String cPathName = entry.getKey();
            List<Article> list = entry.getValue();
            for(int i = 0 ; i < list.size(); i++ ){
                Article art = list.get(i);
                Map map = Maps.newHashMap();

                map.put("title",art.getName());
                map.put("url",art.getArticleElement().getId() + ".html");
                map.put("des",art.getDescription());
                map.put("postTime",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(art.getGenerateTime()));
                map.put("viewCount",art.getViewCount() == null ? "0" : "" + art.getViewCount().toString() +"");

                if(i == 0){
                    map.put("next", "");
                }else{
                    Article nextArt = list.get(i - 1);
                    String str = nextArt.getArticleElement().getId() + "_" +nextArt.getName();
                    map.put("next", str);
                }

                if(i == list.size()-1){
                    map.put("pre", "");
                }else{
                    Article preArt = list.get(i + 1);
                    String str = preArt.getArticleElement().getId() + "_" +preArt.getName();
                    map.put("pre", str);
                }

                artMap.put(art.getId(),map);

            }
        }

//        for(int i = 0 ; i < articleList.size(); i++ ){
//            Article art = articleList.get(i);
//            Map map = Maps.newHashMap();
//
//            map.put("title",art.getName());
//            map.put("url",art.getArticleElement().getId() + ".html");
//            map.put("des",art.getDescription());
//            map.put("postTime",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(art.getGenerateTime()));
//            map.put("viewCount",art.getViewCount() == null ? "0" : "" + art.getViewCount().toString() +"");
//            if(i == 0){
//                map.put("pre", "");
//            }else{
//                Article preArt = articleList.get(i -1);
//                String str = preArt.getArticleElement().getId() + "_" +preArt.getName();
//                map.put("pre", str);
//            }
//
//            if(i == articleList.size()-1){
//                map.put("next", "");
//            }else{
//                Article nextArt = articleList.get(i + 1);
//                String str = nextArt.getArticleElement().getId() + "_" +nextArt.getName();
//                map.put("next", str);
//            }
//
//            artMap.put(art.getId(),map);
////            String aid = art.getId();
////            String catPath = art.getArticleCategory().getPathName();
////            if(catArtMap.containsKey(catPath)){
////                catArtMap.get(catPath).add(aid);
////            }else {
////                catArtMap.put(catPath,Lists.newArrayList(aid));
////            }
//        }

        for(ArticleCategory cat : articleCategories){
            Map map = Maps.newHashMap();
            map.put("name",cat.getName());
            map.put("cName",cat.getPathName());
            map.put("artCount", "" + cat.getArticleList().size() + "");
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
