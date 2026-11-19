/**
 * 
 */
package com.probity.echawdi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Sandeep Dubey
 *
 */

@Data
@EqualsAndHashCode(callSuper =false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="m_crop_edu_cess")
public class CropEduCess  extends PlainAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @NotNull
    @Column(name = "\"id\"", precision = 19)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="m_crop_edu_cess_seq_gen")
    @SequenceGenerator(name="m_crop_edu_cess_seq_gen", sequenceName="m_crop_edu_cess_seq", allocationSize=1)
	private Long id; 

    @Column(name="crop_code")
    private Integer cropCode;
    
	@Column(name="crop_name")
	private String cropName;
  
	@Column(name="crop_price_per_hect")
	private Double cropPricePerHect;
}