package com.cc.lmsfc.common.model.article;

import com.cc.lmsfc.common.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by tomchen on 15-3-13.
 */

@Entity
@Table(name = "tbl_article")
public class Article extends BaseModel {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @Column(name = "name",nullable = false)
    @NotNull
    @Size(max = 100)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_ele_id")
    private ArticleElement articleElement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "art_cate_id",nullable = true)
    private ArticleCategory articleCategory;

    @Column(name = "state",nullable = false)
    @NotNull
    private Integer state;

    @Column(name = "art_file_name",nullable = false)
    @NotNull
    @Size(max = 50)
    private String artFileName;

    @Column(name = "generate_time")
    private Date generateTime;

    @Column(name = "description")
    @Size(max = 100)
    private String description;

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

    public ArticleElement getArticleElement() {
        return articleElement;
    }

    public void setArticleElement(ArticleElement articleElement) {
        this.articleElement = articleElement;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getArtFileName() {
        return artFileName;
    }

    public void setArtFileName(String artFileName) {
        this.artFileName = artFileName;
    }

    public Date getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Date generateTime) {
        this.generateTime = generateTime;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String[] getProperties() {
        return new String[0];
    }
}
