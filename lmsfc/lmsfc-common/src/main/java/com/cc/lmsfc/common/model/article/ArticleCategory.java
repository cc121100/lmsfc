package com.cc.lmsfc.common.model.article;

import com.cc.lmsfc.common.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by tomchen on 15-3-20.
 */
@Entity
@Table(name = "tbl_article_category")
public class ArticleCategory extends BaseModel {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @Column(name = "name",nullable = false)
    @NotNull
    @Size(max = 20)
    private String name;

    @Column(name = "path_name",nullable = false)
    @NotNull
    @Size(max = 20
    )
    private String pathName;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy="articleCategory",fetch = FetchType.LAZY)
    private List<Article> articleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    @Override
    public String[] getProperties() {
        return new String[0];
    }
}
