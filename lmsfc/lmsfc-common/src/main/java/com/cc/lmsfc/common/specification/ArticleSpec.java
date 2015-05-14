package com.cc.lmsfc.common.specification;

import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomchen on 15-5-13.
 */
public class ArticleSpec  {

    public Specification<Article> findByCategory(final String articleCatId){
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(StringUtils.isNotEmpty(articleCatId)){
                    //Join<Article,User> userJoin = root.join(root.getModel().getSingularAttribute("article",Article.class),JoinType.LEFT);

                    predicate.add(cb.equal(root.get("art_cate_id").as(String.class), articleCatId));


                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }


    public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        query.where(cb.equal(root.get("articleCategory.name") ,""));
        return null;
    }
}
