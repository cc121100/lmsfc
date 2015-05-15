package com.cc.lmsfc.task.helper;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.model.article.Article;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tomchen on 15-5-14.
 */
@Component
public class RedisHelper {

    @Autowired
    private RedisTemplate redisTemplate;

    public void addArticle(Article article){

        String artId = article.getId();
        String cPathName = article.getArticleCategory().getPathName();

        // 1. add new art to art.zset.xxx
        String artZsetKey = CommonConsts.REDIS_ART_ZSET_PRE + cPathName;

        ZSetOperations.TypedTuple<String> currentFirstMember = (ZSetOperations.TypedTuple<String>)redisTemplate.opsForZSet().rangeWithScores(artZsetKey, 0, 0).iterator().next();

        String currentArtId = currentFirstMember.getValue();
        double score = currentFirstMember.getScore();

        redisTemplate.opsForZSet().add(artZsetKey, artId, score - 1);


        // 2. update current first art's next(art.ele.id_art.name)
        String nextStr = article.getArticleElement().getId() + "_" +article.getName();
        redisTemplate.opsForHash().put(CommonConsts.REDIS_ART_HASH_PRE + currentArtId, "next", nextStr);

        // 3. add new art(updated new art's pre)

        Map<String,String> valueMap = Maps.newHashMap();
        valueMap.put("title", article.getName());
        valueMap.put("des", article.getDescription());
        valueMap.put("url", article.getArticleElement().getId() + ".html");
        valueMap.put("postTime", new SimpleDateFormat("yyyy-MM-DD hh:mm:ss").format(article.getGenerateTime()));
        valueMap.put("viewCount",article.getViewCount() == null ? "0" : article.getViewCount() + "");

        Map<String,String> currentArtHash = redisTemplate.opsForHash().entries(CommonConsts.REDIS_ART_HASH_PRE + currentArtId);


        valueMap.put("pre", StringUtils.removeEnd(currentArtHash.get("url"),".html") + "_" + currentArtHash.get("title"));
        valueMap.put("next","");

        redisTemplate.opsForHash().putAll(CommonConsts.REDIS_ART_HASH_PRE + artId,valueMap);

        // 4 update cat.hash.cPathName's artCount
        redisTemplate.opsForHash().increment(CommonConsts.REDIS_CAT_HASH_PRE + article.getArticleCategory().getId(),"artCount",1);;

    }
}
