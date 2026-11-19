package com.probity.echawdi.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class PlainAudit implements Serializable{

	private static final long serialVersionUID = 1L;

	@Version
	private int version;
	
	@CreatedDate
	private LocalDateTime createDtTm;
	
	@LastModifiedDate
	private LocalDateTime update_dt_tm;
	
	@Column(name="active_flag")
	private Character activeFlag;
	
	@PrePersist
	public void onPrePersist() {
		if(this.activeFlag == null) {
			this.activeFlag = 'Y';
		}
		this.createDtTm = LocalDateTime.now();
	}
	
	@PreUpdate
	public void onPreUpdate() {
		this.update_dt_tm = LocalDateTime.now();
	}
	
	@CreatedBy
	@Column(name="created_user_id", insertable=true, updatable= false)
	private Long createdUserId;
	
	@LastModifiedBy
	@Column(name="update_userid", insertable=false, updatable= true)
	private Long updateUserId;
}
