package com.cc.lmsfc.common.model.filter;

import com.cc.lmsfc.common.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by tomchen on 15-3-12.
 */

@Entity
@Table(name = "tbl_filter_detail")
public class FilterDetail extends BaseModel{

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @ManyToOne()
    @JoinColumn(name = "filter_rule_id")
    private FilterRule filterRule;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @Column(name="param_value1")
    @Length(max = 50)
    private String paramValue1;

    @Column(name="param_value2")
    @Size(max = 50)
    private String paramValue2;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_node_id")
    private FilterDetail parentNode;

    @Column(name="sub_num" ,nullable=false)
    @NotNull
    private Integer subNum;

    @Column(name = "category", nullable = false)
    @NotEmpty
    @Size(max = 50)
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getParamValue1() {
        return paramValue1;
    }

    public void setParamValue1(String paramValue1) {
        this.paramValue1 = paramValue1;
    }

    public String getParamValue2() {
        return paramValue2;
    }

    public void setParamValue2(String paramValue2) {
        this.paramValue2 = paramValue2;
    }

    public Integer getSubNum() {
        return subNum;
    }

    public void setSubNum(Integer subNum) {
        this.subNum = subNum;
    }

    public FilterRule getFilterRule() {
        return filterRule;
    }

    public void setFilterRule(FilterRule filterRule) {
        this.filterRule = filterRule;
    }

    public FilterDetail getParentNode() {
        return parentNode;
    }

    public void setParentNode(FilterDetail parentNode) {
        this.parentNode = parentNode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String[] getProperties() {
        return new String[]{"filterRule","filter","paramValue1","paramValue2","parentNode","subNum","category"};
    }
}
