package com.cc.lmsfc.common.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3152132842189516704L;

	@Version
	private Integer version;
	
	@CreatedBy
	@Column(name="created_by")
	private String createdBy;
	
	@CreatedDate
	@Column(name="created_dt")
	private Date createdDt;
	
	@LastModifiedBy
	@Column(name="updated_by")
	private String updatedBy;
	
	@LastModifiedDate
	@Column(name="updated_dt")
	private Date updatedDt;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	/*public String getCreatedDtStr() {
		return createdDtStr;
	}

	public void setCreatedDtStr(String createdDtStr) {
		this.createdDtStr = createdDtStr == null ? null : createdDtStr.trim();
		if (createdDtStr != null) {
			try {
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
				createdDt = formater.parse(createdDtStr);
			} catch (java.text.ParseException e) {
				
			}

		} else {
			createdDt = null;
		}
	}


	public String getUpdatedDtStr() {
		return updatedDtStr;
	}

	public void setUpdatedDtStr(String updatedDtStr) {
		this.updatedDtStr = updatedDtStr == null ? null : updatedDtStr.trim();
		if (updatedDtStr != null) {
			try {
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
				updatedDt = formater.parse(updatedDtStr);
			} catch (java.text.ParseException e) {
				
			}

		} else {
			updatedDt = null;
		}
	}*/
	
	

}
