package com.cc.lmsfc.common.model.filter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.cc.lmsfc.common.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="tbl_filter")
public class Filter extends BaseModel{

	@Id
	@GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name="system-uuid",strategy="uuid")  
	private String id;
	
	@Column(name="filter_name" ,nullable=false, unique = true)
	@NotEmpty
	private String filterName;
	
	@Column(name="filter_class_name", nullable = false)
	@NotEmpty
	private String filterClassName;
	
	@Column(name="filter_class_params", nullable = false)
	@NotEmpty
	private String filterClassParams;
	
	@Column(name="param_type" ,nullable=false)
	@NotEmpty
	private String paramType;
	
	@Column(name="param_num")
	private String paramNum;//TODO check if necessary
	
	@Column(name="set_param_method_name")
	@NotEmpty
	private String setParamMethodName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamNum() {
		return paramNum;
	}

	public void setParamNum(String paramNum) {
		this.paramNum = paramNum;
	}

	public String getSetParamMethodName() {
		return setParamMethodName;
	}

	public void setSetParamMethodName(String setParamMethodName) {
		this.setParamMethodName = setParamMethodName;
	}

	public String getFilterClassName() {
		return filterClassName;
	}

	public void setFilterClassName(String filterClassName) {
		this.filterClassName = filterClassName;
	}

	public String getFilterClassParams() {
		return filterClassParams;
	}

	public void setFilterClassParams(String filterClassParams) {
		this.filterClassParams = filterClassParams;
	}
	
}
