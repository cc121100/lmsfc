package com.cc.lmsfc.common.model.task;

import com.cc.lmsfc.common.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by tomchen on 15-4-1.
 */
@Entity
@Table(name = "tbl_art_tkj_run_log")
public class ArtTaskJobRunLog extends BaseModel {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;


    //0:success, 1:fail
    @Column(name = "state", nullable = false)
    @NotNull
    private Integer state = 0;

    @Column(name = "art_task_step", nullable = false)
    @NotNull
    private Integer artTaskStep;

    //format - date isSuccess taskStep description(optional)
    @Column(name = "description",nullable = true)
    @Size(max = 256)
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_tkj_id")
    private ArticleTaskJob articleTaskJob;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArticleTaskJob getArticleTaskJob() {
        return articleTaskJob;
    }

    public void setArticleTaskJob(ArticleTaskJob articleTaskJob) {
        this.articleTaskJob = articleTaskJob;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getArtTaskStep() {
        return artTaskStep;
    }

    public void setArtTaskStep(Integer artTaskStep) {
        this.artTaskStep = artTaskStep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String[] getProperties() {
        return new String[]{"state","artTaskStep","description"};
    }
}
