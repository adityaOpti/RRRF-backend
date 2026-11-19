package com.probity.echawdi.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name="nirank_and_completedwork_forms")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Nirank extends PlainAudit{


	private static final long serialVersionUID = 1L;


	@Id
	@NotNull
	@Column(name = "\"id\"", precision = 19)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="nirank_and_completedwork_forms_seq_gen")
	@SequenceGenerator(name="nirank_and_completedwork_forms_seq_gen", sequenceName="nirank_and_completedwork_forms_seq", allocationSize=1)
	private Long id;

	@Column(name="district_code")
	private String districtCode;
	
	@Column(name="taluka_code")
	private String talukaCode;
	
	@Column(name="ccode")
	private String cCode;

	@Column(name="revenue_year")
	private String revenueYear;

	@Column(name="form_id")
	private String formId;

	@Column(name="is_nirank")
	private Character isNirank;

	@Column(name="is_completed")
	private Character isCompleted;

	@Column(name="remarks")
	private Character remarks;
	
//	@Column(name="form_names")
//	private String formNames;

	@Transient
	private List<String> cCodes;
	
	@Transient
	private String lgdCode;
	
	@Transient
	private String  village_code;
	
	@Transient
	private String village_name;
	

	public String getcCode() {
		return cCode;
	}
	
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public List<String> getcCodes() {
		return cCodes;
	}
	public void setcCodes(List<String> cCodes) {
		this.cCodes = cCodes;
	}
}






