/**
 * 
 */
package com.probity.echawdi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ashish Dahale
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="m_tenure")
public class Tenure {
		
		@Id
		@Column(name="tenure_code")
		private Integer tenureCode;
		
		@Column(name="tenureName")
		private String tenureName;
		
		@Column(name="date_stamp")
		private Date dateStamp;
		
}
