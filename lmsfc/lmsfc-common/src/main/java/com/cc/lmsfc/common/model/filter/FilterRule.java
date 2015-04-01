package com.cc.lmsfc.common.model.filter;

import com.cc.lmsfc.common.model.BaseModel;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by tomchen on 15-3-12.
 */
@Entity
@Table(name = "tbl_filter_rule")
public class FilterRule extends BaseModel{


    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;


    @Column(name = "name", nullable = false)
    @NotEmpty
    @Size(max = 50)
    private String name;

    @Column(name="source_domain" ,nullable=false, unique = true)
    @NotEmpty
    private String sourceDomain;

    @OneToMany(mappedBy="filterRule",fetch = FetchType.LAZY)
//    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FilterDetail> filterDetails;

    @OneToMany(mappedBy = "filterRule",fetch = FetchType.LAZY)
    private List<ArticleTaskJob> articleTaskJobs;

    @Transient
    private Map<String, List<FilterDetail>> filterDetailMap;


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

    public String getSourceDomain() {
        return sourceDomain;
    }

    public void setSourceDomain(String sourceDomain) {
        this.sourceDomain = sourceDomain;
    }

    public List<FilterDetail> getFilterDetails() {
        return filterDetails;
    }

    public void setFilterDetails(List<FilterDetail> filterDetails) {
        this.filterDetails = filterDetails;
    }

    public List<ArticleTaskJob> getArticleTaskJobs() {
        return articleTaskJobs;
    }

    public void setArticleTaskJobs(List<ArticleTaskJob> articleTaskJobs) {
        this.articleTaskJobs = articleTaskJobs;
    }

    public Map<String, List<FilterDetail>> getFilterDetailMap() {
        return filterDetailMap;
    }

    public void setFilterDetailMap(Map<String, List<FilterDetail>> filterDetailMap) {
        this.filterDetailMap = filterDetailMap;
    }

    public Map<String, List<FilterDetail>> formatFilterDetailMap(){
        if (filterDetailMap == null){
            filterDetailMap = new HashMap<>();
        }else{
            filterDetailMap.clear();
        }

        //filter by category, <key:category,value:filterDetail order by subNo desc>
        if(CollectionUtils.isNotEmpty(filterDetails)){
            Set<String> categorySet = new HashSet<>();
            for (FilterDetail fd : filterDetails){
                categorySet.add(fd.getCategory());
            }

            Iterator<String> it = categorySet.iterator();
            while(it.hasNext()){
                final String category = it.next();

                //get list by category
                List<FilterDetail> list = (ArrayList<FilterDetail>) CollectionUtils.select(filterDetails, new Predicate<FilterDetail>() {
                    @Override
                    public boolean evaluate(FilterDetail filterDetail) {
                        return filterDetail.getCategory().equals(category);
                    }
                });

                //sort list by subNum desc for each category
                Collections.sort(list,new Comparator<FilterDetail>() {
                    @Override
                    public int compare(FilterDetail o1,
                                       FilterDetail o2) {
                        return o2.getSubNum().compareTo(o1.getSubNum());
                    }
                });

                filterDetailMap.put(category,list);
            }
        }

        return filterDetailMap;
    }

    @Override
    public String[] getProperties() {

        return new String[]{"name","sourceDomain"};
    }
}
