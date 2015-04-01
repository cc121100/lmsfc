package com.cc.lmsfc.common.model.article;

import com.cc.lmsfc.common.model.BaseModel;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-3-13.
 */
@Entity
@Table(name = "tbl_article_element")
public class ArticleElement extends BaseModel {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    @Size(max = 50)
    private String name;

    @Column(name = "file_location", nullable = false)
    @NotEmpty
    @Size(max = 100)
    private String fileLocation;

    @Column(name = "state", nullable = false)
    @NotNull
    private Integer state;

    @Column(name = "files",nullable = false)
//    @NotEmpty
    @Size(max = 256)
    private String files;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_tkj_id")
    private ArticleTaskJob articleTaskJob;

//    @OneToOne(mappedBy = "articleElement",fetch = FetchType.LAZY)
    private Article article;

    @Transient
    private Map<String,Object> elementMap;


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

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public ArticleTaskJob getArticleTaskJob() {
        return articleTaskJob;
    }

    public void setArticleTaskJob(ArticleTaskJob articleTaskJob) {
        this.articleTaskJob = articleTaskJob;
    }

    public Map<String, Object> getElementMap() {
        return elementMap;
    }

    public void setElementMap(Map<String, Object> elementMap) {
        this.elementMap = elementMap;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String[] getProperties() {
        return new String[0];
    }
}
