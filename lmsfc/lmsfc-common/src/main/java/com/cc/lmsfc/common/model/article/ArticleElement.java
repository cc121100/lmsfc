package com.cc.lmsfc.common.model.article;

import com.cc.lmsfc.common.model.BaseModel;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
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

    @Column(name = "file_location", nullable = false)
    @NotEmpty
    private String fileLocation;

    @Column(name = "state", nullable = false)
    @NotEmpty
    private int state;

    @Column(name = "files",nullable = false)
    @NotEmpty
    private String files;

    @Column(name = "csss",nullable = false,length = 2000)
    @NotEmpty
    private String csss;

    @Column(name = "imgs",nullable = false,length = 2000)
    @NotEmpty
    private String imgs;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_tkj_id")
    private ArticleTaskJob articleTaskJob;

    @Transient
    private Map<String,Object> elementMap;



}
